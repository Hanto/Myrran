package Interfaces.EntidadesPropiedades.Propiedades;// Created by Hanto on 23/06/2014.

import Interfaces.Misc.Spell.SkillPersonalizadoI;
import Interfaces.Misc.Spell.SpellPersonalizadoI;

import java.util.Iterator;

public interface CasterPersonalizable
{
    public SkillPersonalizadoI getSkillPersonalizado(String skillID);
    public SpellPersonalizadoI getSpellPersonalizado(String spellID);
    public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado();
    public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado();
    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int talento);
    public void añadirSkillsPersonalizados(String spellID);
}
