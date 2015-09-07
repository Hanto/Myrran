package Model.EstructurasDatos;// Created by Hanto on 06/08/2015.

import InterfacesEntidades.EntidadesPropiedades.Misc.IDentificable;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ListaMapa<T extends IDentificable> implements Iterable<T>
{
    private List<T>lista = new ArrayList<>();
    private Map<Integer, T>mapa = new HashMap<>();

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public void add (T iDentificable)
    {
        if (mapa.containsKey(iDentificable.getID()))
        {   logger.warn("ERROR: No se puede añadir entidad, ID ya existe: Entidad:{} ID:{}", iDentificable, iDentificable.getID()); return;}

        mapa.put(iDentificable.getID(), iDentificable);
        lista.add(iDentificable);
    }

    public T remove (Integer iD)
    {
        if (!mapa.containsKey(iD))
        {   logger.warn("ERROR: No se puede eliminar ID que no existe, ID:{}", iD); return null;}

        T valor = mapa.remove(iD);
        lista.remove(valor);
        return valor;
    }

    public T remove (T iDentificable)
    {
        if (!mapa.containsKey(iDentificable.getID()))
        {   logger.warn("ERROR: No se puede eliminar ID que no se ha añadido: Entidad:{} ID:{}", iDentificable, iDentificable.getID()); return null;}

        lista.remove(iDentificable);
        return mapa.remove(iDentificable.getID());
    }

    public T get (Integer iD)
    {   return mapa.get(iD); }

    public int size ()
    {   return lista.size(); }

    public boolean containsKey(Integer iD)
    {   return mapa.containsKey(iD); }

    @Override public Iterator<T> iterator()
    {   return new IteratorListaMapa(); }


    // ITERATOR:
    //------------------------------------------------------------------------------------------------------------------

    private class IteratorListaMapa implements Iterator<T>
    {
        private Iterator<T> iterator;
        private T ultimoElemento;

        public IteratorListaMapa()
        {   iterator = lista.iterator(); }

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
            mapa.remove(ultimoElemento.getID());
        }
    }

}
