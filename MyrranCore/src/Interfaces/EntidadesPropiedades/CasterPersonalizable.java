package Interfaces.EntidadesPropiedades;// Created by Hanto on 23/06/2014.

import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellPersonalizadoI;

import java.util.Iterator;

public interface CasterPersonalizable extends Caster
{
    public void añadirSkillsPersonalizados(String spellID);
    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento);
    public SkillPersonalizadoI getSkillPersonalizado(String skillID);
    public SpellPersonalizadoI getSpellPersonalizado(String spellID);
    public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado();
    public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado();
}
