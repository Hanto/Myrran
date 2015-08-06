package DB.Recursos.TipoSkillRecursos.DAO;// Created by Hanto on 05/08/2015.

import DB.Recursos.TipoSkillRecursos.DTO.TipoSpellRecursos;
import DB.Recursos.TipoSkillRecursos.TipoSkillRecursosXMLDB;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TipoSkillRecursosXML implements TipoSkillRecursosDAO
{
    private Map<String, TipoSpellRecursos> listaTipoSpell;
    private TipoSkillRecursosXMLDB tipoSkillRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public TipoSkillRecursosXML(TipoSkillRecursosXMLDB tipoSkillRecursosXMLDB)
    {
        this.tipoSkillRecursosXMLDB = tipoSkillRecursosXMLDB;
        this.listaTipoSpell = tipoSkillRecursosXMLDB.getListaTipoSpell();
    }

    @Override public TipoSpellRecursos getTipoSpellRecursos(String tipoSpellID)
    {
        if (!listaTipoSpell.containsKey(tipoSpellID))
            { logger.error("ERROR: tipoSpell {} no existe", tipoSpellID); return null; }
        else return listaTipoSpell.get(tipoSpellID);
    }

    @Override public void salvarTipoSpellRecursos(TipoSpellRecursos tipoSpellRecursos)
    {   listaTipoSpell.put(tipoSpellRecursos.getIDTipoSpell(), tipoSpellRecursos); }
}
