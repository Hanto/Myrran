package View.Classes.UI.BarraAccionesView;// Created by Hanto on 08/05/2014.

import DB.RSC;
import DTOs.DTOsBarraAcciones;
import Interfaces.Misc.Controlador.ControladorBarraAccionI;
import Interfaces.Misc.UI.BarraAccionesI;
import Model.Classes.UI.ConjuntoBarraAcciones;
import Model.EstructurasDatos.ListaMapa;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ConjuntoBarraAccionesView implements PropertyChangeListener, Disposable
{
    private ControladorBarraAccionI controlador;
    private Stage stage;
    private ConjuntoBarraAcciones conjuntoBarraAcciones;

    private ListaMapa<BarraAccionesView> listaBarraAccionesView = new ListaMapa<>();
    private DragAndDrop dadAcciones = new DragAndDrop();
    private boolean rebindearSkills = false;

    public boolean getRebindearSkills()             { return rebindearSkills; }
    public DragAndDrop getDadAcciones()             { return dadAcciones; }

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ConjuntoBarraAccionesView(ControladorBarraAccionI controller, ConjuntoBarraAcciones conjuntoBarraAcciones, Stage stage)
    {
        this.controlador = controller;
        this.stage = stage;
        this.conjuntoBarraAcciones = conjuntoBarraAcciones;

        dadAcciones.setDragTime(0);
        crearBotonesRebind();

        this.conjuntoBarraAcciones.añadirObservador(this);
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE: Liberando ConjuntoBarraAccionesView");
        this.conjuntoBarraAcciones.eliminarObservador(this);
        for (BarraAccionesView barraAccionesView : listaBarraAccionesView)
        {   barraAccionesView.dispose(); }
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    public void añadirBarraAccionesView(BarraAccionesI barracciones)
    {
        BarraAccionesView barraAccionesView = new BarraAccionesView(barracciones, this, stage, controlador);
        listaBarraAccionesView.add(barraAccionesView);
    }

    private void eliminarBarraAccionesView(int barraAccionID)
    {
        BarraAccionesView barraAccionView = listaBarraAccionesView.remove(barraAccionID);
        stage.getRoot().removeActor(barraAccionView);
        barraAccionView.dispose();
    }

    private void crearBotonesRebind()
    {
        final Image rebindButtonOff = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonOFF));
        stage.addActor(rebindButtonOff);
        rebindButtonOff.setPosition(32,0);
        rebindButtonOff.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   //Switch para activar y desactivar el rebindeo de Skills
                rebindearSkills = false;
                rebindButtonOff.toBack();
                return true;
            }
        });

        final Image rebindButtonOn = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        stage.addActor(rebindButtonOn);
        rebindButtonOn.setPosition(32,0);
        rebindButtonOn.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   //Switch para activar y desactivar el rebindeo de Skills
                rebindearSkills = true;
                rebindButtonOn.toBack();
                return true;
            }
        });

        Image añadirBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        stage.addActor(añadirBarra);
        añadirBarra.setPosition(32+18,0);
        añadirBarra.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   controlador.añadirBarraAcciones(2, 5);
                return true;
            }
        });
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //MODEL: OBSERVAMOS BARRA ACCIONES:
        if (evt.getNewValue() instanceof DTOsBarraAcciones.AñadirBarraAcciones)
        {
            BarraAccionesI barraAcciones = ((DTOsBarraAcciones.AñadirBarraAcciones) evt.getNewValue()).barraAcciones;
            añadirBarraAccionesView(barraAcciones);
        }
        else if (evt.getNewValue() instanceof DTOsBarraAcciones.EliminarBarraAcciones)
        {   eliminarBarraAccionesView(((DTOsBarraAcciones.EliminarBarraAcciones) evt.getNewValue()).barraAccionID); }
    }
}
