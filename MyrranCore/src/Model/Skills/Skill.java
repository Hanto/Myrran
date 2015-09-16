package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillStatI;
import Interfaces.Misc.Spell.SpellSlotI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Skill extends AbstractModel implements SkillI, PropertyChangeListener
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected List<SkillStatI> listaSkillStats;
    protected List<SpellSlotI> listaSpellSlots;
    protected List<Integer> keys = new ArrayList<>();

    @Override public void setID(String id)                      { this.id = id; }
    @Override public void setNombre (String nombre)             { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)   { this.descripcion = descripcion; }

    @Override public String getID()                             { return id; }
    @Override public String getNombre ()                        { return nombre; }
    @Override public String getDescripcion ()                   { return descripcion; }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Skill() {}

    //constructor copia:
    public Skill(SkillI skill)
    {
        this.setID(skill.getID());
        this.setNombre(skill.getNombre());
        this.setDescripcion(skill.getDescripcion());
        this.setNumSkillStats(skill.getNumSkillStats());

        for  (int iD = 0; iD < getNumSkillStats(); iD++)
        {  getSkillStat(iD).setFullStats(skill.getSkillStat(iD)); }

        this.setNumSpellSlots(skill.getNumSpellSlots());

        for (int iD = 0; iD < getNumSpellSlots(); iD++)
        {   getSpellSlot(iD).setKeys(skill.getSpellSlot(iD)); }
    }

    @Override public void dispose()
    {
        for (SkillStatI skillStat : listaSkillStats)
        {   skillStat.eliminarObservador(this); }

        for (SpellSlotI spellSlot : listaSpellSlots)
        {   spellSlot.eliminarObservador(this); }
    }

    // SKILLSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setNumSkillStats(int numSkillStats)
    {
        listaSkillStats = new ArrayList<>(numSkillStats);
        for  (int i = 0; i < numSkillStats; i++)
        {
            SkillStat skillStat = new SkillStat();
            listaSkillStats.add(skillStat);
            skillStat.añadirObservador(this);
        }
    }

    @Override public int getNumSkillStats()
    {
        if (listaSkillStats == null)
        {   logger.error("ERROR: falta definir el numero de SkillStats en IniciacilizarSkill del skill {}-{}", getID(), getTipoID()); return 0; }
        return listaSkillStats.size();
    }

    @Override public SkillStatI getSkillStat(int numSkillStat)
    {   return listaSkillStats.get(numSkillStat); }

    @Override public Iterator<SkillStatI> getSkillStats()
    {   return listaSkillStats.iterator(); }

    // SPELLSLOTS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setNumSpellSlots(int numSpellSlots)
    {
        listaSpellSlots = new ArrayList<>(numSpellSlots);
        for (int iD = 0; iD < numSpellSlots; iD++)
        {
            SpellSlotI spellSlot = new SpellSlot(iD);
            listaSpellSlots.add(spellSlot);
            spellSlot.añadirObservador(this);
        }
    }

    @Override public int getNumSpellSlots()
    {
        if (listaSpellSlots == null)
        {   logger.error("ERROR: falta definir el numero de SkillSlots en IniciacilizarSkill del skill {}-{}", getID(), getTipoID()); return 0; }
        return listaSpellSlots.size();
    }

    @Override public SpellSlotI getSpellSlot(int numSpellSlot)
    {   return listaSpellSlots.get(numSpellSlot); }

    @Override public Iterator<SpellSlotI> getSpellSlots()
    {   return listaSpellSlots.iterator(); }

    // KEYS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public List<Integer> getKeys()
    {   return keys; }

    @Override public void addKey(Integer keyID)
    {
        keys.add(keyID);
        notificarSetKey();
    }

    @Override public void removeKey(Integer keyID)
    {
        keys.remove(keyID);
        notificarSetKey();
    }

    private void notificarSetKey()
    {
        DTOsSkill.SetKey setKey = new DTOsSkill.SetKey(this);
        notificarActualizacion("setKey", null, setKey);
    }
}
