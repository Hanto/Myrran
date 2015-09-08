package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 17/06/2014.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.DebuffeableI;
import Interfaces.EntidadesPropiedades.Propiedades.Vulnerable;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.SpellI;
import Model.Skills.Spell.TipoSpell;

public class Heal extends TipoSpell
{
    public int STAT_Curacion = 1;

    @Override public void inicializarSkillStats()
    {
        super.inicializarSkillStats();
        setNumSkillStats(2);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo)
    {
        float curacion = spell.getValorTotal(Caster, STAT_Curacion);

        if (Caster instanceof Vulnerable)
        {   ((Vulnerable) Caster).modificarHPs(curacion); }

        if (Caster instanceof DebuffeableI)
        {   spell.aplicarDebuffs(Caster, (DebuffeableI) Caster);}
    }
}
