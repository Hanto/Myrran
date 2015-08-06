package DB.Recursos.SkillRecursos.DAO;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import DB.Recursos.SkillRecursos.SkillRecursosXMLDB;
import Interfaces.Spell.SpellI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SkillRecursosXML implements SkillRecursosDAO
{
    private Map<String, SpellRecursos> listaSpell;
    private SkillRecursosXMLDB skillRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public SkillRecursosXML(SkillRecursosXMLDB skillRecursosXMLDB)
    {
        this.skillRecursosXMLDB = skillRecursosXMLDB;
        this.listaSpell = skillRecursosXMLDB.getListaSpell();
    }

    @Override public SpellRecursos getSpellRecursos(String spellID)
    {
        if (!listaSpell.containsKey(spellID))
            { logger.error("ERROR: spellID {} no existe", spellID); return null; }
        else return listaSpell.get(spellID);
    }

    @Override public SpellRecursos getSpellRecursos(SpellI spell)
    {   return getSpellRecursos(spell.getID()); }

    @Override public void salvarSpellRecursos(SpellRecursos spellRecursos)
    {
        listaSpell.put(spellRecursos.getIDSpell(), spellRecursos);
        skillRecursosXMLDB.salvarSpellRecursos();
    }
}
