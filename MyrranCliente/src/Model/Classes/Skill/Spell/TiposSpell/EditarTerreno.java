package Model.Classes.Skill.Spell.TiposSpell;
// @author Ivan Delgado Huerta

import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import InterfacesEntidades.EntidadesPropiedades.Misc.Caster;
import Model.Skills.Spell.TipoSpell;

public class EditarTerreno extends TipoSpell
{
    @Override public void inicializarSkillStats()
    {
        super.inicializarSkillStats();
        setNumSkillStats(1);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo) {}
}
