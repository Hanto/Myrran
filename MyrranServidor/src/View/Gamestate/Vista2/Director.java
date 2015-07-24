package View.Gamestate.Vista2;// Created by Hanto on 16/07/2015.

import Controller.Controlador;
import DTO.DTOsMundo;
import DTO.DTOsPC;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.ListaPorCuadrantesI;
import Interfaces.Model.ModelI;
import Model.GameState.Mundo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class Director implements PropertyChangeListener
{
    public Mundo mundo;
    public Controlador controlador;

    private ListaPorCuadrantesI<CampoVision> listaCampoVisiones = new ListaPorCuadrantes<>();
    private ListaPorCuadrantesI<MobPC> listaPCs = new ListaPorCuadrantes<>();

    //Constructor:
    public Director (Controlador controlador, Mundo mundo)
    {
        mundo.añadirObservador(this);
        this.controlador = controlador;
        this.mundo = mundo;
    }

    public void dispose()
    {   mundo.eliminarObservador(this); }


    public void añadirPC (MobPC mobPC)
    {
        mobPC.añadirObservador(this);
        listaPCs.put(mobPC);
    }

    public void disposePC (ModelI model)
    {
        model.eliminarObservador(this);
        listaPCs.remove((MobPC)model);
    }

    public void posicionPC (MobPC mobPC)
    {
        listaPCs.update(mobPC);
        Iterator iterator = listaCampoVisiones.getIteratorCuadrantes(mobPC.getMapTileX(), mobPC.getMapTileY());
    }


    @Override public void propertyChange (PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsMundo.NuevoPlayer)
        {
            MobPC mobPC = mundo.getPC(((DTOsMundo.NuevoPlayer)evt.getNewValue()).connectionID);
            añadirPC(mobPC);
        }

        if (evt.getNewValue() instanceof DTOsPC.Dispose)
        {   disposePC(((DTOsPC.Dispose) evt.getNewValue()).model); }

        if (evt.getNewValue() instanceof DTOsPC.Posicion)
        {    }
    }
}
