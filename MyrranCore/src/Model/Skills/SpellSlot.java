package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.KeyI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.Spell.SpellSlotI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpellSlot extends AbstractModel implements SpellSlotI
{
    private int iD;
    private List<Integer> lock = new ArrayList<>();
    private SpellI spell;


    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SpellSlot(int iD)
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

    @Override public SpellI getSpell()
    {   return spell; }

    @Override public String getSpellID()
    {
        if (spell == null) return null;
        else return spell.getID();
    }

    @Override public void setSpell(SpellI spell)
    {
        if (abreLaCerradura(spell))
        {
            this.spell = spell;
            notificarActualizacion();
        }
    }

    @Override public void setKeys(SpellSlotI spellSlot)
    {
        for (Integer cerradura : spellSlot.getKeys())
            lock.add(cerradura);

        notificarActualizacion();
    }

    private void notificarActualizacion()
    {
        DTOsSkill.setSpellSlot setSpellSlot = new DTOsSkill.setSpellSlot(this);
        notificarActualizacion("setSpellSlot", null, setSpellSlot);
    }
}
