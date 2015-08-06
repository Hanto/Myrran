package DB.Recursos.SkillBaseRecursos;// Created by Hanto on 05/08/2015.

import DB.Recursos.SkillBaseRecursos.DAO.SkillBaseRecursosDAO;
import DB.Recursos.SkillBaseRecursos.DAO.SkillBaseRecursosXML;

public enum SkillBaseRecursosDAOFactory
{
    XML("XML")
    {
        @Override public SkillBaseRecursosDAO getSkillBaseRecursosDAO()
        {   return new SkillBaseRecursosXML(SkillBaseRecursosXMLDB.get()); }
    };

    public abstract SkillBaseRecursosDAO getSkillBaseRecursosDAO();
    private SkillBaseRecursosDAOFactory(String nombre) {}
}
