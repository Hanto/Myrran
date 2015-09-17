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
        getStats().setNumStats(1);
        getSpellSlots().setNumSlots(0);
        debuffSlots().setNumSlots(0);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo) {}
}
