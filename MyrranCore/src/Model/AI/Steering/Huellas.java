package Model.AI.Steering;

import DTO.DTOsPC;
import Interfaces.EntidadesPropiedades.SteerableAgent;
import Model.Settings;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayDeque;
import java.util.Iterator;

public class Huellas implements PropertyChangeListener, Disposable
{
    public ArrayDeque<Huella> listaHuellas;
    private SteerableAgent steerable;

    public Huellas(SteerableAgent steerable)
    {
        this.steerable = steerable;
        this.listaHuellas = new ArrayDeque<>();
        this.steerable.añadirObservador(this);
    }

    @Override public void dispose()
    {   steerable.eliminarObservador(this); }

    //
    //------------------------------------------------------------------------------------------------------------------

    public void actualizar (float delta)
    {
        Huella huella;
        Iterator<Huella> iterator = listaHuellas.iterator();
        while (iterator.hasNext())
        {
            huella = iterator.next();
            huella.duracion += delta;
            if (huella.duracion > 20.0f) iterator.remove();
        }
    }

    private void añadirPosicion(int x, int y)
    {
        Huella huella = listaHuellas.peekFirst();
        if (mismoTile(x, y, huella)) huella.duracion = 0;
        else listaHuellas.addFirst(new Huella(x, y));
    }

    private boolean mismoTile (int x, int y, Huella huella)
    {
        if (huella == null) return false;
        if ( huella.punto.x / Settings.TILESIZE == x / Settings.TILESIZE &&
             huella.punto.y / Settings.TILESIZE == y / Settings.TILESIZE ) return true;
        else return false;
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPC.PosicionPC)
        {   añadirPosicion(((DTOsPC.PosicionPC) evt.getNewValue()).posX, ((DTOsPC.PosicionPC) evt.getNewValue()).posY); }

    }

    // HUELLA:
    //------------------------------------------------------------------------------------------------------------------

}
