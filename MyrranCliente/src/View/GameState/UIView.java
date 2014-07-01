package View.GameState;// Created by Hanto on 14/05/2014.

import Controller.Controlador;
import Data.MiscData;
import Model.Classes.UI.BarraAcciones;
import Model.DTO.BarraAccionesDTO;
import Model.GameState.UI;
import DB.RSC;
import View.Classes.Graficos.Texto;
import View.Classes.UI.BarraTerrenos.BarraTerrenosView;
import View.Classes.UI.BarraAcciones.ConjuntoBarraAccionesView;
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

        fps = new Texto("fps", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(MiscData.FUENTE_Nombres),
                        Color.WHITE, Color.BLACK, Align.left, Align.bottom, 2);
        addActor(fps);

        ui.conjuntoBarraAcciones.añadirObservador(this);
    }

    public void resize (int anchura, int altura)
    {   getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof BarraAccionesDTO.AñadirBarraAcciones)
        {
            BarraAcciones barraAcciones = ((BarraAccionesDTO.AñadirBarraAcciones) evt.getNewValue()).barraAcciones;
            conjuntoBarraAccionesView.añadirBarraAccionesView(barraAcciones);
        }
    }
}
