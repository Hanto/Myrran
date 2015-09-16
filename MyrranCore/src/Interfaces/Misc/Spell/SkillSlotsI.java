package Interfaces.Misc.Spell;


import java.beans.PropertyChangeListener;
import java.util.Iterator;

public interface SkillSlotsI<T extends SkillI>
{
    public void setSlots(SkillSlotsI<T> skillSlots);
    public void setNumSlots(int numSpellSlots);
    public int getNumSlots();
    public SkillSlotI<T> getSlot(int numSpellSlot);
    public Iterator<SkillSlotI<T>> getSlots();

    public void añadirObservador(PropertyChangeListener observador);
    public void eliminarObservador(PropertyChangeListener observador);

}
