package Model.Classes.UI;// Created by Hanto on 06/05/2014.

import DTO.DTOsBarraAcciones;
import Interfaces.EntidadesPropiedades.CasterPersonalizable;
import Interfaces.Model.AbstractModel;
import Interfaces.UI.AccionI;
import Interfaces.UI.CasillaI;
import Interfaces.UI.BarraAccionesI;
import Model.Classes.Input.InputManager;
import com.badlogic.gdx.utils.Array;

public class BarraAcciones extends AbstractModel implements BarraAccionesI
{
    private int iD;
    private Array<Array<Casilla>> barraAcciones = new Array<>();
    private CasterPersonalizable caster;

    private InputManager inputManager;

    //SET:
    public void setID(int i)    { iD = i; }

    //GET:
    @Override public int getID()                                { return iD; }
    @Override public int getNumFilas()                          { return barraAcciones.size; }
    @Override public int getNumColumnas()                       { return (barraAcciones.size == 0) ? 0 : barraAcciones.first().size; }
    @Override public CasillaI getCasilla(int posX, int posY)    { return barraAcciones.get(posY).get(posX); }
    @Override public CasterPersonalizable getCaster()           { return caster; }

    //CONSTRUCTOR:
    public BarraAcciones(CasterPersonalizable caster, InputManager inputManager, int id, int numFilas, int numColumnas)
    {
        this.caster = caster;
        this.inputManager = inputManager;
        this.iD = id;

        for (int i=0; i<numFilas; i++)
        {
            Array<Casilla> array = new Array<>();
            for (int j=0; j<numColumnas; j++)
            {
                Casilla casilla = new Casilla(inputManager);
                casilla.setAccion((AccionI)null);

                array.add(casilla);
            }
            barraAcciones.add(array);
        }
    }






    @Override public void eliminarKeycode(int keycode)
    {
        for (int y=0; y< barraAcciones.size; y++)
        {
            for (int x=0; x< barraAcciones.get(y).size; x++)
            {
                if (barraAcciones.get(y).get(x).getKeycode() == keycode)
                {
                    barraAcciones.get(y).get(x).setKeycode(0);
                    barraAcciones.get(y).get(x).setKeybind("");
                }
            }
        }
    }


    //MODIFICACION TAMAÑO DE LA BARRA::

    @Override public void eliminar()
    {   eliminarFila(barraAcciones.size); }

    @Override public void eliminarFila(int numFilas)
    {
        for (int i=0; i<numFilas;i++)
            eliminarFila();

        Object eliminarFilaDTO = new DTOsBarraAcciones.EliminarFila(numFilas);
        notificarActualizacion("eliminarFila", null, eliminarFilaDTO);
    }

    @Override public void añadirFila(int numFilas)
    {
        for (int i=0; i<numFilas;i++)
            añadirFila();

        Object añadirFilaDTO = new DTOsBarraAcciones.AñadirFila(numFilas);
        notificarActualizacion("añadirFila", null, añadirFilaDTO);
    }

    @Override public void eliminarColumna(int numColumnas)
    {
        for (int i=0; i<numColumnas;i++)
            eliminarColumna();

        Object eliminarColumnaDTO = new DTOsBarraAcciones.EliminarColumna(numColumnas);
        notificarActualizacion("eliminarColumna", null, eliminarColumnaDTO);
    }

    @Override public void añadirColumna(int numColumnas)
    {
        for (int i=0; i<numColumnas;i++)
            añadirColumna();

        Object añadirColumnaDTO = new DTOsBarraAcciones.AñadirColumna(numColumnas);
        notificarActualizacion("añadirColumna", null, añadirColumnaDTO);
    }

    private void eliminarFila()
    {
        Array<Casilla> array = barraAcciones.peek();
        for (int i=0; i<array.size; i++)
        {   inputManager.eliminarKeybind(array.get(i).getKeycode()); }

        barraAcciones.removeIndex(barraAcciones.size-1);
    }

    private void añadirFila()
    {
        Array<Casilla> array = new Array<>();
        for (int i=0; i<barraAcciones.first().size; i++)
        {
            Casilla casilla = new Casilla(inputManager);
            casilla.setAccion((AccionI)null);
            array.add(casilla);
        }
        barraAcciones.add(array);
    }

    private void eliminarColumna()
    {
        for (int y=0; y<barraAcciones.size; y++)
        {   inputManager.eliminarKeybind(barraAcciones.get(y).pop().getKeycode());}
    }

    private void añadirColumna()
    {
        for (int y=0; y<barraAcciones.size; y++)
        {
            Casilla casilla = new Casilla(inputManager);
            barraAcciones.get(y).add(casilla);
        }
    }

}
