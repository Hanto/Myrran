package DAO.BDebuff;// Created by Hanto on 07/07/2014.

import Interfaces.Misc.BDebuff.BDebuffI;

import java.util.Map;

public interface BDebuffXMLDBI
{
    public Map<String, BDebuffI> getListaDeBDebuffs();
    public void salvarDatos();
}
