package View.Classes.UI.SpellView2.SkillSlotView;// Created by Hanto on 22/09/2015.

import DB.RSC;
import View.Classes.UI.SpellView2.DebuffSlotView;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class DebuffSlotSource extends DragAndDrop.Source
{
    private DragAndDrop dad;
    private DebuffSlotView debuffSlot;

    public DebuffSlotSource(DebuffSlotView debuffSlotView, DragAndDrop dad)
    {
        super(debuffSlotView.getIcono());
        this.debuffSlot = debuffSlotView;
        this.dad = dad;
    }

    @Override public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer)
    {
        if (debuffSlot.getSkillSlot().getSkill() != null)
        {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Actor dragActor = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
            dad.setDragActorPosition(-dragActor.getWidth() / 2, dragActor.getHeight() / 2);
            payload.setDragActor(dragActor);
            payload.setObject(debuffSlot);
            return payload;
        }
        return null;
    }
}
