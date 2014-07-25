package View.Classes.PCView;// Created by Hanto on 07/04/2014.

import Controller.Controlador;
import DTO.DTOsPC;
import Data.Settings;
import Interfaces.EntidadesTipos.MobPC;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;
import View.Gamestate.MundoView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class PcView implements PropertyChangeListener
{
    //Model:
    private PC pc;
    private MundoView mundoView;
    private Mundo mundo;
    private Controlador controlador;

    //Datos:
    private List<MobPC> listaPCsCercanos = new ArrayList<>();
    private float x;
    private float y;

    protected MapaView mapaView;
    protected PCViewNotificador notificador;

    //Constructor:
    public PcView(PC pc, MundoView mundoView)
    {
        this.pc = pc;
        this.mundoView = mundoView;
        this.controlador = mundoView.controlador;
        this.notificador = new PCViewNotificador(pc.getConnectionID());
        this.mundo = mundoView.mundo;

        x = pc.getX();
        y = pc.getY();

        pc.añadirObservador(this);

        notificador.setPosition(x, y);
        notificador.setNombre(pc.getNombre());
        notificador.setHPs(pc.getActualHPs(), pc.getMaxHPs());

        quienMeVe();

        mapaView = new MapaView(pc, this, mundo, controlador);
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
        {   for (MobPC PCCercanos : listaPCsCercanos) controlador.enviarACliente(PCCercanos.getConnectionID(), notificador.dtoGlobal); }
    }

    // NOTIFICADORES
    //------------------------------------------------------------------------------------------------------------------

    //El notificador de posicion es especial, ya que cada vez que cambia la posicion se tiene que comprobar si hay nuevos players
    //cerca susceptibles de ser añadidos a la lista de PCsCercanos, que luego se usara para retransmitir las notificaciones globales
    private void setPosition (float posX, float posY)
    {
        x = posX; y = posY;
        notificador.setPosition(posX, posY);
        quienMeVe();
        mapaView.comprobarVistaMapa();
    }

    private void quienMeVe()
    {
        for (PcView pcCercanos : mundoView.listaPcViews)
        {
            MobPC pcCercano = pcCercanos.pc;

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
        //Dejamos de observar al model:
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
        if (evt.getNewValue() instanceof DTOsPC.Posicion)
        {   setPosition(((DTOsPC.Posicion) evt.getNewValue()).posX, ((DTOsPC.Posicion) evt.getNewValue()).posY); }

        if (evt.getNewValue() instanceof DTOsPC.Animacion)
        {   setAnimacion(((DTOsPC.Animacion) evt.getNewValue()).numAnimacion); }

        if (evt.getNewValue() instanceof DTOsPC.ModificarHPs)
        {   añadirModificarHPs(((DTOsPC.ModificarHPs) evt.getNewValue()).HPs); }

        if (evt.getNewValue() instanceof DTOsPC.SkillPersonalizado)
        {   añadirSpellPersonalizado(((DTOsPC.SkillPersonalizado) evt.getNewValue()).skillID);}


        if (evt.getNewValue() instanceof DTOsPC.Dispose)
        {   dispose(); }

        if (evt.getNewValue() instanceof DTOsPC.NumTalentosSkillPersonalizado)
        {   añadirModificarSkillTalento(
                ((DTOsPC.NumTalentosSkillPersonalizado) evt.getNewValue()).skillID,
                ((DTOsPC.NumTalentosSkillPersonalizado) evt.getNewValue()).statID,
                ((DTOsPC.NumTalentosSkillPersonalizado) evt.getNewValue()).valor);
        }
    }
}
