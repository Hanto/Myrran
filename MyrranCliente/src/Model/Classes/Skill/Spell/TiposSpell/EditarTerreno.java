package Model.Classes.Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Model.Skills.Spell.TipoSpell;

public class EditarTerreno extends TipoSpell
{
    @Override public void inicializarSkill()
    {
        setNumSkillStats(1);
        setNumSpellSlots(0);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo) {}
}
