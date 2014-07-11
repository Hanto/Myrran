package View.GameState;// Created by Hanto on 14/05/2014.

import Controller.Controlador;
import DB.RSC;
import Data.Settings;
import Model.Classes.UI.BarraAcciones;
import Model.DTO.BarraAccionesDTO;
import Model.GameState.UI;
import View.Classes.Actores.Particula;
import View.Classes.Actores.Texto;
import View.Classes.UI.BarraAcciones.ConjuntoBarraAccionesView;
import View.Classes.UI.BarraTerrenos.BarraTerrenosView;
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

        RSC.particulaRecursoDAO.getParticulaRecursosDAO().crearPool("prueba", 10, 10);
        Particula par = RSC.particulaRecursoDAO.getParticulaRecursosDAO().obtain("prueba");
        par.setScale(0.1f);

        this.addActor(par);
        par.toFront();
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
