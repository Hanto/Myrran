package Model.Classes.Skill.BDebuff;// Created by Hanto on 04/06/2014.

import Model.Settings;
import Interfaces.BDebuff.AuraI;
import Interfaces.BDebuff.BDebuffI;
import Interfaces.EntidadesPropiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Caster;

public class Aura implements AuraI
{
    private byte stacks = 1;
    private byte ticksAplicados = 0;
    private float duracion = 0;
    private float duracionMax;
    private Caster Caster;
    private Debuffeable target;
    private BDebuffI debuff;

    //SET:
    @Override public byte getStacks()                       { return stacks; }
    @Override public byte getTicksAplicados()               { return ticksAplicados; }
    @Override public float getDuracion()                    { return duracion; }
    @Override public float getDuracionMax()                 { return duracionMax; }
    @Override public Caster getCaster()                     { return Caster; }
    @Override public Debuffeable getTarget()                { return target; }
    @Override public BDebuffI getDebuff()                   { return debuff; }

    //GET:
    @Override public void setStacks(byte b)                 { stacks = b; }
    @Override public void setTicksAplicados(byte b)         { ticksAplicados = b; }
    @Override public void setDuracion(float f)              { duracion = f; }
    @Override public void setDuracionMax(float f)           { duracionMax = f; }
    @Override public void setCaster(Caster Caster)          { this.Caster = Caster; }
    @Override public void setTarget(Debuffeable target)     { this.target = target; }
    @Override public void setDebuff(BDebuffI debuff)        { this.debuff = debuff; }

    public Aura (BDebuffI debuff, Caster Caster, Debuffeable target)
    {
        this.debuff = debuff;
        this.Caster = Caster;
        this.target = target;
    }

    @Override public void actualizarAura (float delta)
    {
        duracion += delta;

        int tickActualk =  (int) (duracion / Settings.BDEBUFF_DuracionTick);

        for (int i=ticksAplicados; i<tickActualk; i++)
        {
            ticksAplicados++;
            debuff.actualizarTick(this);
        }
    }
}