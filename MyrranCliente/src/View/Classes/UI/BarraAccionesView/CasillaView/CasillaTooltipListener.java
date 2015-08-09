package View.Classes.UI.BarraAccionesView.CasillaView;// Created by Hanto on 13/07/2014.

import Interfaces.UI.AccionI;
import Model.Classes.Acciones.TiposAccion.SeleccionarSpell;
import View.Classes.UI.SpellView.SpellView;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class CasillaTooltipListener extends InputListener
{
    private CasillaView casillaView;
    private SpellView tooltip;

    public CasillaTooltipListener(CasillaView casillaView)
    {   this.casillaView = casillaView; }

    @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
    {
        if (pointer <0)
        {
            AccionI accion = casillaView.getAccion();

            if (accion instanceof SeleccionarSpell && tooltip == null)
            {
                tooltip = new SpellView(accion.getID(), casillaView.getCaster());
                casillaView.setTooltip(tooltip);
            }
        }
    }

    @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
    {
        if (pointer <0)
        {
            casillaView.setTooltip(null);
            if (tooltip != null) { tooltip.dispose(); tooltip = null; }
        }
    }

    @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
    {
        if (button == Input.Buttons.RIGHT && tooltip != null)
        {
            AccionI accion = casillaView.getAccion();

            if (accion instanceof SeleccionarSpell)
            {
                SpellView tooltipMovible = new SpellView(accion.getID(), casillaView.getCaster());
                event.getStage().addActor(tooltipMovible);
                Vector2 clickPos = getPosicionClick(event, x, y);
                tooltipMovible.setPosition(clickPos.x +16 , clickPos.y +16 -tooltipMovible.getHeight());
            }
        }
        return true;
    }

    private Vector2 getPosicionClick(InputEvent event, float x, float y)
    {
        //Calculamos la posicion del raton, con programacion eslovaca:
        Vector2 clickPos = new Vector2(x, y);
        event.getListenerActor().localToStageCoordinates(clickPos);
        return clickPos;
    }
}
