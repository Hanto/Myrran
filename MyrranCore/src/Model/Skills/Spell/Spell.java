package Model.Skills.Spell;
// @author Ivan Delgado Huerta

import Interfaces.Misc.BDebuff.BDebuffI;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Skill.SkillI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.Spell.TipoSpellI;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Model.Skills.SkillsPersonalizados.SkillStat;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Spell extends AbstractModel implements SpellI
{
    public final int STAT_Cast = 0;

    protected String id;
    protected String nombre;
    protected String descripcion;
    protected TipoSpellI tipoSpell;                              //Command Pattern: Codigo que se ejecuta al castear el skill

    private SkillStat[] skillStats;                             //Stats concretos del skill
    private List<BDebuffI> listaDeDebuffsQueAplica = new ArrayList<>();

    //SET
    @Override public void setID(String id)                      { this.id = id; }
    @Override public void setNombre (String nombre)             { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)   { this.descripcion = descripcion; }
    @Override public void setTipoSpell(TipoSpellI tipoSpell)    { this.tipoSpell = tipoSpell; }

    //GET:
    @Override public String getID()                             { return id; }
    @Override public String getNombre ()                        { return nombre; }
    @Override public String getDescripcion ()                   { return descripcion; }
    @Override public TipoSpellI getTipoSpell()                  { return tipoSpell; }
    @Override public SkillStat getSkillStat(int statID)         { return skillStats[statID]; }
    @Override public Iterator<SkillStat> getSkillStats()        { return Arrays.asList(skillStats).iterator(); }
    @Override public Iterator<BDebuffI> getDebuffsQueAplica()   { return listaDeDebuffsQueAplica.iterator(); }
    @Override public int getNumSkillStats()                     { return skillStats.length; }
    @Override public int getNumDebuffsQueAplica()               { return listaDeDebuffsQueAplica.size(); }

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Spell (TipoSpellI tipospell)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Spell
        if (tipospell == null) { logger.error("ERROR: spellID no encontrado."); return; }
        tipoSpell = tipospell;

        nombre = tipospell.getNombre();
        descripcion = tipospell.getDescripcion();

        //y se copian sus Stats base:
        skillStats = new SkillStat[tipospell.getNumSkillStats()];
        for (int i=0; i<skillStats.length;i++)
        {
            SkillStat statSkill = new SkillStat(tipospell.getSkillStat(i));
            skillStats[i] = statSkill;       
        }
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public SkillI getSkill(String skillID)
    {
        if (id.equals(skillID)) return this;
        else
        {
            Iterator<BDebuffI> debuffIIterator = getDebuffsQueAplica();
            BDebuffI debuff;
            while (debuffIIterator.hasNext())
            {
                debuff = debuffIIterator.next();
                if (debuff.getID().equals(skillID)) { return debuff; }
            }
        }
        return null;
    }

    @Override public void añadirDebuff (BDebuffI debuff)
    {
        if (debuff == null) { logger.error("ERROR: debuff que añadir al Spell {} no encontrado.", id); return; }
        if (!listaDeDebuffsQueAplica.contains(debuff)) { listaDeDebuffsQueAplica.add(debuff); }
    }

    @Override public void aplicarDebuffs (Caster Caster, Debuffeable target)
    {
        for (BDebuffI debuff: listaDeDebuffsQueAplica)
        {   debuff.aplicarDebuff(Caster, target);}
    }

    @Override public float getValorTotal(Caster Caster, int statID)
    {
        if (Caster instanceof CasterPersonalizable)
        {   return ((CasterPersonalizable) Caster).getSkillPersonalizado(id).getValorTotal(statID); }
        else return getSkillStat(statID).getValorBase();
    }

    @Override public void castear (Caster Caster, int targetX, int targetY, MundoI mundo)
    {
        if (Caster.isCasteando()) { }
        else
        {   //Marcamos al personaje como Casteando, y actualizamos su tiempo de casteo con el que marque el Spell (Stat Slot 0)
            Caster.setTotalCastingTime(getValorTotal(Caster, STAT_Cast));
            tipoSpell.ejecutarCasteo(this, Caster, targetX, targetY, mundo);
        }
    }
}
