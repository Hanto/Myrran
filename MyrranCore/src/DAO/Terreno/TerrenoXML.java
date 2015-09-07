package DAO.Terreno;// Created by Hanto on 15/04/2014.

import Interfaces.Misc.Geo.TerrenoI;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

public class TerrenoXML implements TerrenoDAO
{
    private Map<Short, TerrenoI> listaDeTerrenos;
    private TerrenoXMLDBI terrenoXMLDBI;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public TerrenoXML(TerrenoXMLDBI terrenoXMLDBI)
    {
        this.terrenoXMLDBI = terrenoXMLDBI;
        this.listaDeTerrenos = terrenoXMLDBI.getListaTerrenos();
    }



    @Override public boolean añadirTerreno(TerrenoI terreno)
    {
        if (listaDeTerrenos.containsKey(terreno.getID()))
        {   logger.warn("Ya existe un terreno con este ID[{}]", terreno.getID());  return false; }
        else
        {
            listaDeTerrenos.put(terreno.getID(), terreno);
            terrenoXMLDBI.salvarDatos();
            return true;
        }
    }

    @Override public void salvarTerreno(TerrenoI terreno)
    {
        if (listaDeTerrenos.containsKey(terreno.getID()))
        {
            listaDeTerrenos.put(terreno.getID(), terreno);
            terrenoXMLDBI.salvarDatos();
        }
    }

    @Override public void eliminarTerreno(short terrenoID)
    {
        if (listaDeTerrenos.containsKey(terrenoID))
        {
            listaDeTerrenos.remove(terrenoID);
            terrenoXMLDBI.salvarDatos();
        }
    }

    @Override public TerrenoI getTerreno(short terrenoID)
    {   return listaDeTerrenos.get(terrenoID); }

    @Override public Iterator<TerrenoI> getIterator()
    {   return listaDeTerrenos.values().iterator(); }
}
