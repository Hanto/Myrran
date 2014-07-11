package DB.Recursos.ParticulasRecursos;

import DB.Recursos.ParticulasRecursos.DAO.ParticulaRecursosDAO;
import DB.Recursos.ParticulasRecursos.DAO.ParticulaRecursosXML;

public enum ParticulaRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public ParticulaRecursosDAO getParticulaRecursosDAO()
        {   return new ParticulaRecursosXML(ParticulaRecursosXMLDB.get()); }
    };


    public abstract ParticulaRecursosDAO getParticulaRecursosDAO();
    private ParticulaRecursosDAOFactory(String nombre)  {}

}