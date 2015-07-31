package Model.Classes.Misc;// Created by Hanto on 16/07/2015.

import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.Misc.ListaPorCuadrantesI;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class ListaPorCuadrantes<T extends Espacial> implements ListaPorCuadrantesI<T>
{
    //Estructura de datos que almacena Espaciales en un Hashmap agrupandolos por sectores segun el maptile en el que
    //se encuentren. Todos los Espaciles del MapTileX 0, y MaptileY 0 estaran en un mismo ArrayList, indexado por el
    //String 0-0. Esto permite acotar las busquedas por cercania, haciendolo solo en el subconjunto de los maptiles
    //adyacentes, para esto se implementa el IteratorCuadrantes, que itera las unidades de los cuadrantes adyacentes.

    private Map<String, List<T>> lista = new HashMap<>();
    //Se podria hacer con una Array[][] de ArrayList tambien

    private void put (String key, T valor)
    {
        if (lista.containsKey(key))
        {
            List<T> array = lista.get(key);

            if (!array.contains(valor))
            {
                array.add(valor);
                valor.setUltimoMapTile(valor.getMapTileX(), valor.getMapTileY());
            }
        }
        else
        {
            List<T> array = new ArrayList<>();
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
            List<T> array = lista.get(key);

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
            List<T> array = lista.get(oldKey);

            if (array.contains(espacial))
            {   array.remove(espacial); }
        }
        else if (lista.containsKey(key))
        {
            List<T> array = lista.get(key);

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

    // ITERATOR
    //------------------------------------------------------------------------------------------------------------------

    public class IteratorCuadrantes implements Iterator<T>
    {
        private int mapTileX;
        private int mapTileY;
        private int offsetActual = 0;
        private final Vector2[] offsets = new Vector2[9];
        private Iterator<T> iteratorListaActual;

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

            firstLista();
        }

        private void firstLista()
        {
            String key;

            while (offsetActual <=8)
            {
                key = (mapTileX+(int)offsets[offsetActual].x)+"-"+(mapTileY+(int)offsets[offsetActual].y);
                offsetActual++;

                if (lista.containsKey(key))
                {
                    if (!lista.get(key).isEmpty())
                    {   iteratorListaActual = lista.get(key).iterator(); break; }
                }
            }
        }

        private T nextLista()
        {
            String key;

            while (offsetActual <=8)
            {
                key = (mapTileX+(int)offsets[offsetActual].x)+"-"+(mapTileY+(int)offsets[offsetActual].y);
                offsetActual++;

                if (lista.containsKey(key))
                {
                    if (!lista.get(key).isEmpty())
                    {
                        iteratorListaActual = lista.get(key).iterator();
                        return iteratorListaActual.next();
                    }
                }
            }
            return null;
        }

        public T next()
        {
            if (iteratorListaActual.hasNext()) return iteratorListaActual.next();
            else return nextLista();
        }

        public boolean hasNext()
        {   return iteratorListaActual.hasNext(); }

        @Override public void remove()
        {   iteratorListaActual.remove(); }
    }
}
