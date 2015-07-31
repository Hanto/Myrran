package View.GameState;// Created by Hanto on 14/05/2014.

import Controller.Controlador;
import DB.RSC;
import DTO.DTOsBarraAcciones;
import Model.Settings;
import Interfaces.UI.BarraAcciones.BarraAccionesI;
import Model.GameState.UI;
import View.Classes.Actores.Texto;
import View.Classes.UI.BarraAccionesView.ConjuntoBarraAccionesView;
import View.Classes.UI.BarraTerrenosView.BarraTerrenosView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UIView extends Stage implements PropertyChangeListener
{
    protected Controlador controlador;

    protected ConjuntoBarraAccionesView conjuntoBarraAccionesView;
    protected BarraTerrenosView barraTerrenosView;
    protected Texto fps;

    public void setTextoFPS(String s)                           { fps.setTexto(s); }
    public void mostrarBarraTerreno()                           { barraTerrenosView.mostrarBarraTerrenos(); }
    public void ocultarBarraTerreno()                           { barraTerrenosView.ocultarBarraTerrenos(); }

    public UIView(Controlador controlador, UI ui)
    {
        this.controlador = controlador;
        conjuntoBarraAccionesView = new ConjuntoBarraAccionesView(this.controlador, this);
        barraTerrenosView = new BarraTerrenosView(this.controlador, this, ui.barraTerrenos);

        fps = new Texto("fps", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                        Color.WHITE, Color.BLACK, Align.left, Align.bottom, 2);
        addActor(fps);

        ui.conjuntoBarraAcciones.añadirObservador(this);
    }

    public void resize (int anchura, int altura)
    {   getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsBarraAcciones.AñadirBarraAcciones)
        {
            BarraAccionesI barraAcciones = ((DTOsBarraAcciones.AñadirBarraAcciones) evt.getNewValue()).barraAcciones;
            conjuntoBarraAccionesView.añadirBarraAccionesView(barraAcciones);
        }
    }
}
