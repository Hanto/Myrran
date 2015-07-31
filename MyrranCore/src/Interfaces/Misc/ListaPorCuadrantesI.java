package Interfaces.Misc;// Created by Hanto on 24/07/2015.

import Interfaces.EntidadesPropiedades.Espacial;

import java.util.Iterator;
import java.util.List;

public interface ListaPorCuadrantesI<T extends Espacial>
{
    public void put (T espacial);
    public void remove (T espacial);
    public void update (T espacial);
    public List<T> get (int mapTileX, int mapTileY);
    public Iterator<T> getIteratorCuadrante(int mapTileX, int mapTileY);
    public Iterator<T> getIteratorCuadrantes(int mapTileX, int mapTileY);
    public int size();
    public boolean isEmpty();
    public boolean containsKey(String key);
    public void clear();
}
