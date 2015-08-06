package Interfaces.GameState;// Created by Hanto on 30/07/2015.

import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;

public interface MundoI extends ModelI
{
    public MapaI getMapa();
    public World getWorld();
    public PCI getPC (int connectionID);
    public Iterator<PCI> getIteratorListaPCs();

    public void añadirProyectil(ProyectilI proyectil);
    public void eliminarProyectil(ProyectilI proyectil);
}
