package DB.Recursos.TipoSkillRecursos.DAO;// Created by Hanto on 05/08/2015.

import DB.Recursos.TipoSkillRecursos.DTO.TipoSpellRecursos;

public interface TipoSkillRecursosDAO
{
    public TipoSpellRecursos getTipoSpellRecursos(String TipoSpellID);
    public void salvarTipoSpellRecursos(TipoSpellRecursos tipoSpellRecursos);
}
