package DAO.TipoBDebuff;// Created by Hanto on 07/07/2014.

import Interfaces.Misc.Spell.TipoBDebuffI;

import java.util.Map;

public interface TipoBDebuffXMLDBI
{
    public Map<String, TipoBDebuffI> getListaDeTipoBDebuffs();
    public void salvarDatos();
}
