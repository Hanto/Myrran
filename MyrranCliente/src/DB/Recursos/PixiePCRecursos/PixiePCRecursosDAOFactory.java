package DB.Recursos.PixiePCRecursos;// Created by Hanto on 01/05/2014.

import DB.Recursos.PixiePCRecursos.DAO.PixiePCRecursosXML;
import DB.Recursos.PixiePCRecursos.DAO.PixiePCRecursosDAO;

public enum PixiePCRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public PixiePCRecursosDAO getPixiePCRecursosDAO()
        {   return new PixiePCRecursosXML(PixiePCRecursosXMLDB.get()); }
    };

    public abstract PixiePCRecursosDAO getPixiePCRecursosDAO();
    private PixiePCRecursosDAOFactory(String nombre) {}
}
