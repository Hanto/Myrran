package View.Classes.UI.SpellView2.SkillSlotView;// Created by Hanto on 21/09/2015.

import Interfaces.Misc.Controlador.ControladorSpellsI;
import View.Classes.UI.SpellView2.DebuffSlotView;
import View.Classes.UI.SpellView2.SpellView2;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class DebuffSlotTooltipListener extends InputListener
{
    private DebuffSlotView debuffSlotView;
    private ControladorSpellsI controlador;
    private DragAndDrop dad;

    private SpellView2 tooltip;

    public DebuffSlotTooltipListener(DebuffSlotView debuffSlotView, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.debuffSlotView = debuffSlotView;
        this.controlador = controlador;
        this.dad = dad;
    }

    @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
    {
        if (pointer <0)
        {
            if (debuffSlotView.getSkillSlot().getSkill() != null && tooltip == null)
            {
                tooltip = new SpellView2(debuffSlotView.getSkillSlot().getSkill(), controlador, dad);
                event.getStage().addActor(tooltip);
                Vector2 clickPos = event.getListenerActor().localToStageCoordinates(new Vector2());
                tooltip.setPosition(clickPos.x+32, clickPos.y+50-34);
            }
        }
    }

    @Override public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor)
    {
        if (pointer <0)
        {
            if (tooltip != null)
            {
                tooltip.dispose();
                tooltip.getStage().getRoot().removeActor(tooltip);
                tooltip = null;
            }
        }
    }

    @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
    {
        if (button == Input.Buttons.RIGHT)
        {
            //TODO hacerlo a traves del controlador:8
            debuffSlotView.getSkillSlot().setSkill(null);
            this.exit(event, x, y, pointer, null);
        }
        return true;
    }
}
