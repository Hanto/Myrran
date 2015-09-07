package DB.Recursos.SkillRecursos.DAO;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import Interfaces.Misc.Spell.SpellI;

public interface SkillRecursosDAO
{
    public SpellRecursos getSpellRecursos(String spellID);
    public SpellRecursos getSpellRecursos(SpellI spell);
    public void salvarSpellRecursos(SpellRecursos spellRecursos);
}
