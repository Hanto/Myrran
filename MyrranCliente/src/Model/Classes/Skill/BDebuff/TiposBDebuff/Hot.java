package Model.Classes.Skill.BDebuff.TiposBDebuff;// Created by Hanto on 10/06/2014.

import Interfaces.Misc.Spell.AuraI;
import Model.Skills.BDebuff.TipoBDebuff;

public class Hot extends TipoBDebuff
{
    @Override public void inicializarSkillStats()
    {
        stats().setNumStats(2);
        spellSlots().setNumSlots(0);
    }

    @Override public void actualizarTick(AuraI aura) { }
}
