package Model.Skills.Spell;
// @author Ivan Delgado Huerta

import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillSlotsI;
import Interfaces.Misc.Spell.TipoSpellI;
import Model.Skills.Skill;
import Model.Skills.SkillSlots;

import java.beans.PropertyChangeEvent;

public abstract class TipoSpell extends Skill implements TipoSpellI
{
    protected SkillSlotsI<BDebuffI> debuffSlots = new SkillSlots<>(this);

    @Override public SkillSlotsI<BDebuffI> debuffSlots()                { return debuffSlots; }
    @Override public String getTipoID()                                 { return null; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public TipoSpell ()
    {
        setID(this.getClass().getSimpleName().toUpperCase());
        inicializarSkill();
    }

    @Override public void propertyChange(PropertyChangeEvent evt) {}
}
