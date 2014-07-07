package DB.Recursos.FuentesRecursos;// Created by Hanto on 02/05/2014.

import DB.Recursos.FuentesRecursos.DAO.FuentesRecursosDAO;
import DB.Recursos.FuentesRecursos.DAO.FuentesRecursosXML;

public enum FuentesRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public FuentesRecursosDAO getFuentesRecursosDAO()
        {   return new FuentesRecursosXML(FuentesRecursosXMLDB.get()); }
    };

    public abstract FuentesRecursosDAO getFuentesRecursosDAO();
    private FuentesRecursosDAOFactory(String nombre) {}
}
