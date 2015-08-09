package View.GameState;// Created by Hanto on 08/04/2014.

import DB.RSC;
import Interfaces.Controlador.ControladorVistaI;
import Model.GameState.Mundo;
import Model.GameState.UI;
import Tweens.TweenEng;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;

public class Vista implements ControladorVistaI, Disposable
{
    private Mundo mundo;
    private UI ui;

    private MundoView mundoView;
    private UIView uiView;

    public UIView getUiView()       { return uiView; }
    public MundoView getMundoView() { return mundoView; }

    public Vista (UI ui, Mundo mundo)
    {
        this.ui = ui;
        this.mundo = mundo;

        mundoView = new MundoView(mundo.getPlayer(), mundo);
        uiView = new UIView(this, ui);
    }

    @Override public void dispose ()
    {
        mundoView.dispose();
        uiView.dispose();
        RSC.atlasRecursosDAO.getAtlasRecursosDAO().dispose();
        RSC.fuenteRecursosDAO.getFuentesRecursosDAO().dispose();
        RSC.particulaRecursoDAO.getParticulaRecursosDAO().dispose();
    }

    public void render (float delta)
    {
        Gdx.gl.glClearColor(0/2.55f, 0/2.55f, 0/2.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        TweenEng.getTweenManager().update(delta);
        mundoView.act(delta);
        mundoView.draw();

        uiView.act(delta);
        uiView.draw();

        uiView.setTextoFPS(Integer.toString(Gdx.graphics.getFramesPerSecond()) + "fps");
        uiView.setDebugUnderMouse(true);
    }

    public void resize (int anchura, int altura)
    {
        mundoView.resize(anchura, altura);
        uiView.resize(anchura, altura);
    }

    @Override public void aplicarZoom(int incrementoZoom)
    {   mundoView.aplicarZoom(incrementoZoom); }
}
