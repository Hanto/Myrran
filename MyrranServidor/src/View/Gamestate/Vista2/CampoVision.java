package View.Gamestate.Vista2;  //Created by Hanto on 14/04/2015.

import Controller.Controlador;
import DTO.DTOsPC;
import Data.Settings;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesTipos.CampoVisionI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Model.AbstractModel;
import Model.GameState.Mundo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CampoVision extends AbstractModel implements PropertyChangeListener, CampoVisionI
{
    //Model:
    protected MundoView2 mundoView;
    protected Mundo mundo;
    protected Controlador controlador;
    protected CampoVisionNotificador notificador;
    protected MapaView2 mapaView;

    protected PCI pc;                                     //Player al que pertecene el Campo de Vision
    protected Espacial centro;                            //Espacial en el que esta centrado el campo de Vision

    //UNIDADES QUE OBSERVAMOS:
    private List<PCI> listaPCsCercanos = new ArrayList<>();

    @Override public float getX()                       { return centro != null ? centro.getX() : pc.getX(); }
    @Override public float getY()                       { return centro != null ? centro.getY() : pc.getY(); }
    @Override public int getMapTileX()                  { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                  { return (int)(getY() / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoMapTileX()            { return centro != null ? centro.getUltimoMapTileX() : pc.getUltimoMapTileX(); }
    @Override public int getUltimoMapTileY()            { return centro != null ? centro.getUltimoMapTileY() : pc.getUltimoMapTileY(); }
    @Override public void setUltimoMapTile(int x, int y){  }
    @Override public void setPosition(float x, float y) {  }
    @Override public void setCentro (Espacial espacial) { centro = espacial; }

    //Constructor:
    public CampoVision(Espacial centro, PCI pc, MundoView2 mundoView)
    {
        this.mundoView = mundoView;
        this.controlador = mundoView.controlador;
        this.mundo = mundoView.mundo;
        this.pc = pc;
        this.centro = centro;
        this.notificador = new CampoVisionNotificador();
        this.mapaView = new MapaView2(this.centro, this.pc.getConnectionID(), mundo, controlador, this);

        this.pc.añadirObservador(this);
    }

    @Override public void dispose()
    {   //Dejamos de observar al Player, las entidades cercanas y al Mapa
        pc.eliminarObservador(this);
        for (PCI pc : listaPCsCercanos)
        {   pc.eliminarObservador(this); }
        mapaView.dispose();
        //Eliminamos el campo de vision de la lista de campo de visiones:
        mundoView.eliminarCampoVision(this);
    }

    //CODIGO DE RADAR:
    //------------------------------------------------------------------------------------------------------------
    public boolean isVisiblePor(Espacial espacial)
    {
        if ( Math.abs(espacial.getX()-centro.getX()) <= (Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Horizontal_Resolution/2) &&
             Math.abs(espacial.getY()-centro.getY()) <= (Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Vertical_Resolution/2))
             return true;
        else return false;
    }

    public void comprobarVisiblidadMobsObservados()
    {
        Iterator<PCI> iterator = listaPCsCercanos.iterator();
        PCI pc;
        while (iterator.hasNext())
        {
            pc = iterator.next();
            if (!isVisiblePor(pc))
            {
                iterator.remove();
                pc.eliminarObservador(this);
                //NOTIFICAR CLIENTE:
                notificador.eliminarPC(pc);
            }
        }
    }

    public void radar()
    {
        comprobarVisiblidadMobsObservados();
        Iterator<PCI> iteratorPC = mundo.getMapaPCs().getIteratorCuadrantes(getMapTileX(), getMapTileY());
        PCI pc;
        while (iteratorPC.hasNext())
        {
            pc = iteratorPC.next();
            if (isVisiblePor(pc)) añadirPC(pc);
        }
    }

    //PLAYERS:
    //-------------------------------------------------------------------------------------------------------------
    public void añadirPC (PCI pc)
    {
        if (!listaPCsCercanos.contains(pc))
        {
            listaPCsCercanos.add(pc);
            pc.añadirObservador(this);
            notificador.añadirPC(pc);
        }
    }

    private void eliminarPC (PCI pc)
    {
        if (pc.getConnectionID() == this.pc.getConnectionID()) dispose();
        else if (listaPCsCercanos.contains(pc))
        {
            listaPCsCercanos.remove(pc);
            pc.eliminarObservador(this);
            notificador.eliminarPC(pc);
        }
    }

    private void posicionPC (PCI pc)
    {
        if (pc.getConnectionID() == this.pc.getConnectionID()) { mapaView.comprobarVistaMapa(); return; }
        notificador.setPositionPC(pc);
    }

    private void numAnimacionPC (PCI pc)
    {   notificador.setNumAnimacionPC(pc); }

    private void añadirSpellPersonalizadoPC (PCI pc, String spellID)
    {   notificador.addAñadirSpellPersonalizado(pc, spellID); }

    //CAMPO VISION:
    //--------------------------------------------------------------------------------------------------------------
    public void enviarDTOs()
    {   notificador.enviarDTOS(controlador, pc.getConnectionID()); }


    //CAMPOS OBSERVADOS:
    //--------------------------------------------------------------------------------------------------------------
    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //OBSERVAR A LOS PLAYERS (PC)
        if (evt.getNewValue() instanceof DTOsPC.EliminarPC)
        {   eliminarPC(((DTOsPC.EliminarPC) evt.getNewValue()).pc); }

        if (evt.getNewValue() instanceof DTOsPC.PosicionPC)
        {   posicionPC(((DTOsPC.PosicionPC) evt.getNewValue()).pc); }

        if (evt.getNewValue() instanceof DTOsPC.NumAnimacionPC)
        {   numAnimacionPC(((DTOsPC.NumAnimacionPC) evt.getNewValue()).pc); }

        if (evt.getNewValue() instanceof DTOsPC.AñadirSpellPersonalizadoPC)
        {   añadirSpellPersonalizadoPC(((DTOsPC.AñadirSpellPersonalizadoPC) evt.getNewValue()).pc,
                                       ((DTOsPC.AñadirSpellPersonalizadoPC) evt.getNewValue()).spellID);}
    }


}
