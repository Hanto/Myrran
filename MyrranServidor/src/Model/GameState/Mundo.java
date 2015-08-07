package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.DTOsMundo;
import DTO.DTOsPC;
import DTO.DTOsProyectil;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Datos.ListaMapaCuadrantes;
import Model.Settings;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class Mundo extends AbstractModel implements PropertyChangeListener, MundoI
{
    protected ListaMapaCuadrantes<ProyectilI> dataProyectiles = new ListaMapaCuadrantes<>();
    protected ListaMapaCuadrantes<PCI> dataPCs = new ListaMapaCuadrantes<>();

    private Mapa mapa = new Mapa();
    private World world;
    @Override public MapaI getMapa()                        { return mapa; }
    @Override public World getWorld()                       { return world; }


    public Mundo()
    {
        world = new World(new Vector2(0,0), false);

        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   mapa.setTerreno(x,y,0,(short)0); }
        }
    }

    //PLAYERS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirPC (PCI pc)
    {
        dataPCs.add(pc);
        pc.añadirObservador(this);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    @Override public void eliminarPC (int connectionID)
    {
        PCI pc = dataPCs.remove(connectionID);
        pc.eliminarObservador(this);
        pc.dispose();

        DTOsMundo.EliminarPC eliminarPlayer = new DTOsMundo.EliminarPC(pc);
        notificarActualizacion("eliminarPC", null, eliminarPlayer);
    }

    @Override public PCI getPC(int connectionID)
    {   return dataPCs.get(connectionID); }

    @Override public Iterator<PCI> getIteratorPCs()
    {   return dataPCs.iterator(); }

    @Override public Iterator<PCI> getIteratorPCs(int mapTileX, int mapTileY)
    {   return dataPCs.getIteratorCuadrantes(mapTileX, mapTileY); }

    private void updatePC(PCI pc)
    {   dataPCs.update(pc); }

    //PROYECTILES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirProyectil(ProyectilI proyectil)
    {
        dataProyectiles.add(proyectil);
        proyectil.añadirObservador(this);
    }

    @Override public void eliminarProyectil(int iD)
    {
        ProyectilI proyectil = dataProyectiles.remove(iD);
        proyectil.eliminarObservador(this);
        proyectil.dispose();
    }

    @Override public ProyectilI getProyectil (int iD)
    {   return dataProyectiles.get(iD); }

    @Override public Iterator<ProyectilI> getIteratorProyectiles()
    {   return dataProyectiles.iterator(); }

    @Override public Iterator<ProyectilI> getIteratorProyectiles(int mapTileX, int mapTileY)
    {   return dataProyectiles.getIteratorCuadrantes(mapTileX, mapTileY); }

    private void updateProyectil(ProyectilI proyectil)
    {   dataProyectiles.update(proyectil); }

    //IA MUNDO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarUnidades(float delta)
    {   //PCs
        for (PCI pc : dataPCs)
        {   pc.actualizar(delta); }
        //PROYECTILES:
        Iterator<ProyectilI>iterator = dataProyectiles.iterator(); ProyectilI pro;
        while (iterator.hasNext())
        {
            pro = iterator.next();
            if (pro.consumirse(delta))
            {
                iterator.remove();
                pro.eliminarObservador(this);
                pro.dispose();
            }
        }
    }

    //Salvamos los ultimos valores para poder interpolarlos
    @Override public void actualizarFisica(float delta)
    {
        //PROYECTILES:
        for (ProyectilI proyectil : dataProyectiles)
        {   proyectil.copiarUltimaPosicion(); }

        world.step(delta, 8, 6);
    }

    @Override public void interpolarPosicion(float alpha)
    {
        //PROYECTILES:
        for (ProyectilI proyectil : dataProyectiles)
        {   proyectil.interpolarPosicion(alpha); }
    }

    //CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPC.PosicionPC)
        {   updatePC(((DTOsPC.PosicionPC) evt.getNewValue()).pc); }

        if (evt.getNewValue() instanceof DTOsProyectil.PosicionProyectil)
        {   updateProyectil(((DTOsProyectil.PosicionProyectil) evt.getNewValue()).proyectil); }
    }
}
