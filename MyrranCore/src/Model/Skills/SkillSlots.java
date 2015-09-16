package Model.Skills;

import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillSlotI;
import Interfaces.Misc.Spell.SkillSlotsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkillSlots<T extends SkillI> implements SkillSlotsI<T>
{
    private List<SkillSlotI<T>> listaSpellSlots;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setSlots(SkillSlotsI<T> skillSlots)
    {
        if (listaSpellSlots != null) { logger.error("ERROR: skillSlots ya inicializado"); return; }

        if (skillSlots != null)
        {
            setNumSlots(skillSlots.getNumSlots());
            for (int iD = 0; iD < skillSlots.getNumSlots(); iD++)
            {   getSlot(iD).setKeys(skillSlots.getSlot(iD)); }
        }
    }

    @Override public void setNumSlots(int numSpellSlots)
    {
        if (listaSpellSlots != null) { logger.error("ERROR: skillSlots ya inicializado"); return; }

        listaSpellSlots = new ArrayList<>(numSpellSlots);
        for (int iD = 0; iD < numSpellSlots; iD++)
        {   listaSpellSlots.add(new SkillSlot<T>(iD)); }
    }

    @Override public int getNumSlots()
    {
        if (listaSpellSlots == null)
        {   logger.error("ERROR: falta definir el numero de SkillSlots en IniciacilizarSkill del skill"); return 0; }
        return listaSpellSlots.size();
    }

    @Override public SkillSlotI<T> getSlot(int numSpellSlot)
    {   return listaSpellSlots.get(numSpellSlot); }

    @Override public Iterator<SkillSlotI<T>> getSlots()
    {   return listaSpellSlots.iterator(); }

    // MODEL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirObservador(PropertyChangeListener observador)
    {
        for (SkillSlotI<T> skillSlot : listaSpellSlots)
        {   skillSlot.añadirObservador(observador); }
    }

    @Override public void eliminarObservador(PropertyChangeListener observador)
    {
        for (SkillSlotI<T> skillSlot : listaSpellSlots)
        {   skillSlot.eliminarObservador(observador); }
    }
}
