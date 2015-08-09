package View.GameState;// Created by Hanto on 14/05/2014.

import DB.RSC;
import DTO.DTOsBarraAcciones;
import DTO.DTOsInputManager;
import Interfaces.Controlador.ControladorUI;
import Interfaces.Controlador.ControladorVistaI;
import Interfaces.Model.AbstractModelStage;
import Interfaces.UI.BarraAccionesI;
import Interfaces.UI.CasillaI;
import Model.GameState.UI;
import Model.Settings;
import View.Classes.Actores.Texto;
import View.Classes.UI.BarraAccionesView.ConjuntoBarraAccionesView;
import View.Classes.UI.BarraTerrenosView.BarraTerrenosView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UIView extends AbstractModelStage implements PropertyChangeListener, Disposable, ControladorUI
{
    protected ControladorVistaI controlador;
    protected UI ui;

    protected ConjuntoBarraAccionesView conjuntoBarraAccionesView;
    protected BarraTerrenosView barraTerrenosView;
    protected Texto fps;

    public void setTextoFPS(String s)                           { fps.setTexto(s); }

    public UIView(ControladorVistaI controlador, UI ui)
    {
        this.controlador = controlador;
        this.ui = ui;
        conjuntoBarraAccionesView = new ConjuntoBarraAccionesView(this, this);
        barraTerrenosView = new BarraTerrenosView(this, this, ui.getBarraTerrenos());

        fps = new Texto("fps", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                        Color.WHITE, Color.BLACK, Align.left, Align.bottom, 2);
        addActor(fps);

        ui.getConjuntoBarraAcciones().añadirObservador(this);
        ui.getInputManager().añadirObservador(this);
    }

    @Override public void dispose()
    {
        ui.getConjuntoBarraAcciones().eliminarObservador(this);
        ui.getInputManager().eliminarObservador(this);
    }

    public void resize (int anchura, int altura)
    {   getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }

    private void añadirBarraAccionesView(BarraAccionesI barraACciones)
    {   conjuntoBarraAccionesView.añadirBarraAccionesView(barraACciones); }

    private void aplicarZoom(int nivelZoom)
    {   controlador.aplicarZoom(nivelZoom); }

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

        //MODEL: OBSERVAMOS BARRA ACCIONES:
        else if (evt.getNewValue() instanceof DTOsBarraAcciones.AñadirBarraAcciones)
        {
            BarraAccionesI barraAcciones = ((DTOsBarraAcciones.AñadirBarraAcciones) evt.getNewValue()).barraAcciones;
            añadirBarraAccionesView(barraAcciones);
        }
    }

    //CONTROLADOR UI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirBarraAcciones(int filas, int columnas)
    {   ui.añadirBarraAcciones(filas, columnas); }

    @Override public void eliminarBarraAcciones(BarraAccionesI barra)
    {   ui.eliminarBarraAcciones(barra); }

    @Override public void barraAñadirColumna(BarraAccionesI barra, int numColumnas)
    {   barra.añadirColumna(numColumnas); }

    @Override public void barraAñadirFila(BarraAccionesI barra, int numFilas)
    {   barra.añadirFila(numFilas); }

    @Override public void barraEliminarColumna(BarraAccionesI barra, int numColumnas)
    {   barra.eliminarColumna(numColumnas); }

    @Override public void barraEliminarFila(BarraAccionesI barra, int numFilas)
    {   barra.eliminarFila(numFilas); }

    @Override public void barraAccionMoverAccion(CasillaI casillaOrigen, CasillaI casillaDestino)
    {   ui.moverAccion(casillaOrigen, casillaDestino); }

    @Override public void barraAccionRebindear(CasillaI casilla, int keycode)
    {   ui.rebindearCasilla(casilla, keycode); }

    @Override public void barraTerrenosMoverTerreno(int posOrigen, int posDestino)
    {   ui.barraTerrenosMoverTerreno(posOrigen, posDestino); }

    @Override public void mostrarBarraTerrenos()
    {   barraTerrenosView.mostrarBarraTerrenos(); }

    @Override public void ocultarBarraTerrenos()
    {   barraTerrenosView.ocultarBarraTerrenos(); }
}
