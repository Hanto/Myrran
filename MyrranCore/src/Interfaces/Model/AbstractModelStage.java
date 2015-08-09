package Interfaces.Model;// Created by Hanto on 09/08/2015.

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AbstractModelStage extends Stage implements ModelI
{
    protected PropertyChangeSupport observado;

    public AbstractModelStage()
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
