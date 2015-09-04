package Model.Classes.Mobiles.Modulares.Abstractos;// Created by Hanto on 03/09/2015.

import DB.DAO;
import InterfacesEntidades.EntidadesPropiedades.CasterPersonalizable;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.SpellPersonalizadoI;
import Model.Skills.SpellPersonalizado;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CasterPersonalizadoAbstract implements CasterPersonalizable
{
    protected Map<String, SkillPersonalizadoI> listaSkillsPersonalizados = new HashMap<>();
    protected Map<String, SpellPersonalizadoI> listaSpellsPersonalizados = new HashMap<>();

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


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
        SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(spellID);
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
