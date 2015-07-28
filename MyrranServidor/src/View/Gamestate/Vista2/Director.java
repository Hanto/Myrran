package View.Gamestate.Vista2;// Created by Hanto on 16/07/2015.

import Controller.Controlador;
import DTO.DTOsMundo;
import Interfaces.EntidadesTipos.CampoVisionI;
import Interfaces.EntidadesTipos.PCI;
import Model.GameState.Mundo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Director implements PropertyChangeListener
{
    public Controlador controlador;
    public Mundo mundo;

    private List<CampoVisionI> listaCampoVisiones = new ArrayList<>();

    //Constructor:
    public Director (Controlador controlador, Mundo mundo)
    {
        mundo.añadirObservador(this);
        this.controlador = controlador;
        this.mundo = mundo;
    }

    //CAMPO VISION:
    //--------------------------------------------------------------------------------------------------------------
    public void añadirCampoVision(PCI pc)
    {
        CampoVisionI campoVision = new CampoVision(pc, pc, this);
        listaCampoVisiones.add(campoVision);
    }

    public void eliminarCampoVision(CampoVisionI campoVision)
    {   listaCampoVisiones.remove(campoVision); }

    public void radar()
    {
        for (CampoVisionI campoVision : listaCampoVisiones)
        {   campoVision.radar(); }
    }

    public void enviarDatosAClientes()
    {
        for (CampoVisionI campoVision : listaCampoVisiones)
        {   campoVision.enviarDTOs(); }
    }

    //CAMPOS OBSERVADOS:
    //-------------------------------------------------------------------------------------------------------------
    @Override public void propertyChange (PropertyChangeEvent evt)
    {
        //OBSERVAR MUNDO:
        if (evt.getNewValue() instanceof DTOsMundo.AñadirPC)
        {   añadirCampoVision(((DTOsMundo.AñadirPC) evt.getNewValue()).pc); }
    }
}
