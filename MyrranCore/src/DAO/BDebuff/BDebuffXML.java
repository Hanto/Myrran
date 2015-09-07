package DAO.BDebuff;// Created by Hanto on 16/06/2014.

import Interfaces.Misc.BDebuff.BDebuffI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BDebuffXML implements BDebuffDAO
{
    private Map<String, BDebuffI> listaDeBDebuffs;
    private BDebuffXMLDBI bDebuffXMLDBI;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public BDebuffXML(BDebuffXMLDBI bDebuffXMLDBI)
    {
        this.bDebuffXMLDBI = bDebuffXMLDBI;
        this.listaDeBDebuffs = bDebuffXMLDBI.getListaDeBDebuffs();
    }




    @Override public boolean añadirBDebuff(BDebuffI debuff)
    {
        if (listaDeBDebuffs.containsKey(debuff.getID()))
        {   logger.warn("Ya existe un Buff/Debuff con este ID[{}]", debuff.getID());  return false; }
        else
        {
            listaDeBDebuffs.put(debuff.getID(), debuff);
            bDebuffXMLDBI.salvarDatos();
            return true;
        }
    }

    @Override public void salvarBDebuff(BDebuffI debuff)
    {
        if (listaDeBDebuffs.containsKey(debuff.getID()))
        {
            listaDeBDebuffs.put(debuff.getID(), debuff);
            bDebuffXMLDBI.salvarDatos();
        }
    }

    @Override public void eliminarBDebuff(String debuffID)
    {
        if (listaDeBDebuffs.containsKey(debuffID))
        {
            listaDeBDebuffs.remove(debuffID);
            bDebuffXMLDBI.salvarDatos();
        }
    }

    @Override public BDebuffI getBDebuff(String debuffID)
    {   return listaDeBDebuffs.get(debuffID); }
}
