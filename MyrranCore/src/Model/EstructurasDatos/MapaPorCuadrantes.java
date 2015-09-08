package Model.EstructurasDatos;// Created by Hanto on 16/07/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.Misc.EstructurasDatos.ListaPorCuadrantesI;
import Model.Settings;

import java.util.*;

public class MapaPorCuadrantes<T extends Espacial & IDentificable> implements ListaPorCuadrantesI<T>
{
    //Estructura de datos que almacena Espaciales en un Hashmap agrupandolos por sectores segun el maptile en el que
    //se encuentren. Todos los Espaciles del MapTileX 0, y MaptileY 0 estaran en un mismo ArrayList, indexado por el
    //String 0-0. Esto permite acotar las busquedas por cercania, haciendolo solo en el subconjunto de los maptiles
    //adyacentes, para esto se implementa el IteratorCuadrantes, que itera las unidades de los cuadrantes adyacentes.
    //Se podria hacer con una Array[][] de ArrayList tambien

    private static final Punto[] offsets = new Punto[]
            { new Punto(-1,+1), new Punto( 0,+1), new Punto(+1,+1),
              new Punto(-1, 0), new Punto( 0, 0), new Punto(+1, 0),
              new Punto(-1,-1), new Punto( 0,-1), new Punto(+1,-1) };
    private Map<Integer, List<T>> mapa = new HashMap<>();

    private void put (Integer key, T valor)
    {
        //MAPA:
        if (mapa.containsKey(key))
        {
            List<T> array = mapa.get(key);

            if (!array.contains(valor))
            {
                array.add(valor);
                valor.setUltimoMapTile(valor.getCuadranteX(), valor.getCuadranteY());
            }
        }
        else
        {
            List<T> array = new ArrayList<>();
            array.add(valor);
            mapa.put(key, array);
            valor.setUltimoMapTile(valor.getCuadranteX(), valor.getCuadranteY());
        }
    }

    @Override public void put (T espacial)
    {
        //String key = espacial.getCuadranteX()+ "," + espacial.getCuadranteY();
        int key = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());

        put(key, espacial);
    }

    private void remove (int key, T espacial)
    {
        //MAPA:
        if (mapa.containsKey(key))
        {
            List<T> array = mapa.get(key);

            //Parte discutile para ahorrar memoria a costa de rendimiento, cuando el arraylist contenedor queda
            //vacio, se elimina para que no ocupe espacio, ya se volvera a crear si hace falta.
            array.remove(espacial);
            if (array.isEmpty()) mapa.remove(array);
        }
    }

    @Override public void remove (T espacial)
    {
        //String key = espacial.getCuadranteX()+ "," + espacial.getCuadranteY();
        //String oldKey =  espacial.getUltimoCuadranteX()+ "," + espacial.getUltimoCuadranteY();
        int key = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());
        int oldKey = generarKey(espacial.getUltimoCuadranteX(), espacial.getUltimoCuadranteY());


        //MAPA:
        if (mapa.containsKey(oldKey))
        {
            List<T> array = mapa.get(oldKey);

            array.remove(espacial);
            if (array.isEmpty()) mapa.remove(array);
        }
        else if (mapa.containsKey(key))
        {
            List<T> array = mapa.get(key);

            array.remove(espacial);
            if (array.isEmpty()) mapa.remove(array);
        }
    }

    @Override public void update (T espacial)
    {
        if ( espacial.getCuadranteX() != espacial.getUltimoCuadranteX() ||
             espacial.getCuadranteY() != espacial.getUltimoCuadranteY() )
        {
            //String oldKey = espacial.getUltimoCuadranteX()+ "," + espacial.getUltimoCuadranteY();
            int oldKey = generarKey(espacial.getUltimoCuadranteX(), espacial.getUltimoCuadranteY());
            remove(oldKey, espacial);

            //String newKey = espacial.getCuadranteX()+ "," + espacial.getCuadranteY();
            int newKey = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());
            put(newKey, espacial);
        }
    }

    @Override public List<T> get (int mapTileX, int mapTileY)
    {
        //String key = mapTileX+ ","+ mapTileY;
        int key = generarKey(mapTileX, mapTileY);

        if (mapa.containsKey(key))
        {   return mapa.get(key); }
        else return null;
    }

    @Override public Iterator<T> getIteratorCuadrante(int mapTileX, int mapTileY)
    {
        //String key = mapTileX+ ","+ mapTileY;
        int key = generarKey(mapTileX, mapTileY);

        if (mapa.containsKey(key))
        {   return mapa.get(key).iterator();}
        else return null;
    }

    @Override public Iterator<T> getIteratorCuadrantes(int mapTileX, int mapTileY)
    {   return new IteratorCuadrantes(mapTileX, mapTileY); }

    @Override public int size()
    {   return mapa.size(); }

    @Override public boolean isEmpty()
    {   return mapa.isEmpty(); }

    @Override public boolean containsKey(int key)
    {   return mapa.containsKey(key); }

    @Override public void clear()
    {   mapa.clear(); }

    private int generarKey(int mapTileX, int mapTileY)
    {   return mapTileX * (Settings.MAPA_Max_TilesY / Settings.MAPTILE_NumTilesY) + mapTileY; }

    // ITERATOR
    //------------------------------------------------------------------------------------------------------------------

    public class IteratorCuadrantes implements Iterator<T>
    {
        private int mapTileX;
        private int mapTileY;
        private int offsetActual = 0;
        private Iterator<T> iterator;

        public IteratorCuadrantes(int mapTileX, int mapTileY)
        {
            this.mapTileX = mapTileX;
            this.mapTileY = mapTileY;

            firstLista();
        }

        private void firstLista()
        {
            //String key;
            int key;

            while (offsetActual <=8)
            {
                //key = (mapTileX+(int)offsets[offsetActual].x)+","+(mapTileY+(int)offsets[offsetActual].y);
                key = generarKey(mapTileX+offsets[offsetActual].x, mapTileY+offsets[offsetActual].y);
                offsetActual++;

                if (mapa.containsKey(key))
                {
                    if (!mapa.get(key).isEmpty())
                    {   iterator = mapa.get(key).iterator(); break; }
                }
            }
        }

        private T nextLista()
        {
            //String key;
            int key;

            while (offsetActual <=8)
            {
                //key = (mapTileX+(int)offsets[offsetActual].x)+","+(mapTileY+(int)offsets[offsetActual].y);
                key = generarKey(mapTileX+offsets[offsetActual].x, mapTileY+offsets[offsetActual].y);
                offsetActual++;

                if (mapa.containsKey(key))
                {
                    if (!mapa.get(key).isEmpty())
                    {
                        iterator = mapa.get(key).iterator();
                        return iterator.next();
                    }
                }
            }
            return null;
        }

        private boolean hasNextLista()
        {
            //String key;
            int key;

            for (int i=offsetActual; i<=8; i++)
            {
                //key = (mapTileX+(int)offsets[i].x)+","+(mapTileY+(int)offsets[i].y);
                key = generarKey(mapTileX+offsets[i].x, mapTileY+offsets[i].y);
                if (mapa.containsKey(key))
                {
                    if (!mapa.get(key).isEmpty())
                        return true;
                }
            }
            return false;
        }

        public T next()
        {
            if (iterator.hasNext()) return iterator.next();
            else return nextLista();
        }

        public boolean hasNext()
        {
            if (iterator == null) return false;
            else
            {
                if (iterator.hasNext()) return true;
                else return hasNextLista();
            }

        }

        @Override public void remove()
        {   iterator.remove(); }
    }
}
