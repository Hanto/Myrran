package Model.Datos;// Created by Hanto on 06/08/2015.

import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesPropiedades.IDentificable;
import Interfaces.Misc.ListaPorCuadrantesI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class ListaMapaCuadrantes<T extends Espacial & IDentificable> implements Iterable<T>
{
    private ListaPorCuadrantesI<T> listaPorCuadrantes = new ListaPorCuadrantes<T>();
    private ListaMapa<T> listaMapa = new ListaMapa<>();

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public void put (T espacial)
    {
        listaPorCuadrantes.put(espacial);
        listaMapa.add(espacial);
    }

    public void add (T espacial)
    {   this.put(espacial); }

    public T remove (T espacial)
    {
        listaPorCuadrantes.remove(espacial);
        return listaMapa.remove(espacial);
    }

    public T remove (Integer iD)
    {
        T valor = listaMapa.get(iD);
        if (valor == null) {   logger.warn("ERROR: No se puede eliminar ID que no existe, ID:{}", iD); return null; }

        listaPorCuadrantes.remove(valor);
        return listaMapa.remove(iD);
    }

    public T get (Integer iD)
    {   return listaMapa.get(iD); }

    public int size()
    {   return listaMapa.size(); }

    public boolean contansKey(Integer iD)
    {   return listaMapa.containsKey(iD); }

    public void update (T espacial)
    {   listaPorCuadrantes.update(espacial); }

    public List<T> get (int mapTileX, int mapTileY)
    {   return get(mapTileX, mapTileY); }

    @Override public Iterator<T> iterator()
    {   return new IteratorListaMapa(); }

    public Iterator<T> getIteratorCuadrantes(int mapTileX, int mapTileY)
    {   return new IteratorCuadrantes(mapTileX, mapTileY); }




    // ITERATOR:
    //------------------------------------------------------------------------------------------------------------------

    private class IteratorListaMapa implements Iterator<T>
    {
        private Iterator<T> iterator;
        private T ultimoElemento;

        public IteratorListaMapa()
        {   iterator = listaMapa.iterator(); }

        @Override public boolean hasNext()
        {   return iterator.hasNext(); }

        @Override public T next()
        {
            ultimoElemento = iterator.next();
            return ultimoElemento;
        }

        @Override public void remove()
        {
            iterator.remove();
            listaPorCuadrantes.remove(ultimoElemento);
        }
    }

    private class IteratorCuadrantes implements Iterator<T>
    {
        private Iterator<T> iterator;
        private T ultimoElemento;

        public IteratorCuadrantes(int mapTileX, int mapTileY)
        {   iterator = listaPorCuadrantes.getIteratorCuadrantes(mapTileX, mapTileY); }

        @Override public boolean hasNext()
        {   return iterator.hasNext(); }

        @Override public T next()
        {
            ultimoElemento = iterator.next();
            return ultimoElemento;
        }

        @Override public void remove()
        {
            iterator.remove();
            listaMapa.remove(ultimoElemento);
        }
    }
}
