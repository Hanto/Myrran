package Model.Classes.Skill.BDebuff.TiposBDebuff;// Created by Hanto on 26/06/2014.

import Interfaces.Misc.Spell.AuraI;
import Model.Skills.BDebuff.TipoBDebuff;

public class Bomba extends TipoBDebuff
{
    @Override public void inicializarSkillStats()
    {   setNumSkillStats(2); }

    @Override public void actualizarTick(AuraI aura) { }

}
