package View.Classes.UI.SpellView2;// Created by Hanto on 22/09/2015.

import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.SpellI;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class DebuffsSlotView extends Table implements Disposable, PropertyChangeListener
{
    private SpellI skill;
    private ControladorSpellsI controlador;
    private DragAndDrop dad;
    public List<DebuffSlotView> listaDebuffs = new ArrayList<>();

    public DebuffsSlotView(SpellI skill, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.dad = dad;

        this.bottom().left();
        this.defaults();
        this.setTransform(false);

        crearView();
        crearTabla();
    }

    public void dispose()
    {
        for (DebuffSlotView debuffSlot : listaDebuffs)
        {   debuffSlot.dispose(); }
    }

    private void crearView()
    {
        for (int iD = 0; iD < skill.getDebuffSlots().getNumSlots(); iD++)
        {
            listaDebuffs.add(new DebuffSlotView(skill, iD, controlador, dad));
            skill.getDebuffSlots().getSlot(iD).añadirObservador(this);
        }
    }

    private void crearTabla()
    {
        for (DebuffSlotView debuffSlot : listaDebuffs)
        {
            this.add(debuffSlot.getIcono()).left().bottom();
            this.row();
            //this.add(debuffSlot.stats).left().bottom().padBottom(4);
            //this.row();
        }
    }

    private void recrearTabla()
    {
        this.clear();
        for (DebuffSlotView debuffSlot : listaDebuffs)
        {
            this.add(debuffSlot.getIcono()).height(32).left().bottom();
            this.row();
            this.add(debuffSlot.stats).left().bottom().padBottom(4);
            this.row();
        }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {

        if (evt.getNewValue() instanceof DTOsSkill.SetSkillSlot)
        {
            recrearTabla();
        }
    }
}
