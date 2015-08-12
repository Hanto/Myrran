package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.DTOsMob;
import DTO.DTOsMundo;
import DTO.DTOsPC;
import DTO.DTOsProyectil;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.AbstractModel;
import Model.Classes.AI.Steering.SteeringFactory;
import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.Mob.Mob;
import Model.Classes.Mobiles.Mob.MobFactory;
import Model.Datos.ListaMapaCuadrantes;
import Model.Settings;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.physics.box2d.World;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class Mundo extends AbstractModel implements PropertyChangeListener, MundoI
{
    protected ListaMapaCuadrantes<ProyectilI> dataProyectiles = new ListaMapaCuadrantes<>();
    protected ListaMapaCuadrantes<PCI> dataPCs = new ListaMapaCuadrantes<>();
    protected ListaMapaCuadrantes<MobI> dataMobs = new ListaMapaCuadrantes<>();

    private Mapa mapa;
    private World world;
    private int mobID;

    @Override public MapaI getMapa()                        { return mapa; }
    @Override public World getWorld()                       { return world; }
    public int getMobID()                                   { return mobID++ > Integer.MAX_VALUE ? 0 : mobID; }


    public Mundo(World world, Mapa mapa)
    {
        this.world = world;
        this.mapa = mapa;

        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   mapa.setTerreno(x,y,0,(short)0); }
        }
    }

    // PLAYERS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirPC (PCI pc)
    {
        dataPCs.add(pc);
        pc.añadirObservador(this);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);

        Mob mob = MobFactory.NUEVO.nuevo(this);
        añadirMob(mob);

        Arrive ster = (Arrive)SteeringFactory.Steering2.ARRIVE.nuevo(mob, pc);
        ster.setDecelerationRadius(0.2f);
        //ster.setDecelerationRadius(20f);
        mob.setSteeringBehavior(ster);
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

    // PROYECTILES:
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

    // MOBS:
    //------------------------------------------------------------------------------------------------------------------

    public void añadirMob (MobI mob)
    {
        dataMobs.add(mob);
        mob.añadirObservador(this);
    }

    public void eliminarMob (int iD)
    {
        MobI mob = dataMobs.remove(iD);
        mob.eliminarObservador(this);
        mob.dispose();
    }

    public MobI getMob (int iD)
    {   return dataMobs.get(iD); }

    public Iterator<MobI> getIteratorMobs()
    {   return dataMobs.iterator(); }

    public Iterator<MobI> getIteratorMobs(int mapTileX, int mapTileY)
    {   return dataMobs.getIteratorCuadrantes(mapTileX, mapTileY); }

    private void updateMob(MobI mob)
    {   dataMobs.update(mob); }

    //IA MUNDO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarUnidades(float delta, MundoI mundo)
    {   // PCS:
        for (PCI pc : dataPCs)
        {   pc.actualizar(delta, mundo); }
        // MOBS:
        for (MobI mob : dataMobs)
        {   mob.actualizar(delta, mundo); }
        // PROYECTILES:
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

        else if (evt.getNewValue() instanceof DTOsProyectil.PosicionProyectil)
        {   updateProyectil(((DTOsProyectil.PosicionProyectil) evt.getNewValue()).proyectil); }

        else if (evt.getNewValue() instanceof DTOsMob.PosicionMob)
        {   updateMob(((DTOsMob.PosicionMob) evt.getNewValue()).mob); }
    }
}
