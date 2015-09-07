package View.GameState;// Created by Hanto on 14/05/2014.

import DB.RSC;
import DTOs.DTOsInputManager;
import Interfaces.Observable.AbstractModel;
import Interfaces.UI.BarraAccionesI;
import Model.Classes.Input.InputManager;
import Model.Settings;
import View.Classes.Actores.Texto;
import View.Classes.UI.BarraAccionesView.ConjuntoBarraAccionesView;
import View.Classes.UI.BarraTerrenosView.BarraTerrenosView;
import View.Classes.UI.UIViewController;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UIView extends AbstractModel implements PropertyChangeListener, Disposable
{
    protected InputManager inputManager;
    protected UIViewController uiController;

    protected Stage stage;
    protected ConjuntoBarraAccionesView conjuntoBarraAccionesView;
    protected BarraTerrenosView barraTerrenosView;
    protected Texto fps;

    public void setTextoFPS(String s)                           { fps.setTexto(s); }
    public Stage getStage()                                     { return stage; }
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public UIView(UIViewController uiController, InputManager inputManager,
                  ConjuntoBarraAccionesView conjuntoBarraAccionesView, BarraTerrenosView barraTerrenosView, Stage stage)
    {
        this.inputManager = inputManager;
        this.uiController = uiController;
        this.conjuntoBarraAccionesView = conjuntoBarraAccionesView;
        this.barraTerrenosView = barraTerrenosView;
        this.stage = stage;

        fps = new Texto("fps", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                        Color.WHITE, Color.BLACK, Align.left, Align.bottom, 2);
        stage.addActor(fps);

        inputManager.añadirObservador(this);
        uiController.añadirObservador(this);
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE: Liberando Stage:UIView");
        stage.dispose();
        conjuntoBarraAccionesView.dispose();
        barraTerrenosView.dispose();
        inputManager.eliminarObservador(this);
        uiController.eliminarObservador(this);
    }

    //STAGE:
    //------------------------------------------------------------------------------------------------------------------

    public void act(float delta)
    {   stage.act(delta); }

    public void draw()
    {   stage.draw(); }

    public void setDebugUnderMouse(boolean bool)
    {   stage.setDebugUnderMouse(bool);}

    public void resize (int anchura, int altura)
    {   stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }

    //VIEW:
    //------------------------------------------------------------------------------------------------------------------

    public void mostrarBarraTerrenos()
    {   barraTerrenosView.mostrarBarraTerrenos(); }

    public void ocultarBarraTerrenos()
    {   barraTerrenosView.ocultarBarraTerrenos(); }

    private void añadirBarraAccionesView(BarraAccionesI barraACciones)
    {   conjuntoBarraAccionesView.añadirBarraAccionesView(barraACciones); }

    private void aplicarZoom(int nivelZoom)
    {   notificarActualizacion("aplicarZoom", null, nivelZoom); }

    // CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //MODEL: OBSERVAMOS INPUT MANAGER:
        if (evt.getNewValue() instanceof DTOsInputManager.AplicarZoom)
        {   aplicarZoom(((DTOsInputManager.AplicarZoom) evt.getNewValue()).nivelZoom);}

        else if (evt.getNewValue() instanceof DTOsInputManager.MostrarBarraTerrenos)
        {   mostrarBarraTerrenos(); }

        else if (evt.getNewValue() instanceof DTOsInputManager.OcultarBarraTerrenos)
        {   ocultarBarraTerrenos(); }
    }
}
