package DB.Recursos.SkillRecursos;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DAO.SkillRecursosDAO;
import DB.Recursos.SkillRecursos.DAO.SkillRecursosXML;

public enum SkillRecursosDAOFactory
{
    XML("XML")
    {
        @Override public SkillRecursosDAO getSkillRecursosDAO()
        {   return new SkillRecursosXML(SkillRecursosXMLDB.get()); }
    };

    public abstract SkillRecursosDAO getSkillRecursosDAO();
    private SkillRecursosDAOFactory(String nombre) {}
}
