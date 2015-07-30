package View.Gamestate.Vista2;// Created by Hanto on 16/07/2015.

import Controller.Controlador;
import DTO.DTOsMundo;
import Interfaces.EntidadesTipos.CampoVisionI;
import Interfaces.EntidadesTipos.PCI;
import Model.GameState.Mundo;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MundoView2 implements PropertyChangeListener, Disposable
{
    protected Controlador controlador;
    protected Mundo mundo;

    protected List<CampoVisionI> listaCampoVisiones = new ArrayList<>();


    //Constructor:
    public MundoView2(Controlador controlador, Mundo mundo)
    {
        mundo.añadirObservador(this);
        this.controlador = controlador;
        this.mundo = mundo;
    }

    @Override public void dispose()
    {   mundo.eliminarObservador(this); }

    public void añadirCampoVision(PCI pc)
    {
        CampoVisionI campoVision = new CampoVision(pc, pc.getConnectionID(), this);
        listaCampoVisiones.add(campoVision);
    }

    public void eliminarCampoVision(int connectionID)
    {
        CampoVisionI campoVision;
        Iterator<CampoVisionI>iterator = listaCampoVisiones.iterator();
        while (iterator.hasNext())
        {
            campoVision = iterator.next();
            if (campoVision.getConnectionID() == connectionID)
            {
                campoVision.dispose();
                iterator.remove();
            }
        }
    }

    public void radar()
    {
        for (CampoVisionI campoVision : listaCampoVisiones)
        {   campoVision.radar(); }
    }

    public void enviarDTOs()
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

        if (evt.getNewValue() instanceof DTOsMundo.EliminarPC)
        {   eliminarCampoVision(((DTOsMundo.EliminarPC) evt.getNewValue()).pc.getConnectionID());}
    }
}
