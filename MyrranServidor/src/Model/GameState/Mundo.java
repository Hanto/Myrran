package Model.GameState;// Created by Hanto on 07/04/2014.

import DTOs.DTOsMundo;
import DTOs.DTOsEspacial;
import Interfaces.Misc.AI.ColisionMurosI;
import Interfaces.Misc.AI.ColisionProyectilesI;
import Interfaces.Misc.AI.SistemaAggroI;
import Interfaces.Misc.EstructurasDatos.QuadTreeI;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Geo.MapaI;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.AI.Behaviors.SteeringFactory.SteeringCompuestoFactory;
import Model.Classes.Mobiles.Mob.MobFactory;
import Model.EstructurasDatos.ListaMapaCuadrantes;
import com.badlogic.gdx.physics.box2d.World;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class Mundo extends AbstractModel implements PropertyChangeListener, MundoI
{

    protected ListaMapaCuadrantes<ProyectilI> dataProyectiles = new ListaMapaCuadrantes<>();
    protected ListaMapaCuadrantes<PCI> dataPCs = new ListaMapaCuadrantes<>();
    protected ListaMapaCuadrantes<MobI> dataMobs = new ListaMapaCuadrantes<>();

    private World world;
    private MapaI mapa;
    private QuadTreeI quadTree;
    private SistemaAggroI aggro;
    private ColisionMurosI colisionMuros;
    private ColisionProyectilesI colisionProyectiles;
    private int mobID;

    @Override public MapaI getMapa()                        { return mapa; }
    @Override public World getWorld()                       { return world; }
    @Override public SistemaAggroI getAggro()               { return aggro; }
    public int getMobID()                                   { return mobID++ > Integer.MAX_VALUE ? 0 : mobID; }


    public Mundo(World world, MapaI mapa, QuadTreeI quadTree, SistemaAggroI aggro, ColisionMurosI colisionMuros, ColisionProyectilesI colisionProyectiles)
    {
        this.world = world;
        this.mapa = mapa;
        this.quadTree = quadTree;
        this.aggro = aggro;
        this.colisionMuros = colisionMuros;
        this.colisionProyectiles = colisionProyectiles;
    }

    // PLAYERS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirPC (PCI pc)
    {
        dataPCs.add(pc);
        pc.añadirObservador(this);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);


        //TODO CODIGO PROVISIONAL PARA PROBAR:
        MobI mob = MobFactory.NUEVOMOB.nuevo(this);
                //MobFactory.NUEVO.nuevo(this);
        añadirMob(mob);
        mob.setSteeringBehavior(SteeringCompuestoFactory.WALL_PURSUE_LOOK.nuevo(mob, pc, this));
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


    @Override public void añadirMob (MobI mob)
    {
        dataMobs.add(mob);
        mob.añadirObservador(this);
    }

    @Override public void eliminarMob (int iD)
    {
        MobI mob = dataMobs.remove(iD);
        mob.eliminarObservador(this);
        mob.dispose();
    }

    @Override public MobI getMob (int iD)
    {   return dataMobs.get(iD); }

    @Override public Iterator<MobI> getIteratorMobs()
    {   return dataMobs.iterator(); }

    @Override public Iterator<MobI> getIteratorMobs(int mapTileX, int mapTileY)
    {   return dataMobs.getIteratorCuadrantes(mapTileX, mapTileY); }

    private void updateMob(MobI mob)
    {   dataMobs.update(mob); }

    //IA MUNDO:
    //------------------------------------------------------------------------------------------------------------------

    private void actualizarQuadTree()
    {
        quadTree.clear();

        //PCS:
        for (PCI pc : dataPCs)
        {   quadTree.add(pc); }
        //MOBS:
        for (MobI mob : dataMobs)
        {   quadTree.add(mob); }
        //PROYECTILES:
        //for (ProyectilI proyectil: dataProyectiles)
        //{   quadTree.add(proyectil); }
    }


    @Override public void actualizarUnidades(float delta, MundoI mundo)
    {
        //PCS:
        for (PCI pc : dataPCs)
        {   pc.actualizarTimers(delta);
            pc.actualizarIA(delta, mundo);
        }

        //MOBS:
        for (MobI mob : dataMobs)
        {   mob.actualizarTimers(delta); }

        //PROYECTILES:
        Iterator<ProyectilI>iterator = dataProyectiles.iterator(); ProyectilI pro;
        while (iterator.hasNext())
        {
            pro = iterator.next();
            if (pro.actualizarDuracion(delta))
            {
                iterator.remove();
                pro.eliminarObservador(this);
                pro.dispose();
            }
        }
    }

    //Salvamos los ultimos valores para poder interpolarlos
    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        //MOBS:
        for (MobI mob : dataMobs)
        {   mob.actualizarFisica(delta, mundo); }

        world.step(delta, 8, 6);

        //PROYECTILES:
        for (ProyectilI proyectil : dataProyectiles)
        {   proyectil.actualizarFisica(delta, mundo); }
    }

    @Override public void checkColisiones()
    {
        actualizarQuadTree();

        //PROYECTILES:
        for (ProyectilI proyectil : dataProyectiles)
        {
            colisionProyectiles.collides(proyectil);
            colisionMuros.collides(proyectil);
        }
    }

    @Override public void interpolarPosicion(float alpha) {}

    //CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsEspacial.Posicion)
        {
            Espacial espacial = ((DTOsEspacial.Posicion) evt.getNewValue()).espacial;
            if (espacial instanceof PCI) updatePC((PCI)espacial);
            else if (espacial instanceof MobI) updateMob((MobI)espacial);
            else if (espacial instanceof ProyectilI) updateProyectil((ProyectilI)espacial);
        }
    }
}
