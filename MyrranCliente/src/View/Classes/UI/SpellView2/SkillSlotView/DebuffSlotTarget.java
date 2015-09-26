package View.Classes.UI.SpellView2.SkillSlotView;// Created by Hanto on 22/09/2015.

import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.BDebuffI;
import View.Classes.UI.SpellView2.DebuffSlotView;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

public class DebuffSlotTarget extends DragAndDrop.Target
{
    private DebuffSlotView debuffSlot;
    private DragAndDrop dad;
    private ControladorSpellsI controlador;

    public DebuffSlotTarget(DebuffSlotView debuffSlot, DragAndDrop dad, ControladorSpellsI controlador)
    {
        super(debuffSlot.getIconoSlot());
        this.debuffSlot = debuffSlot;
        this.dad = dad;
        this.controlador = controlador;
    }

    @Override public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v2, int i)
    {   return true; }

    @Override public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload)
    {   super.reset(source, payload); }

    @Override public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v2, int i)
    {
        //TODO poner este codigo en el controlador:
        DebuffSlotView debuffSlotOrigen = ((DebuffSlotView)payload.getObject());

        if ( debuffSlotOrigen.getSkillSlot().abreLaCerradura(debuffSlot.getSkillSlot().getSkill()) &&
             debuffSlot.getSkillSlot().abreLaCerradura(debuffSlotOrigen.getSkillSlot().getSkill()))
        {
            BDebuffI skill = debuffSlotOrigen.getSkillSlot().getSkill();
            debuffSlotOrigen.getSkillSlot().setSkill(debuffSlot.getSkillSlot().getSkill());
            debuffSlot.getSkillSlot().setSkill(skill);
        }
    }
}
