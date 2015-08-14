package DB.Recursos.PixieMobRecursos;

import DB.Recursos.PixieMobRecursos.DAO.PixieMobRecursosDAO;
import DB.Recursos.PixieMobRecursos.DAO.PixieMobRecursosXML;

public enum PixieMobRecursosFactory
{
    XML("XML")
    {
        @Override
        public PixieMobRecursosDAO getPixieMobRecursosDaoDAO()
        {   return new PixieMobRecursosXML(PixieMobRecursosXMLDB.get());}
    };


    public abstract PixieMobRecursosDAO getPixieMobRecursosDaoDAO();
    private PixieMobRecursosFactory(String nombre) {}

}