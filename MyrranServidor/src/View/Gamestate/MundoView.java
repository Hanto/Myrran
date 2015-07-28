package View.Gamestate;// Created by Hanto on 07/04/2014.

import Controller.Controlador;
import DTO.DTOsMundo;
import Interfaces.EntidadesTipos.PCI;
import Model.GameState.Mundo;
import View.Gamestate.Vistas.PcView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MundoView implements PropertyChangeListener
{
    public Controlador controlador;
    public Mundo mundo;

    public List<PcView> listaPcViews = new ArrayList<>();

    //Constructor:
    public MundoView(Controlador controlador, Mundo mundo)
    {
        mundo.añadirObservador(this);
        this.controlador = controlador;
        this.mundo = mundo;
    }

    public void enviarDatosAClientes()
    {
        for (PcView pcView: listaPcViews)
        {
            pcView.generarDTOs();
            pcView.enviarDatosPersonales();
        }

        for (PcView pcView: listaPcViews)
            pcView.enviarDatosGlobales();
    }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsMundo.AñadirPC)
        {
            PCI pc = ((DTOsMundo.AñadirPC) evt.getNewValue()).pc;
            PcView pcView = new PcView(pc, this);
            listaPcViews.add(pcView);
        }
    }
}
