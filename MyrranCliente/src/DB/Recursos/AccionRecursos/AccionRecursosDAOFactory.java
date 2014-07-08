package DB.Recursos.AccionRecursos;

import DB.Recursos.AccionRecursos.DAO.AccionRecursosDAO;
import DB.Recursos.AccionRecursos.DAO.AccionRecursosXML;

public enum AccionRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public AccionRecursosDAO getAccionRecursosDAO()
        {   return new AccionRecursosXML(AccionRecursosXMLDB.get()); }
    };

    public abstract AccionRecursosDAO getAccionRecursosDAO();
    private AccionRecursosDAOFactory(String nombre) { }
}