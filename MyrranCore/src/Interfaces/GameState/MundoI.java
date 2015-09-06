package Interfaces.GameState;// Created by Hanto on 30/07/2015.

import Interfaces.AI.SistemaAggroI;
import InterfacesEntidades.EntidadesTipos.MobI;
import InterfacesEntidades.EntidadesTipos.PCI;
import InterfacesEntidades.EntidadesTipos.ProyectilI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

public interface MundoI<PC extends PCI, Proyectil extends ProyectilI, Mob extends MobI> extends ModelI
{
    public MapaI getMapa();
    public World getWorld();
    public SistemaAggroI getAggro();

    //PCs:
    public void añadirPC (PC pc);
    public void eliminarPC (int connectionID);
    public PC getPC (int connectionID);
    public Iterator<PC> getIteratorPCs();
    public Iterator<PC> getIteratorPCs(int mapTileX, int mapTileY);

    //PROYECTIL:
    public void añadirProyectil(Proyectil proyectil);
    public void eliminarProyectil(int iD);
    public Proyectil getProyectil (int iD);
    public Iterator<Proyectil> getIteratorProyectiles();
    public Iterator<Proyectil> getIteratorProyectiles(int mapTileX, int mapTileY);

    //MOBS:
    public void añadirMob (Mob mob);
    public void eliminarMob (int iD);
    public Mob getMob (int iD);
    public Iterator<Mob> getIteratorMobs();
    public Iterator<Mob> getIteratorMobs(int mapTileX, int mapTileY);

    //IA:
    public void actualizarUnidades(float delta, MundoI mundo);
    public void actualizarFisica(float delta, MundoI mundo);
    public void checkColisiones();
    public void interpolarPosicion(float alpha);
}
