package Interfaces.Misc.GameState;// Created by Hanto on 30/07/2015.

import Interfaces.Misc.AI.SistemaAggroI;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.Misc.Geo.MapaI;
import Interfaces.Misc.Observable.ModelI;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

public interface MundoI extends ModelI
{
    public MapaI getMapa();
    public World getWorld();
    public SistemaAggroI getAggro();

    //PCs:
    public void añadirPC (PCI pc);
    public void eliminarPC (int connectionID);
    public PCI getPC (int connectionID);
    public Iterator<PCI> getIteratorPCs();
    public Iterator<PCI> getIteratorPCs(int mapTileX, int mapTileY);

    //PROYECTIL:
    public void añadirProyectil(ProyectilI proyectil);
    public void eliminarProyectil(int iD);
    public ProyectilI getProyectil (int iD);
    public Iterator<ProyectilI> getIteratorProyectiles();
    public Iterator<ProyectilI> getIteratorProyectiles(int mapTileX, int mapTileY);

    //MOBS:
    public void añadirMob (MobI mob);
    public void eliminarMob (int iD);
    public MobI getMob (int iD);
    public Iterator<MobI> getIteratorMobs();
    public Iterator<MobI> getIteratorMobs(int mapTileX, int mapTileY);

    //IA:
    public void actualizarUnidades(float delta, MundoI mundo);
    public void actualizarFisica(float delta, MundoI mundo);
    public void checkColisiones();
    public void actualizarFisicaPorInterpolacion(float alpha);
}
