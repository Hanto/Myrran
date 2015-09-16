package Interfaces.Misc.Spell;

import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.Misc.Observable.ModelI;

public interface SpellSlotI extends LockI<Integer>, IDentificable, ModelI
{
    public SpellI getSpell();
    public String getSpellID();
    public void setSpell(SpellI spell);
    public void setKeys(SpellSlotI spellSlot);
}
