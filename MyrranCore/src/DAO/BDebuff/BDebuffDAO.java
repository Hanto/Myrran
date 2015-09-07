package DAO.BDebuff;// Created by Hanto on 16/06/2014.

import Interfaces.Misc.BDebuff.BDebuffI;

public interface BDebuffDAO
{
    public boolean añadirBDebuff(BDebuffI debuff);
    public void salvarBDebuff(BDebuffI debuff);
    public void eliminarBDebuff(String debuffID);
    public BDebuffI getBDebuff(String debuffID);
}
