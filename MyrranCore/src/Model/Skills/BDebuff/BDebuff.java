package Model.Skills.BDebuff;// Created by Hanto on 04/06/2014.


import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.TipoBDebuffI;
import Model.Skills.SkillsPersonalizados.SkillStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;

public class BDebuff extends AbstractModel implements BDebuffI
{
    protected static final int STAT_Duracion = 0;

    protected String id;
    protected String nombre;
    protected String descripcion;
    protected boolean isDebuff = false;
    protected byte stacksMaximos = 0;
    protected TipoBDebuffI tipoBDebuff;                             //Command Pattern: Codigo que se ejecuta al castear el skill

    private SkillStat[] skillStats;                                 //Stats concretos del skill

    //SET
    @Override public void setID(String id)                          { this.id = id; }
    @Override public void setNombre (String nombre)                 { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)       { this.descripcion = descripcion; }
    @Override public void setIsDebuff (boolean b)                   { isDebuff = b; }
    @Override public void setStacksMaximos (byte i)                 { stacksMaximos = i; }
    @Override public void setTipoBDebuff(TipoBDebuffI tipoBDebuff)  { this.tipoBDebuff = tipoBDebuff; }

    //GET:
    @Override public String getID()                                 { return id; }
    @Override public String getTipoID()                             { return tipoBDebuff.getID(); }
    @Override public String getNombre ()                            { return nombre; }
    @Override public String getDescripcion ()                       { return descripcion; }
    @Override public boolean isDebuff ()                            { return isDebuff; }
    @Override public byte getStacksMaximos ()                       { return stacksMaximos; }
    @Override public TipoBDebuffI getTipoBDebuff()                  { return tipoBDebuff; }
    @Override public SkillStat getSkillStat(int statID)             { return skillStats[statID]; }
    @Override public Iterator<SkillStat> getSkillStats()            { return Arrays.asList(skillStats).iterator(); }
    @Override public int getNumSkillStats()                         { return skillStats.length; }

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public BDebuff (TipoBDebuffI tipoBDebuff)
    {   //Se vincula el objeto que ejecutara los metodos de este tipo de Spell
        if (tipoBDebuff == null) { logger.error("ERROR: TipoSpellID no encontrado."); return; }
        this.tipoBDebuff = tipoBDebuff;

        nombre = tipoBDebuff.getNombre();
        descripcion = tipoBDebuff.getDescripcion();
        isDebuff = tipoBDebuff.getIsDebuff();
        stacksMaximos = tipoBDebuff.getStacksMaximos();

        //y se copian sus Stats base:
        skillStats = new SkillStat[tipoBDebuff.getNumSkillStats()];
        for (int i=0; i<skillStats.length;i++)
        {
            SkillStat statSkill = new SkillStat(tipoBDebuff.getSkillStat(i));
            skillStats[i] = statSkill;
        }
    }

    @Override public float getValorTotal(Caster caster, int statID)
    {
        if (caster instanceof CasterPersonalizable)
        {   return ((CasterPersonalizable) caster).getSkillPersonalizado(id).getValorTotal(statID); }
        else return getSkillStat(statID).getValorBase();
    }

    @Override public void aplicarDebuff(Caster caster, Debuffeable target)
    {   target.añadirAura(this, caster, target); }

    @Override public void actualizarTick (AuraI aura)
    {   tipoBDebuff.actualizarTick(aura); }
}
