package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillSlotsI;
import Interfaces.Misc.Spell.SkillStatsI;
import Interfaces.Misc.Spell.SpellI;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Skill extends AbstractModel implements SkillI, PropertyChangeListener
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected SkillI skillPadre;

    protected SkillStatsI stats = new SkillStats();
    protected SkillSlotsI<SpellI> spellSlots = new SkillSlots<>(this);
    protected List<Integer> keys = new ArrayList<>();

    @Override public void setID(String id)                      { this.id = id; }
    @Override public void setNombre (String nombre)             { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)   { this.descripcion = descripcion; }
    @Override public String getID()                             { return id; }
    @Override public String getNombre ()                        { return nombre; }
    @Override public String getDescripcion ()                   { return descripcion; }
    @Override public SkillStatsI getStats()                     { return stats; }
    @Override public SkillSlotsI<SpellI> getSpellSlots()        { return spellSlots; }
    @Override public List<Integer> getKeys()                    { return keys; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Skill() {}

    public Skill(SkillI skill)
    {
        this.setID(skill.getID());
        this.setNombre(skill.getNombre());
        this.setDescripcion(skill.getDescripcion());
        this.getStats().setStats(skill.getStats());
        this.getSpellSlots().setSlots(skill.getSpellSlots());

        getStats().añadirObservador(this);
        getSpellSlots().añadirObservador(this);
    }

    @Override public void dispose()
    {
        getStats().eliminarObservador(this);
        getSpellSlots().eliminarObservador(this);
    }

    // SKILLASOCIADOALOSLOT: (para evitar la dependencia circular)
    //------------------------------------------------------------------------------------------------------------------

    public SkillI getSkill()
    {   return this; }

    public SkillI getSkillPadre()
    {   return skillPadre; }

    public void setSkillPadre(SkillI skill)
    {
        skillPadre = skill;
        notificarSetSpellPadre();
    }

    // KEYS:
    //------------------------------------------------------------------------------------------------------------------

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

    // NOTIFICAR:
    //------------------------------------------------------------------------------------------------------------------

    private void notificarSetKey()
    {
        DTOsSkill.SetKey setKey = new DTOsSkill.SetKey(this);
        notificarActualizacion("setKey", null, setKey);
    }

    private void notificarSetSpellPadre()
    {
        DTOsSkill.SetSpellPadre setSpellPadre = new DTOsSkill.SetSpellPadre(this);
        notificarActualizacion("setSpellPadre", null, setSpellPadre);
    }
}
