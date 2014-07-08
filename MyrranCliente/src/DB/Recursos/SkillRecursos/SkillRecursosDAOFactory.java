package DB.Recursos.SkillRecursos;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DAO.SkillRecursosDAO;
import DB.Recursos.SkillRecursos.DAO.SkillRecursosXML;

public enum SkillRecursosDAOFactory
{
    XML("XML")
    {
        @Override
        public SkillRecursosDAO getSpellRecursosDAO()
        {   return new SkillRecursosXML(SkillRecursosXMLDB.get()); }
    };

    public abstract SkillRecursosDAO getSpellRecursosDAO();
    private SkillRecursosDAOFactory(String nombre) {}
}
