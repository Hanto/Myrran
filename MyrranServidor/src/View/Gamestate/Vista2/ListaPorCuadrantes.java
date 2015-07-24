package View.Gamestate.Vista2;// Created by Hanto on 16/07/2015.

import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.ListaPorCuadrantesI;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class ListaPorCuadrantes<T extends Espacial> implements ListaPorCuadrantesI<T>
{
    //Se podria hacer con una Array[][] de ArrayList tambien
    private Map<String, ArrayList<T>> lista = new HashMap<>();


    private void put (String key, T valor)
    {
        if (lista.containsKey(key))
        {
            ArrayList<T> array = lista.get(key);

            if (!array.contains(valor))
            {
                array.add(valor);
                valor.setUltimoMapTile(valor.getMapTileX(), valor.getMapTileY());
            }
        }
        else
        {
            ArrayList<T> array = new ArrayList<>();
            array.add(valor);
            lista.put(key, array);
            valor.setUltimoMapTile(valor.getMapTileX(), valor.getMapTileY());
        }
    }

    @Override public void put (T espacial)
    {
        String key = espacial.getMapTileX()+ "-" + espacial.getMapTileY();
        put(key, espacial);
    }

    private void remove (String key, T espacial)
    {
        if (lista.containsKey(key))
        {
            ArrayList<T> array = lista.get(key);

            if (array.contains(espacial))
            {
                array.remove(espacial);
                //Parte discutile para ahorrar memoria a costa de rendimiento, cuando el arraylist contenedor queda
                //vacio, se elimina para que no ocupe espacio, ya se volvera a crear si hace falta.
                if (array.isEmpty())
                {   lista.remove(key); }
            }
        }
    }

    @Override public void remove (T espacial)
    {
        String key = espacial.getMapTileX()+ "-" + espacial.getMapTileY();
        String oldKey =  espacial.getUltimoMapTileX()+ "-" + espacial.getUltimoMapTileY();

        if (lista.containsKey(oldKey))
        {
            ArrayList<T> array = lista.get(oldKey);

            if (array.contains(espacial))
            {   array.remove(espacial); }
        }
        else if (lista.containsKey(key))
        {
            ArrayList<T> array = lista.get(key);

            if (array.contains(espacial))
            {   array.remove(espacial); }
        }
    }

    @Override public void update (T espacial)
    {
        if ( espacial.getMapTileX() != espacial.getUltimoMapTileX() ||
             espacial.getMapTileY() != espacial.getUltimoMapTileY() )
        {
            String oldKey = espacial.getUltimoMapTileX()+ "-" + espacial.getUltimoMapTileY();
            remove(oldKey, espacial);

            String newKey = espacial.getMapTileX()+ "-" + espacial.getMapTileY();
            put(newKey, espacial);
        }
    }

    @Override public List<T> get (int mapTileX, int mapTileY)
    {
        String key = mapTileX+ "-"+ mapTileY;

        if (lista.containsKey(key))
        {   return lista.get(key); }
        else return null;
    }

    @Override public Iterator<T> getIteratorCuadrante(int mapTileX, int mapTileY)
    {
        String key = mapTileX+ "-"+ mapTileY;

        if (lista.containsKey(key))
        {   return lista.get(key).iterator();}
        else return null;
    }

    @Override public Iterator<T> getIteratorCuadrantes(int mapTileX, int mapTileY)
    {   return new IteratorCuadrantes(mapTileX, mapTileY); }

    @Override public int size()
    {   return lista.size(); }

    @Override public boolean isEmpty()
    {   return lista.isEmpty(); }

    @Override public boolean containsKey(String key)
    {   return lista.containsKey(key); }

    @Override public void clear()
    {   lista.clear(); }




    public class IteratorCuadrantes implements Iterator<T>
    {
        private int mapTileX;
        private int mapTileY;
        private int offsetActual = 0;
        private List<T> listaActual;
        private final Vector2[] offsets = new Vector2[9];

        public IteratorCuadrantes(int mapTileX, int mapTileY)
        {
            this.mapTileX = mapTileX;
            this.mapTileY = mapTileY;
            this.offsets[0] = new Vector2(-1,-1);
            this.offsets[1] = new Vector2(-1, 0);
            this.offsets[2] = new Vector2(-1,+1);
            this.offsets[3] = new Vector2( 0,-1);
            this.offsets[4] = new Vector2( 0, 0);
            this.offsets[5] = new Vector2( 0,+1);
            this.offsets[6] = new Vector2(+1,-1);
            this.offsets[7] = new Vector2(+1, 0);
            this.offsets[8] = new Vector2(+1,+1);

            nextLista();
        }

        private void nextLista()
        {
            String key;
            listaActual = null;

            while (offsetActual <=8)
            {
                key = (mapTileX+offsets[offsetActual].x)+"-"+(mapTileY+offsets[offsetActual].y);
                offsetActual++;

                if (lista.containsKey(key))
                {
                    if (!lista.get(key).isEmpty())
                    {
                        listaActual = lista.get(key);
                        break;
                    }
                }
            }
        }

        @Override public T next()
        {
            if (listaActual == null) return null;

            T next = listaActual.iterator().next();
            if (!listaActual.iterator().hasNext()) nextLista();
            return next;
        }

        @Override public boolean hasNext()
        {   return listaActual == null ? false : listaActual.iterator().hasNext(); }

        @Override public void remove()  {}
    }
}
