package Model.Classes.Skill.BDebuff.TiposBDebuff;// Created by Hanto on 26/06/2014.

import Interfaces.Misc.Spell.AuraI;
import Model.Skills.BDebuff.TipoBDebuff;

public class Bomba extends TipoBDebuff
{
    @Override public void inicializarSkillStats()
    {
        getStats().setNumStats(2);
        getSpellSlots().setNumSlots(0);
    }

    @Override public void actualizarTick(AuraI aura) { }

}
