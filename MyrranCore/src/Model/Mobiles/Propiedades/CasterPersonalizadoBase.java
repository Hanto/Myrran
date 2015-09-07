package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import DAO.Spell.SpellDAOFactoryI;
import Interfaces.Misc.Skill.SkillPersonalizadoI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.Spell.SpellPersonalizadoI;
import Interfaces.EntidadesPropiedades.Misc.CasterPersonalizable;
import Model.Skills.SkillsPersonalizados.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CasterPersonalizadoBase implements CasterPersonalizable
{
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected SpellDAOFactoryI spellDAOFactory;
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    public CasterPersonalizadoBase(SpellDAOFactoryI spellDAOFactory)
    {   this.spellDAOFactory = spellDAOFactory; }

    public SkillPersonalizadoI getSkillPersonalizado(String skillID)
    {   return listaSkillsPersonalizados.get(skillID); }

    public SpellPersonalizadoI getSpellPersonalizado(String spellID)
    {   return listaSpellsPersonalizados.get(spellID); }

    public Iterator<SpellPersonalizadoI> getIteratorSpellPersonalizado()
    {   return listaSpellsPersonalizados.values().iterator(); }

    public Iterator<SkillPersonalizadoI> getIteratorSkillPersonalizado()
    {   return listaSkillsPersonalizados.values().iterator(); }

    public void añadirSkillsPersonalizados(String spellID)
    {
        SpellI spell = spellDAOFactory.getSpellDAO().getSpell(spellID);
        if (spell == null)
            { logger.error("ERROR: añadirSkillsPersonalizados: spellID no encontrado: {}", spellID); return; }

        SpellPersonalizadoI spellPersonalizado = new SpellPersonalizado(spell);
        listaSpellsPersonalizados.put(spellPersonalizado.getID(), spellPersonalizado);

        listaSkillsPersonalizados.put(spellPersonalizado.getCustomSpell().getID(), spellPersonalizado.getCustomSpell());
        Iterator<SkillPersonalizadoI> iterator = spellPersonalizado.getIteratorCustomDebuffs();
        while(iterator.hasNext())
        {
            SkillPersonalizadoI skillPersonalizado = iterator.next();
            listaSkillsPersonalizados.put(skillPersonalizado.getID(), skillPersonalizado);
        }
    }

    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {
        SkillPersonalizadoI skillPersonalizado = listaSkillsPersonalizados.get(skillID);
        if (skillPersonalizado == null)
            { logger.error("ERROR: setNumTalentosSkillPersonalizado, spellID no existe: {}", skillID); return; }
        else if (valor >= 0 && valor <= skillPersonalizado.getTalentoMaximo(statID))
            skillPersonalizado.setNumTalentos(statID, valor);
    }
}
