package DB.Datos.Terreno;// Created by Hanto on 14/04/2014.

import DAO.Terreno.TerrenoDAO;
import DAO.Terreno.TerrenoXML;

public enum TerrenoDAOFactory
{
    XML("XML")
    {
        @Override public TerrenoDAO getTerrenoDAO()
        {   return new TerrenoXML(TerrenoXMLDB.get()); }
    };

    public abstract TerrenoDAO getTerrenoDAO();
    private TerrenoDAOFactory(String nombre) {}
}
