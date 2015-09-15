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
    {   inicializarSkillStats(); }

    // TIPOSPELLI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void inicializarSkillStats()
    {   setID(this.getClass().getSimpleName().toUpperCase()); }

    @Override public void propertyChange(PropertyChangeEvent evt) {}
}
