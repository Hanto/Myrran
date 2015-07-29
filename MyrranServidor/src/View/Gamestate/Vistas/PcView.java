package View.Gamestate.Vistas;// Created by Hanto on 07/04/2014.

import Controller.Controlador;
import DTO.DTOsPC;
import DTO.DTOsPlayer;
import Data.Settings;
import Interfaces.EntidadesTipos.PCI;
import Model.GameState.Mundo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PcView implements PropertyChangeListener
{
    //Model:
    private PCI pc;
    private MundoView mundoView;
    private Mundo mundo;
    private Controlador controlador;

    //Datos:
    private List<PCI> listaPCsCercanos = new ArrayList<>();

    protected MapaView mapaView;
    public PCViewNotificador notificador;

    //Constructor:
    public PcView(PCI pc, MundoView mundoView)
    {
        this.pc = pc;
        this.mundoView = mundoView;
        this.controlador = mundoView.controlador;
        this.notificador = new PCViewNotificador(pc.getConnectionID());
        this.mundo = mundoView.mundo;

        notificador.setPosition(pc.getX(), pc.getY());
        notificador.setNombre(pc.getNombre());
        notificador.setHPs(pc.getActualHPs(), pc.getMaxHPs());

        quienMeVe();

        mapaView = new MapaView(pc, this, mundo, controlador);
        pc.añadirObservador(this);
    }

    //Separamos los eventos Personales de los globales y mandamos los Personales de todas las unidades antes que los globales para
    //que los mensajes de creacion de unidades (Personales) tengan preferencia sobre los de alteracion de unidades ajenas (Globales)
    //y asi no nos suceda que se nos pida alterar la posicion de una unidad que aun no esta creada, generando un error

    public void generarDTOs()
    {   notificador.generarDTOs(); }

    public void enviarDatosPersonales()
    {   if (notificador.contieneDatosDTOPersonal()) controlador.enviarACliente(pc.getConnectionID(), notificador.dtoPersonal); }

    public void enviarDatosGlobales()
    {
        if (notificador.contieneDatosDTOGlobal() && !listaPCsCercanos.isEmpty())
        {   for (PCI PCCercanos : listaPCsCercanos) controlador.enviarACliente(PCCercanos.getConnectionID(), notificador.dtoGlobal); }
    }

    // NOTIFICADORES
    //------------------------------------------------------------------------------------------------------------------

    //El notificador de posicion es especial, ya que cada vez que cambia la posicion se tiene que comprobar si hay nuevos players
    //cerca susceptibles de ser añadidos a la lista de PCsCercanos, que luego se usara para retransmitir las notificaciones globales
    private void setPosition (float posX, float posY)
    {
        notificador.setPosition(posX, posY);
        quienMeVe();
        mapaView.comprobarVistaMapa();
    }

    private void quienMeVe()
    {
        for (PcView pcCercanos : mundoView.listaPcViews)
        {
            PCI pcCercano = pcCercanos.pc;

            if (pcCercano.getConnectionID() != pc.getConnectionID())
            {
                if (Math.abs(pcCercano.getX()- pc.getX()) <=  Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Horizontal_Resolution /2 &&
                    Math.abs(pcCercano.getY()- pc.getY()) <=  Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Vertical_Resolution /2     )
                {
                    añadirPCVisible(pcCercanos);
                    pcCercanos.añadirPCVisible(this);
                }
                else
                {
                    eliminarPCVisible(pcCercanos);
                    pcCercanos.eliminarPCVisible(this);
                }
            }
        }
    }

    private void añadirPCVisible (PcView pcview)
    {
        if (!listaPCsCercanos.contains(pcview.pc))
        {
            listaPCsCercanos.add(pcview.pc);
            notificador.añadirVeAlPC(pcview.pc);
        }
    }

    private void eliminarPCVisible (PcView pcView)
    {
        if (listaPCsCercanos.contains(pcView.pc))
        {
            listaPCsCercanos.remove(pcView.pc);
            notificador.añadirNoVeAlPC(pcView.pc.getConnectionID());
        }
    }

    private void setAnimacion(int numAnimacion)
    {   notificador.setNumAnimacion(numAnimacion); }

    private void añadirModificarSkillTalento(String skillID, int statID, int valor)
    {   notificador.añadirNumTalentosSkillPersonalizado(skillID, statID, valor); }

    private void añadirSpellPersonalizado(String spellID)
    {   notificador.añadirSkillPersonalizado(spellID); }

    private void añadirModificarHPs(float HPs)
    {   notificador.añadirModificarHPs(HPs); }

    private void dispose()
    {
        //para que deje de observar el Mapa Model
        mapaView.dispose();
        //Dejamos de observar al pc:
        pc.eliminarObservador(this);
        //eliminamos la vista y transmitimos la informacion al resto de clientes:
        mundoView.listaPcViews.remove(this);
        //transmitimos los mensajes que tuvieramos pendientes sin esperar al siguiente ciclo(Esto incluye el mensaje de eliminacion):
        notificador.añadirEliminarPC(pc.getConnectionID());
        generarDTOs();
        enviarDatosPersonales();
        enviarDatosGlobales();
    }



    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //MOBILES:
        if (evt.getNewValue() instanceof DTOsPlayer.Posicion)
        {   setPosition(((DTOsPlayer.Posicion) evt.getNewValue()).posX, ((DTOsPlayer.Posicion) evt.getNewValue()).posY); }

        if (evt.getNewValue() instanceof DTOsPlayer.Animacion)
        {   setAnimacion(((DTOsPlayer.Animacion) evt.getNewValue()).numAnimacion); }

        if (evt.getNewValue() instanceof DTOsPlayer.ModificarHPs)
        {   añadirModificarHPs(((DTOsPlayer.ModificarHPs) evt.getNewValue()).HPs); }

        if (evt.getNewValue() instanceof DTOsPlayer.SkillPersonalizado)
        {   añadirSpellPersonalizado(((DTOsPlayer.SkillPersonalizado) evt.getNewValue()).skillID);}


        if (evt.getNewValue() instanceof DTOsPC.EliminarPC)
        {   dispose(); }

        if (evt.getNewValue() instanceof DTOsPlayer.NumTalentosSkillPersonalizado)
        {   añadirModificarSkillTalento(
                ((DTOsPlayer.NumTalentosSkillPersonalizado) evt.getNewValue()).skillID,
                ((DTOsPlayer.NumTalentosSkillPersonalizado) evt.getNewValue()).statID,
                ((DTOsPlayer.NumTalentosSkillPersonalizado) evt.getNewValue()).valor);
        }
    }
}
