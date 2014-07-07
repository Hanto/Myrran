package DB.Recursos.MiscRecursos;

import DB.Recursos.MiscRecursos.DAO.MiscRecursosDAO;
import DB.Recursos.MiscRecursos.DAO.MiscRecursosXML;

public enum MiscRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public MiscRecursosDAO getMiscRecursosDAO()
        {   return new MiscRecursosXML(MiscRecursosXMLDB.get()); }
    };

    public abstract MiscRecursosDAO getMiscRecursosDAO();
    private MiscRecursosDAOFactory(String nombre) {}

}