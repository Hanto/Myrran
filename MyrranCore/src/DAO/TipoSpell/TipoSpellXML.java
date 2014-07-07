package DAO.TipoSpell;// Created by Hanto on 17/04/2014.

import Interfaces.Spell.TipoSpellI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TipoSpellXML implements TipoSpellDAO
{
    private Map<String, TipoSpellI> listaDeTipoSpells;
    private TipoSpellXMLDBI tipoSpellXMLDBI;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public TipoSpellXML(TipoSpellXMLDBI tipoSpellXMLDBI)
    {
        this.tipoSpellXMLDBI = tipoSpellXMLDBI;
        listaDeTipoSpells = tipoSpellXMLDBI.getListaDeTipoSpells();
    }




    @Override public boolean añadirTipoSpell(TipoSpellI tipoSpell)
    {
        if (listaDeTipoSpells.containsKey(tipoSpell.getID()))
        {   logger.error("Ya existe un TipoSpell con este ID[{}]",tipoSpell.getID());  return false; }
        else
        {
            listaDeTipoSpells.put(tipoSpell.getID(), tipoSpell);
            tipoSpellXMLDBI.salvarDatos();
            return true;
        }
    }

    @Override public void salvarTipoSpell(TipoSpellI tipoSpell)
    {
        if (listaDeTipoSpells.containsKey(tipoSpell.getID()))
        {
            listaDeTipoSpells.put(tipoSpell.getID(), tipoSpell);
            tipoSpellXMLDBI.salvarDatos();
        }
    }

    @Override public void eliminarTipoSpell(String tipoSpellID)
    {
        if (listaDeTipoSpells.containsKey(tipoSpellID))
        {
            listaDeTipoSpells.remove(tipoSpellID);
            tipoSpellXMLDBI.salvarDatos();
        }
    }

    @Override public TipoSpellI getTipoSpell(String tipoSpellID)
    {   return listaDeTipoSpells.get(tipoSpellID); }
}
