package View.Gamestate.CampoVision;  //Created by Hanto on 14/04/2015.

import Controller.Controlador;
import DTO.DTOsPC;
import Model.Settings;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesTipos.CampoVisionI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Model.AbstractModel;
import Model.GameState.Mundo;
import View.Gamestate.MundoView;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CampoVision extends AbstractModel implements PropertyChangeListener, CampoVisionI
{
    protected MundoView mundoView;
    protected Mundo mundo;
    protected Controlador controlador;

    protected BufferCampoVision buffer;
    protected MapaView mapaView;
    protected targetLock targetLock;                    //target Espacial al que sigue el campo de vision
    protected int connectionID;                         //conexion a la que enviar los datos

    //UNIDADES QUE OBSERVAMOS:
    private List<PCI> listaPCsCercanos = new ArrayList<>();

    @Override public int getConnectionID()              { return connectionID; }
    @Override public float getX()                       { return targetLock.getEspacial().getX(); }
    @Override public float getY()                       { return targetLock.getEspacial().getY(); }
    @Override public int getMapTileX()                  { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                  { return (int)(getY() / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoMapTileX()            { return targetLock.getEspacial().getUltimoMapTileX(); }
    @Override public int getUltimoMapTileY()            { return targetLock.getEspacial().getUltimoMapTileY(); }
    @Override public void setUltimoMapTile(int x, int y){  }
    @Override public void setPosition(float x, float y) {  }

    //Constructor:
    public CampoVision(Espacial targetCampoVision, int connectionID, MundoView mundoView)
    {
        this.mundoView = mundoView;
        this.controlador = mundoView.controlador;
        this.mundo = mundoView.mundo;
        this.connectionID = connectionID;
        this.buffer = new BufferCampoVision();
        this.targetLock = new targetLock(targetCampoVision);
        this.mapaView = new MapaView(targetLock.getEspacial(), this.connectionID, mundo, controlador, this);
        radar();
    }

    @Override public void dispose()
    {
        for (PCI pc : listaPCsCercanos)
        {   pc.eliminarObservador(this); }
        mapaView.dispose();
        targetLock.dispose();
    }

    @Override public void setCentro (Espacial espacial)
    {
        for (PCI pc : listaPCsCercanos)
        {   pc.eliminarObservador(this); }
        listaPCsCercanos.clear();
        targetLock.setEspacial(espacial);
        radar();
    }

    //CODIGO DE RADAR:
    //-------------------------------------------------------------------------------------------------------------

    private boolean isVisiblePor(Espacial espacial)
    {
        if ( Math.abs(espacial.getX()-targetLock.getEspacial().getX()) <= (Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Horizontal_Resolution/2) &&
             Math.abs(espacial.getY()-targetLock.getEspacial().getY()) <= (Settings.NETWORK_DistanciaVisionMobs * Settings.MAPTILE_Vertical_Resolution/2))
             return true;
        else return false;
    }

    private void comprobarVisiblidadMobsObservados()
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
                buffer.eliminarPC(pc);
            }
        }
    }

    public void radar()
    {
        comprobarVisiblidadMobsObservados();
        PCI pc;
        Iterator<PCI> iteratorPC = mundo.getMapaPCs().getIteratorCuadrantes(getMapTileX(), getMapTileY());
        while (iteratorPC.hasNext())
        {
            pc = iteratorPC.next();
            if (isVisiblePor(pc)) añadirPC(pc);
        }
    }

    public void enviarDTOs()
    {   buffer.enviarDTOS(controlador, connectionID); }

    //PCS:
    //--------------------------------------------------------------------------------------------------------------

    public void añadirPC (PCI pc)
    {
        if (!listaPCsCercanos.contains(pc))
        {
            listaPCsCercanos.add(pc);
            pc.añadirObservador(this);
            buffer.setDatosCompletosPC(pc);
            if (pc.getConnectionID() != connectionID)
                buffer.setPositionPC(pc);
        }
    }

    private void eliminarPC (PCI pc)
    {
        if (listaPCsCercanos.contains(pc))
        {
            listaPCsCercanos.remove(pc);
            pc.eliminarObservador(this);
            if (pc.getConnectionID() != connectionID)
                buffer.eliminarPC(pc);
        }
    }

    private void posicionPC (PCI pc)
    {   if (pc.getConnectionID() != connectionID)
        buffer.setPositionPC(pc); }

    private void numAnimacionPC (PCI pc)
    {   if (pc.getConnectionID() != connectionID)
        buffer.setNumAnimacionPC(pc); }

    //PLAYER y PCs:
    //--------------------------------------------------------------------------------------------------------------

    private void modificarHPsPC (PCI pc, float hps)
    {   buffer.addModificarHPsPC(pc, hps); }

    private void añadirSpellPersonalizadoPC (PCI pc, String spellID)
    {   buffer.addAñadirSpellPersonalizado(pc, spellID); }

    private void numTalentosSkillPersonalizadoPC (PCI pc, String skillID, int statID, int valor)
    {   buffer.addNumTalentosSkillPersonalizadoPC(pc, skillID, statID, valor); }

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

        if (evt.getNewValue() instanceof DTOsPC.ModificarHPsPC)
        {   modificarHPsPC(((DTOsPC.ModificarHPsPC) evt.getNewValue()).pc,
                           ((DTOsPC.ModificarHPsPC) evt.getNewValue()).HPs); }

        if (evt.getNewValue() instanceof DTOsPC.AñadirSpellPersonalizadoPC)
        {   añadirSpellPersonalizadoPC(((DTOsPC.AñadirSpellPersonalizadoPC) evt.getNewValue()).pc,
                                       ((DTOsPC.AñadirSpellPersonalizadoPC) evt.getNewValue()).spellID);}

        if (evt.getNewValue() instanceof DTOsPC.NumTalentosSkillPersonalizadoPC)
        {   numTalentosSkillPersonalizadoPC(((DTOsPC.NumTalentosSkillPersonalizadoPC) evt.getNewValue()).pc,
                                            ((DTOsPC.NumTalentosSkillPersonalizadoPC) evt.getNewValue()).skillID,
                                            ((DTOsPC.NumTalentosSkillPersonalizadoPC) evt.getNewValue()).statID,
                                            ((DTOsPC.NumTalentosSkillPersonalizadoPC) evt.getNewValue()).valor);
        }
    }

    //TARGET LOCK
    //-------------------------------------------------------------------------------------------------------------
    //La observacion del Espacial que hace de referencia del campo de vision, el punto que el campo de vision sigue
    //se hace desde una inner class para que los eventos no se mezclen y no tengamos que diferenciarlos con IFs guarros

    private class targetLock implements PropertyChangeListener, Disposable
    {
        private Espacial espacial;

        public targetLock(Espacial espacial)
        {
            this.espacial = espacial;
            this.espacial.añadirObservador(this);
        }

        @Override public void dispose()
        {   espacial.eliminarObservador(this); }

        public Espacial getEspacial()
        {   return espacial; }

        public void setEspacial(Espacial espacial)
        {
            this.espacial.eliminarObservador(this);
            this.espacial = espacial;
            this.espacial.añadirObservador(this);
        }
        
        //REACCION A EVENTOS:
        //---------------------------------------------------------------------------------------------------------
        
        private void posicion()
        {   mapaView.comprobarVistaMapa(); }

        private void eliminar()
        {   if (mundo.getPC(connectionID) != null) setCentro(mundo.getPC(connectionID)); }

        @Override public void propertyChange(PropertyChangeEvent evt)
        {
            if (evt.getNewValue() instanceof DTOsPC.PosicionPC) { posicion(); }
            if (evt.getNewValue() instanceof DTOsPC.EliminarPC) { eliminar(); }
        }
    }
}
