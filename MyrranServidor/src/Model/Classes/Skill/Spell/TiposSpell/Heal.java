package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 17/06/2014.

import InterfacesEntidades.EntidadesPropiedades.Misc.Caster;
import InterfacesEntidades.EntidadesPropiedades.Misc.Debuffeable;
import InterfacesEntidades.EntidadesPropiedades.Misc.Vulnerable;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Skills.Spell.TipoSpell;

public class Heal extends TipoSpell
{
    @Override public void inicializarSkillStats()
    {
        super.inicializarSkillStats();
        setNumSkillStats(2);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo)
    {
        int STAT_Curacion = 1;
        float curacion = spell.getValorTotal(Caster, STAT_Curacion);

        if (Caster instanceof Vulnerable)
        {   ((Vulnerable) Caster).modificarHPs(curacion); }

        if (Caster instanceof Debuffeable)
        {   spell.aplicarDebuffs(Caster, (Debuffeable) Caster);}
    }
}
