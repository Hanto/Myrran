package Interfaces.Misc.Spell;


import Interfaces.Misc.Observable.ModelI;

import java.util.Iterator;

public interface SkillSlotsI<T extends SkillI> extends ModelI
{
    public void setSlots(SkillSlotsI<T> skillSlots);
    public void setNumSlots(int numSpellSlots);
    public int getNumSlots();
    public SkillSlotI<T> getSlot(int numSpellSlot);
    public Iterator<SkillSlotI<T>> getIterator();
}
