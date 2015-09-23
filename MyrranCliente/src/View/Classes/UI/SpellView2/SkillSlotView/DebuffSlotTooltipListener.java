package View.Classes.UI.SpellView2.SkillSlotView;// Created by Hanto on 21/09/2015.

import Interfaces.Misc.Controlador.ControladorSpellsI;
import View.Classes.UI.SpellView2.DebuffSlotView;
import View.Classes.UI.SpellView2.SpellView2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class DebuffSlotTooltipListener extends InputListener
{
    private DebuffSlotView debuffSlotView;
    private ControladorSpellsI controlador;
    private SpellView2 tooltip;

    public DebuffSlotTooltipListener(DebuffSlotView debuffSlotView, ControladorSpellsI controlador)
    {   this.debuffSlotView = debuffSlotView; }

    @Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
    {
        if (pointer <0)
        {
            if (debuffSlotView.getSkillSlot().getSkill() != null && tooltip == null)
            {
                tooltip = new SpellView2(debuffSlotView.getSkillSlot().getSkill(), controlador);
                event.getStage().addActor(tooltip);
                Vector2 clickPos = event.getListenerActor().localToStageCoordinates(new Vector2());
                tooltip.setPosition(clickPos.x+66, clickPos.y+32);
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
}
