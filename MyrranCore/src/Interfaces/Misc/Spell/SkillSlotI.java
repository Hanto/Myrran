package Interfaces.Misc.Spell;

import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.Misc.Observable.ModelI;

public interface SkillSlotI<T extends SkillI> extends LockI<Integer>, IDentificable, ModelI
{
    public T getSkill();
    public String getSkillID();
    public void setSkill(T skill);
    public void setKeys(SkillSlotI<T> spellSlot);
}
