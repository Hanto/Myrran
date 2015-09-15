package Model.Skills.Spell;
// @author Ivan Delgado Huerta

import Interfaces.Misc.Spell.TipoSpellI;
import Model.Skills.Skill;

import java.beans.PropertyChangeEvent;

public abstract class TipoSpell extends Skill implements TipoSpellI
{
    @Override public String getTipoID()                                 { return null; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public TipoSpell ()
    {
        setID(this.getClass().getSimpleName().toUpperCase());
        inicializarSkillStats();
    }

    @Override public void propertyChange(PropertyChangeEvent evt) {}
}
