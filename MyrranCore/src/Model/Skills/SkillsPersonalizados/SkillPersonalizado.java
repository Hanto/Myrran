package Model.Skills.SkillsPersonalizados;// Created by Hanto on 25/06/2014.

import DTOs.DTOsSkillPersonalizado;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillPersonalizadoI;

import java.util.Arrays;
import java.util.Iterator;

public class SkillPersonalizado extends AbstractModel implements SkillPersonalizadoI
{
    private String id;
    private SkillI skill;
    private SkillMod[] skillMods;

    //SET:
    public void setID(String id)                                        { this.id = id; }
    @Override public void setBonosPorObjetos(int statID, float valor)   { skillMods[statID].setBonosPorObjetos(valor); }

    //GET:
    @Override public int getNumTalentos(int statID)         { return skillMods[statID].getNumTalentos(); }
    @Override public float getBonosPorObjetos(int statID)   { return skillMods[statID].getBonosPorObjetos(); }

    @Override public boolean isDebuff()                     { return (skill instanceof BDebuffI ? true : false); }
    @Override public String getID()                         { return id; }
    @Override public String getNombre()                     { return skill.getNombre(); }
    @Override public int getNumSkillStats()                 { return skill.getNumSkillStats(); }
    @Override public String getNombre(int statID)           { return skill.getSkillStat(statID).getNombre(); }

    @Override public float getValorBase(int statID)         { return skill.getSkillStat(statID).getValorBase(); }
    @Override public float getBonoTalento (int statID)      { return skill.getSkillStat(statID).getBonoTalento(); }
    @Override public int getCosteTalento(int statID)        { return skill.getSkillStat(statID).getCosteTalento(); }
    @Override public int getTalentoMaximo(int statID)       { return skill.getSkillStat(statID).getTalentosMaximos(); }
    @Override public boolean getIsMejorable(int statID)     { return skill.getSkillStat(statID).getisMejorable(); }
    @Override public Iterator<SkillMod>getIterator()        { return Arrays.asList(skillMods).iterator(); }

    public SkillPersonalizado(SkillI skill)
    {
        this.skill = skill;
        this.id = skill.getID();

        skillMods = new SkillMod[skill.getNumSkillStats()];
        for (int i = 0; i < skillMods.length; i++)
        {   skillMods[i] = new SkillMod(i); }
    }

    @Override public float getValorTotal(int statID)
    {   return (getValorBase(statID) + getNumTalentos(statID) * getBonoTalento(statID) + getBonosPorObjetos(statID)); }

    @Override public void setNumTalentos(int statID, int valor)
    {
        skillMods[statID].setNumTalentos(valor);

        Object modificarNumTalentos = new DTOsSkillPersonalizado.SetNumTalentos(skill.getID(), statID, valor);
        notificarActualizacion("setNumTalentosSkillPersonalizado", null, modificarNumTalentos);
    }
}
