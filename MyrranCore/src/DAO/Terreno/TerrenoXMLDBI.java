package DAO.Terreno;// Created by Hanto on 07/07/2014.

import Interfaces.Geo.TerrenoI;

import java.util.Map;

public interface TerrenoXMLDBI
{
    public Map<Short, TerrenoI> getListaTerrenos();
    public void salvarDatos();
}
