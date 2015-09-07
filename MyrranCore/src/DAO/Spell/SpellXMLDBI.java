package DAO.Spell;// Created by Hanto on 07/07/2014.

import Interfaces.Misc.Spell.SpellI;

import java.util.Map;

public interface SpellXMLDBI
{
    public Map<String, SpellI> getListaDeSpells();
    public void salvarDatos();
}
