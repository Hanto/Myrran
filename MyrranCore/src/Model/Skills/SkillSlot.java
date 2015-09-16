package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkillSlot<T extends SkillI> extends AbstractModel implements SkillSlotI<T>
{
    private int iD;
    private List<Integer> lock = new ArrayList<>();
    private T skill;


    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillSlot(int iD)
    {   this.iD = iD; }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                    { return iD; }
    @Override public void setID(int iD)             { this.iD = iD; }

    // LOCKI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public List<Integer> getKeys()
    {   return lock;}

    @Override public boolean abreLaCerradura(KeyI<Integer> key)
    {   return !Collections.disjoint(key.getKeys(), lock); }

    @Override public void addKey(Integer key)
    {   lock.add(key);
        notificarActualizacion();
    }

    @Override public void removeKey(Integer key)
    {   lock.remove(key);
        notificarActualizacion();
    }

    // SPELLSLOT:
    //------------------------------------------------------------------------------------------------------------------

    @Override public T getSkill()
    {   return skill; }

    @Override public String getSkillID()
    {
        if (skill == null) return null;
        else return skill.getID();
    }

    @Override public void setSkill(T spell)
    {
        if (abreLaCerradura(spell))
        {
            this.skill = spell;
            notificarActualizacion();
        }
    }

    @Override public void setKeys(SkillSlotI<T> spellSlot)
    {
        for (Integer cerradura : spellSlot.getKeys())
            lock.add(cerradura);

        notificarActualizacion();
    }

    private void notificarActualizacion()
    {
        if (skill instanceof SpellI)
        {
            DTOsSkill.SetSpellSlot setSpellSlot = new DTOsSkill.SetSpellSlot(this);
            notificarActualizacion("setSpellSlot", null, setSpellSlot);
        }
        if (skill instanceof BDebuffI)
        {
            DTOsSkill.SetDebuffSlot setDebuffSlot = new DTOsSkill.SetDebuffSlot(this);
            notificarActualizacion("setDebuffSlot", null, setDebuffSlot);
        }
    }
}
