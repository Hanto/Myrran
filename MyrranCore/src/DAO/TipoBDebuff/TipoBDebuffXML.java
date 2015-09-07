package DAO.TipoBDebuff;// Created by Hanto on 10/06/2014.

import Interfaces.Misc.BDebuff.TipoBDebuffI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TipoBDebuffXML implements TipoBDebuffDAO
{
    private Map<String, TipoBDebuffI> listaDeTipoBDebuffs;
    private TipoBDebuffXMLDBI tipoBDebuffXMLDBI;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public TipoBDebuffXML(TipoBDebuffXMLDBI tipoBDebuffXMLDBI)
    {
        this.tipoBDebuffXMLDBI = tipoBDebuffXMLDBI;
        this.listaDeTipoBDebuffs = tipoBDebuffXMLDBI.getListaDeTipoBDebuffs();
    }



    @Override public boolean añadirTipoBDebuff(TipoBDebuffI debuff)
    {
        if (listaDeTipoBDebuffs.containsKey(debuff.getID()))
        {   logger.warn("Ya existe un TipoBDebuff con este ID[{}]", debuff.getID());  return false; }
        else
        {
            listaDeTipoBDebuffs.put(debuff.getID(), debuff);
            tipoBDebuffXMLDBI.salvarDatos();
            return true;
        }
    }

    @Override public void salvarTipoBDebuff(TipoBDebuffI debuff)
    {
        if (listaDeTipoBDebuffs.containsKey(debuff.getID()))
        {
            listaDeTipoBDebuffs.put(debuff.getID(), debuff);
            tipoBDebuffXMLDBI.salvarDatos();
        }
    }

    @Override public void eliminarTipoBDebuff(String debuffID)
    {
        if (listaDeTipoBDebuffs.containsKey(debuffID))
        {
            listaDeTipoBDebuffs.remove(debuffID);
            tipoBDebuffXMLDBI.salvarDatos();
        }
    }

    @Override public TipoBDebuffI getTipoBDebuff(String debuffID)
    {   return listaDeTipoBDebuffs.get(debuffID); }
}
