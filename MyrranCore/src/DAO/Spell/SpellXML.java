package DAO.Spell;// Created by Hanto on 17/04/2014.

import Interfaces.Spell.SpellI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SpellXML implements SpellDAO
{
    private Map<String, SpellI> listaDeSpells;
    private SpellXMLDBI spellXMLDBI;
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public SpellXML(SpellXMLDBI spellXMLDBI)
    {
        this.spellXMLDBI = spellXMLDBI;
        this.listaDeSpells = spellXMLDBI.getListaDeSpells();
    }




    @Override public boolean añadirSpell(SpellI spell)
    {
        if (listaDeSpells.containsKey(spell.getID()))
        {   logger.warn("No se puede añadir spell, ya existe ID[{}]",spell.getID());  return false; }
        else
        {
            listaDeSpells.put(spell.getID(), spell);
            spellXMLDBI.salvarDatos();
            return true;
        }
    }

    @Override public void salvarSpell(SpellI spell)
    {
        if (listaDeSpells.containsKey(spell.getID()))
        {
            listaDeSpells.put(spell.getID(), spell);
            spellXMLDBI.salvarDatos();
        }
        else logger.warn("No se puede salvar spell, no existe ID[{}]", spell.getID());
    }

    @Override public void eliminarSpell(String spellID)
    {
        if (listaDeSpells.containsKey(spellID))
        {
            listaDeSpells.remove(spellID);
            spellXMLDBI.salvarDatos();
        }
        else logger.warn("No se puede eliminar spell, no existe ID[{}]", spellID);
    }

    @Override public SpellI getSpell(String spellID)
    {   return listaDeSpells.get(spellID); }
}
