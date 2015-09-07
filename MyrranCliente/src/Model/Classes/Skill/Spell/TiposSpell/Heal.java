package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 17/06/2014.

import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import InterfacesEntidades.EntidadesPropiedades.Misc.Caster;
import Model.Skills.Spell.TipoSpell;

public class Heal extends TipoSpell
{
    @Override public void inicializarSkillStats()
    {
        super.inicializarSkillStats();
        setNumSkillStats(2);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo) {}
}
