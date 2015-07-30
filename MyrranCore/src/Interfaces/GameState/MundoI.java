package Interfaces.GameState;// Created by Hanto on 30/07/2015.

import Interfaces.EntidadesTipos.PCI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.ModelI;

import java.util.Iterator;

public interface MundoI extends ModelI
{
    public MapaI getMapa();
    public PCI getPC (int connectionID);
    public Iterator<PCI> getIteratorListaPCs();
}
