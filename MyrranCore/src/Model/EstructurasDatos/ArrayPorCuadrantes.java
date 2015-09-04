package Model.EstructurasDatos;

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Espacial;
import InterfacesEntidades.EntidadesPropiedades.IDentificable;
import Interfaces.EstructurasDatos.ListaPorCuadrantesI;
import Model.Settings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayPorCuadrantes<T extends Espacial & IDentificable> implements ListaPorCuadrantesI<T>
{
    private static final Punto[] offsets = new Punto[]
            { new Punto(-1,+1), new Punto( 0,+1), new Punto(+1,+1),
              new Punto(-1, 0), new Punto( 0, 0), new Punto(+1, 0),
              new Punto(-1,-1), new Punto( 0,-1), new Punto(+1,-1) };
    private List<T>[] array;
    private int numCuadrantesX;
    private int numCuadrantesY;


    public ArrayPorCuadrantes()
    {
        numCuadrantesX = Settings.MAPA_Max_TilesX / Settings.MAPTILE_NumTilesX;
        numCuadrantesY = Settings.MAPA_Max_TilesY / Settings.MAPTILE_NumTilesY;

        array = new List[numCuadrantesX * numCuadrantesY];
    }


    private void put (Integer key, T valor)
    {
        if (containsKey(key))
        {
            List<T> lista = array[key];
            if (!lista.contains(valor))
            {
                lista.add(valor);
                valor.setUltimoMapTile(valor.getCuadranteX(), valor.getCuadranteY());
            }
        }
        else
        {
            List<T> lista = new ArrayList<>();
            lista.add(valor);
            array[key] = lista;
            valor.setUltimoMapTile(valor.getCuadranteX(), valor.getCuadranteY());
        }
    }

    @Override public void put(T espacial)
    {
        int key = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());
        put(key, espacial);
    }

    private void remove (int key, T espacial)
    {
        if (containsKey(key))
        {
            List<T> lista = array[key];
            lista.remove(espacial);
            if (lista.isEmpty()) array[key] = null;
        }
    }

    @Override public void remove(T espacial)
    {
        int key = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());
        int oldKey = generarKey(espacial.getUltimoCuadranteX(), espacial.getUltimoCuadranteY());

        if (containsKey(oldKey))
        {
            List<T>lista = array[oldKey];
            lista.remove(espacial);
            if (lista.isEmpty()) array[oldKey] = null;
        }
        else if (containsKey(key))
        {
            List<T>lista = array[key];
            lista.remove(espacial);
            if (lista.isEmpty()) array[key] = null;
        }
    }

    @Override public void update(T espacial)
    {
        if ((espacial.getCuadranteX() != espacial.getUltimoCuadranteX() ||
             espacial.getCuadranteY() != espacial.getUltimoCuadranteY()) &&
             espacial.getCuadranteX() >=0 && espacial.getCuadranteX() < numCuadrantesX &&
             espacial.getCuadranteY() >=0 && espacial.getCuadranteX() < numCuadrantesY)
        {
            int oldKey = generarKey(espacial.getUltimoCuadranteX(), espacial.getUltimoCuadranteY());
            int newKey = generarKey(espacial.getCuadranteX(), espacial.getCuadranteY());

            remove(oldKey, espacial);
            put(newKey, espacial);
        }
    }

    private List<T> get(int key)
    {   return array[key]; }

    @Override public List<T> get(int mapTileX, int mapTileY)
    {
        int key = generarKey(mapTileX, mapTileY);
        return array[key];
    }

    @Override public Iterator<T> getIteratorCuadrante(int mapTileX, int mapTileY)
    {
        int key = generarKey(mapTileX, mapTileY);
        if (containsKey(key)) return array[key].iterator();
        return null;
    }

    @Override public Iterator<T> getIteratorCuadrantes(int mapTileX, int mapTileY)
    {   return new IteratorCuadrantes(mapTileX, mapTileY); }

    @Override public int size()
    {   return array.length; }

    @Override public boolean isEmpty()
    {
        for (List<T> lista : array)
        {   if (lista != null) return false; }
        return true;
    }

    @Override public boolean containsKey(int key)
    {
        if (key <0 || key >= numCuadrantesX*numCuadrantesY || array[key] == null) return false;
        else return true;
    }

    @Override public void clear()
    {
        for (int i=0; i<array.length; i++)
        {   array[i] = null; }
    }

    private int generarKey(int mapTileX, int mapTileY)
    {   return mapTileX * numCuadrantesY + mapTileY; }

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
            int key;

            while (offsetActual <=8)
            {
                key = generarKey(mapTileX+offsets[offsetActual].x, mapTileY+offsets[offsetActual].y);
                offsetActual++;

                if (containsKey(key))
                {
                    if (!get(key).isEmpty())
                    {   iterator = get(key).iterator(); break; }
                }
            }
        }

        private T nextLista()
        {
            int key;

            while (offsetActual <=8)
            {
                key = generarKey(mapTileX+offsets[offsetActual].x, mapTileY+offsets[offsetActual].y);
                offsetActual++;

                if (containsKey(key))
                {
                    if (!get(key).isEmpty())
                    {
                        iterator = get(key).iterator();
                        return iterator.next();
                    }
                }
            }
            return null;
        }

        private boolean hasNextLista()
        {
            int key;

            for (int i=offsetActual; i<=8; i++)
            {
                key = generarKey(mapTileX+offsets[i].x, mapTileY+offsets[i].y);
                if (containsKey(key))
                {
                    if (!get(key).isEmpty())
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
