package Model.Classes.Skill.BDebuff.TiposBDebuff;// Created by Hanto on 10/06/2014.

import Interfaces.Misc.BDebuff.AuraI;
import Interfaces.EntidadesPropiedades.Misc.Vulnerable;
import Model.Skills.BDebuff.TipoBDebuff;

public class Hot extends TipoBDebuff
{
    public final int STAT_Heal = 1;

    @Override public void inicializarSkillStats()
    {
        super.inicializarSkillStats();
        setNumSkillStats(2);
    }

    @Override public void actualizarTick(AuraI aura)
    {
        float HPsPorTick = aura.getDebuff().getValorTotal(aura.getCaster(), STAT_Heal) * aura.getStacks();

        if (aura.getTarget() instanceof Vulnerable)
        {   ((Vulnerable)aura.getTarget()).modificarHPs((int)+HPsPorTick); }
    }
}
