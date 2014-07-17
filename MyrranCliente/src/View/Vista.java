package View;// Created by Hanto on 08/04/2014.

import Controller.Controlador;
import DB.RSC;
import Model.GameState.Mundo;
import Model.GameState.UI;
import Tweens.TweenEng;
import View.GameState.MundoView;
import View.GameState.UIView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static Data.Settings.FIXED_TimeStep;

public class Vista
{
    private Controlador controlador;
    private Mundo mundo;
    private UI ui;

    private MundoView mundoView;
    private UIView uiView;
    private float timeStep = 0f;

    public UIView getUiView()       { return uiView; }
    public MundoView getMundoView() { return mundoView; }

    public Vista (Controlador controlador, UI ui, Mundo mundo)
    {
        this.controlador = controlador;
        this.ui = ui;
        this.mundo = mundo;

        mundoView = new MundoView(controlador, mundo.getPlayer(), mundo);
        uiView = new UIView(controlador, ui);
    }

    public void render (float delta)
    {
        Gdx.gl.glClearColor(0/2.55f, 0/2.55f, 0/2.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //para evitar la Spiral of Death:
        if (delta > 2.0f) delta = 2.0f;

        timeStep += delta;
        while (timeStep >= FIXED_TimeStep)
        {
            mundo.actualizarFisica(FIXED_TimeStep);
            mundo.actualizarUnidades(FIXED_TimeStep);
            timeStep -= FIXED_TimeStep;
        }
        mundo.interpolacionEspacial(timeStep / FIXED_TimeStep);

        TweenEng.getTweenManager().update(delta);

        mundoView.act(delta);
        mundoView.draw();

        uiView.act(delta);
        uiView.draw();

        uiView.setTextoFPS(Integer.toString(Gdx.graphics.getFramesPerSecond()) + "fps");

        Table.drawDebug(uiView);
    }

    public void resize (int anchura, int altura)
    {
        mundoView.resize(anchura, altura);
        uiView.resize(anchura, altura);
    }

    public void dispose ()
    {
        mundoView.dispose();
        uiView.dispose();
        RSC.atlasRecursosDAO.getAtlasRecursosDAO().dispose();
        RSC.fuenteRecursosDAO.getFuentesRecursosDAO().dispose();
        RSC.particulaRecursoDAO.getParticulaRecursosDAO().dispose();
    }

    public void aplicarZoom(int incrementoZoom)
    {   mundoView.aplicarZoom(incrementoZoom); }
}
