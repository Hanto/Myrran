package DB.Recursos.TipoSkillRecursos;// Created by Hanto on 05/08/2015.

import DB.Recursos.TipoSkillRecursos.DAO.TipoSkillRecursosDAO;
import DB.Recursos.TipoSkillRecursos.DAO.TipoSkillRecursosXML;

public enum TipoSkillRecursosDAOFactory
{
    XML("XML")
    {   @Override public TipoSkillRecursosDAO getTipoSkillRecursosDAO()
        {   return new TipoSkillRecursosXML(TipoSkillRecursosXMLDB.get()); }

    };

    public abstract TipoSkillRecursosDAO getTipoSkillRecursosDAO();
    private TipoSkillRecursosDAOFactory(String nombre) {}
}
