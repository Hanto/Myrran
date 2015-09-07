package Model.Skills.BDebuff;// Created by Hanto on 04/06/2014.


import Interfaces.Misc.BDebuff.AuraI;
import Interfaces.Misc.BDebuff.BDebuffI;
import Interfaces.Misc.BDebuff.TipoBDebuffI;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Model.Settings;
import Model.Skills.SkillsPersonalizados.SkillStat;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;

public class BDebuff extends AbstractModel implements BDebuffI
{
    protected final int STAT_Duracion = 0;

    protected String id;
    protected String nombre;
    protected String descripcion;
    protected boolean isDebuff = false;
    protected byte stacksMaximos = 0;
    protected TipoBDebuffI tipoBDebuff;                             //Command Pattern: Codigo que se ejecuta al castear el skill

    private SkillStat[] skillStats;                               //Stats concretos del skill

    //SET
    @Override public void setID(String id)                          { this.id = id; }
    @Override public void setNombre (String nombre)                 { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)       { this.descripcion = descripcion; }
    @Override public void setIsDebuff (boolean b)                   { isDebuff = b; }
    @Override public void setStacksMaximos (byte i)                 { stacksMaximos = i; }
    @Override public void setTipoBDebuff(TipoBDebuffI tipoBDebuff)  { this.tipoBDebuff = tipoBDebuff; }

    //GET:
    @Override public String getID()                                 { return id; }
    @Override public String getNombre ()                            { return nombre; }
    @Override public String getDescripcion ()                       { return descripcion; }
    @Override public boolean isDebuff ()                            { return isDebuff; }
    @Override public byte getStacksMaximos ()                       { return stacksMaximos; }
    @Override public TipoBDebuffI getTipoBDebuff()                  { return tipoBDebuff; }
    @Override public SkillStat getSkillStat(int statID)             { return skillStats[statID]; }
    @Override public Iterator<SkillStat> getSkillStats()            { return Arrays.asList(skillStats).iterator(); }
    @Override public int getNumSkillStats()                         { return skillStats.length; }

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
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

    private AuraI auraExisteYEsDelCaster(Caster Caster, Debuffeable target)
    {
        AuraI aura;
        Iterator<AuraI> iterator = target.getAuras();

        while (iterator.hasNext())
        {
            aura = iterator.next();
            if (aura.getDebuff().getID().equals(id) && aura.getCaster() == Caster)
            {   return aura; }
        }
        return null;
    }

    @Override public float getValorTotal(Caster Caster, int statID)
    {
        if (Caster instanceof CasterPersonalizable)
        {   return ((CasterPersonalizable) Caster).getSkillPersonalizado(id).getValorTotal(statID); }
        else return getSkillStat(statID).getValorBase();
    }

    @Override public void aplicarDebuff(Caster Caster, Debuffeable target)
    {
        AuraI aura = auraExisteYEsDelCaster(Caster, target);

        if (aura != null)
        {
            aura.setTicksAplicados((byte)0);
            if (aura.getStacks() < stacksMaximos) aura.setStacks((byte) (aura.getStacks()+1));
            aura.setDuracion(aura.getDuracion() % Settings.BDEBUFF_DuracionTick);
        }
        else
        {
            aura = new Aura(this, Caster, target);
            aura.setDuracionMax(getValorTotal(Caster, STAT_Duracion));
            target.añadirAura(aura);
        }
    }

    @Override public void actualizarTick (AuraI aura)
    {   tipoBDebuff.actualizarTick(aura); }
}
