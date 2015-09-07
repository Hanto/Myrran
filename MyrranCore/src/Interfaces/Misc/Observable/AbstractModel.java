package Interfaces.Misc.Observable;// Created by Hanto on 09/04/2014.

import Interfaces.Misc.Model.ModelI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractModel implements ModelI
{
    protected PropertyChangeSupport observado;

    public AbstractModel()
    {   observado = new PropertyChangeSupport(this); }

    @Override public void notificarActualizacion(String nombreValor, Object viejoValor, Object nuevoValor)
    {   observado.firePropertyChange(nombreValor, viejoValor, nuevoValor); }

    @Override public void añadirObservador(PropertyChangeListener observador)
    {   observado.addPropertyChangeListener(observador); }

    @Override public void eliminarObservador(PropertyChangeListener observador)
    {   observado.removePropertyChangeListener(observador); }

    @Override public void eliminarObservadores()
    {
        PropertyChangeListener observadores[] = observado.getPropertyChangeListeners();
        for (int i = 0; i < observadores.length; i++)
        {   observado.removePropertyChangeListener(observadores[i]); }
    }
}
