package Model.Skills.BDebuff;// Created by Hanto on 04/06/2014.

import DTOs.DTOsAura;
import Interfaces.Misc.Observable.AbstractModel;
import Model.Settings;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;

public class Aura extends AbstractModel implements AuraI
{
    private int iD;
    private int stacks = 1;
    private int ticksAplicados = 0;
    private float duracion = 0;
    private float duracionMax;
    private Caster Caster;
    private Debuffeable target;
    private BDebuffI debuff;

    // AURAI:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getStacks()                        { return stacks; }
    @Override public int getTicksAplicados()                { return ticksAplicados; }
    @Override public int getTicksRestantes()                { return getMaxTicks()- ticksAplicados; }
    @Override public int getMaxTicks()                      { return (int) (duracionMax / Settings.BDEBUFF_DuracionTick); }
    @Override public float getDuracion()                    { return duracion; }
    @Override public float getDuracionMax()                 { return duracionMax; }
    @Override public Caster getCaster()                     { return Caster; }
    @Override public Debuffeable getTarget()               { return target; }
    @Override public BDebuffI getDebuff()                   { return debuff; }

    @Override public void setDuracion(float f)              { duracion = f; }
    @Override public void setDuracionMax(float f)           { duracionMax = f; }
    @Override public void setCaster(Caster Caster)          { this.Caster = Caster; }
    @Override public void setTarget(Debuffeable target)    { this.target = target; }
    @Override public void setDebuff(BDebuffI debuff)        { this.debuff = debuff; }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                            { return iD; }
    @Override public void setID(int iD)                     { this.iD = iD; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Aura (int iD, BDebuffI debuff, Caster caster, Debuffeable target)
    {
        this.iD = iD;
        this.debuff = debuff;
        this.Caster = caster;
        this.target = target;
        this.duracionMax = debuff.getValorTotal(caster, BDebuff.STAT_Duracion);
    }

    @Override public void dispose()
    {   this.eliminarObservadores(); }

    // NOTIFICAR VISTA:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setStacks(int numStacks)
    {
        stacks = numStacks;
        notificarActualizacion("setStacks", null, new DTOsAura.SetStacks(numStacks));
    }

    @Override public void setTicksAplicados(int ticksAplicados)
    {
        this.ticksAplicados = ticksAplicados;
        notificarActualizacion("setTicksAplicados", null, new DTOsAura.SetTicksAplicados(ticksAplicados));
    }

    // METODO DE ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void resetDuracion()
    {
        setTicksAplicados(0);
        setDuracion(getDuracion() % Settings.BDEBUFF_DuracionTick);
    }

    @Override public void actualizarAura (float delta)
    {
        setDuracion(duracion + delta);

        int tickActual =  (int) (duracion / Settings.BDEBUFF_DuracionTick);

        for (int i=ticksAplicados; i<tickActual; i++)
        {
            setTicksAplicados(ticksAplicados+1);
            debuff.actualizarTick(this);
        }
    }
}
