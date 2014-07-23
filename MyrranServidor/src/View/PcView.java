package View;// Created by Hanto on 07/04/2014.

import Controller.Controlador;
import DTO.NetDTO;
import DTO.NetPCServidor;
import Data.Settings;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PcView implements PropertyChangeListener
{
    //Model:
    private PC pc;
    private Vista vista;
    private Mundo mundo;
    private Controlador controlador;

    //Datos:
    private List<MobPC> listaPCsCercanos = new ArrayList<>();
    private float x;
    private float y;

    private MapaView mapaView;
    private NetPCServidor netPCServidor;

    //Constructor:
    public PcView(PC pc, Vista vista)
    {
        this.pc = pc;
        this.vista = vista;
        this.controlador = vista.controlador;
        this.netPCServidor = new NetPCServidor(pc.getConnectionID());
        this.mundo = vista.mundo;

        x = pc.getX();
        y = pc.getY();

        mundo.getMapa().añadirObservador(this);
        pc.añadirObservador(this);

        netPCServidor.setPosition(x, y);
        netPCServidor.setNombre(pc.getNombre());

        quienMeVe();

        mapaView = new MapaView(pc, mundo, controlador);
    }

    public void enviarDatosAClientes()
    {
        netPCServidor.getDTOs();
        if (netPCServidor.contieneDatosDTOPersonal()) actualizarPlayer(netPCServidor.dtoPersonal);
        if (netPCServidor.contieneDatosDTOGlobal() && isVisible()) actualizarPlayersCercanos(netPCServidor.dtoGlobal);
    }
    private void actualizarPlayersCercanos (Object obj)
    {
        for (MobPC PCCercanos : listaPCsCercanos)
            controlador.enviarACliente(PCCercanos.getConnectionID(), obj);
    }
    private void actualizarPlayer (Object obj)
    {   controlador.enviarACliente(pc.getConnectionID(), obj); }

    private boolean isVisible()
    {   return !listaPCsCercanos.isEmpty(); }

    private void setPosition (float posX, float posY)
    {
        x = posX; y = posY;
        netPCServidor.setPosition(posX, posY);
        quienMeVe();
        mapaView.comprobarVistaMapa();
    }

    private void quienMeVe()
    {
        for (PcView pcCercanos : vista.listaPcViews)
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
            netPCServidor.añadirCrearPC(pcview.pc);
        }
    }

    private void eliminarPCVisible (PcView pcView)
    {
        if (listaPCsCercanos.contains(pcView.pc))
        {
            listaPCsCercanos.remove(pcView.pc);
            netPCServidor.añadirtEliminarPC(pcView.pc.getConnectionID());
        }
    }




    private void setAnimacion(int numAnimacion)
    {   netPCServidor.setNumAnimacion(numAnimacion); }
    private void modificarSkillTalento(String skillID, int statID, int valor)
    {   netPCServidor.añadirNumTalentosSkillPersonalizado(skillID, statID, valor); }
    private void añadirSpellPersonalizado(String spellID)
    {
        pc.getSpellPersonalizado(spellID).añadirObservador(this);
        netPCServidor.añadirSkillPersonalizado(spellID);
    }
    private void modificarHPs(float HPs)
    {   netPCServidor.añadirModificarHPs(HPs); }

    private void eliminar(NetDTO.EliminarPPC eliminarPPC)
    {
        //Dejamos de observar al mundo colindante por cambios (para la edicion de terreno):
        mundo.getMapa().eliminarObservador(this);
        //Dejamos de observar al pc:
        pc.eliminarObservador(this);
        //Dejamos de observar a cada uno de los Spells Personalizados del pc:
        Iterator<SpellPersonalizadoI> iSpell = pc.getIteratorSpellPersonalizado();
        while (iSpell.hasNext()) { iSpell.next().eliminarObservador(this); }
        //eliminamos la vista y transmitimos la informacion al resto de clientes:
        vista.listaPcViews.remove(this);
        actualizarPlayersCercanos(eliminarPPC);
    }



    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //MOBILES:
        if (evt.getNewValue() instanceof NetDTO.PosicionPPC)
        {
            x = ((NetDTO.PosicionPPC) evt.getNewValue()).x;
            y = ((NetDTO.PosicionPPC) evt.getNewValue()).y;
            setPosition(x, y);
        }
        if (evt.getNewValue() instanceof NetDTO.ModificarHPsPPC)
        {   modificarHPs(((NetDTO.ModificarHPsPPC) evt.getNewValue()).HPs); }

        if (evt.getNewValue() instanceof NetDTO.EliminarPPC)
        {   eliminar((NetDTO.EliminarPPC)evt.getNewValue()); }

        if (evt.getNewValue() instanceof NetDTO.AñadirSpellPersonalizadoPPC)
        {   añadirSpellPersonalizado(((NetDTO.AñadirSpellPersonalizadoPPC) evt.getNewValue()).spellID);}

        if (evt.getNewValue() instanceof NetDTO.ModificarNumTalentosSkillPersonalizadoPPC)
        {   modificarSkillTalento(((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) evt.getNewValue()).skillID,
                ((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) evt.getNewValue()).statID,
                ((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) evt.getNewValue()).valor);
        }

        if (evt.getNewValue() instanceof NetDTO.AnimacionPPC)
        {   setAnimacion(((NetDTO.AnimacionPPC) evt.getNewValue()).numAnimacion); }

        //TERRENOS:
        if (evt.getNewValue() instanceof NetDTO.SetTerreno)
        {
            int celdaX = ((NetDTO.SetTerreno) evt.getNewValue()).celdaX;
            int celdaY = ((NetDTO.SetTerreno) evt.getNewValue()).celdaY;
            int numCapa = ((NetDTO.SetTerreno) evt.getNewValue()).numCapa;
            short iDTerreno = ((NetDTO.SetTerreno) evt.getNewValue()).iDTerreno;
            mapaView.cambioTerreno(celdaX, celdaY, numCapa, iDTerreno);
        }
    }
}
