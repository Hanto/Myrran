package DAO.TipoSpell;// Created by Hanto on 07/07/2014.

import Interfaces.Misc.Spell.TipoSpellI;

import java.util.Map;

public interface TipoSpellXMLDBI
{
    public Map<String, TipoSpellI> getListaDeTipoSpells();
    public void salvarDatos();
}
