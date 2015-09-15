package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.


import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;

public interface BDebuffI extends SkillI
{
    //SET
    public void setTipoBDebuff(TipoBDebuffI tipoBDebuff);
    public void setIsDebuff (boolean b);
    public void setStacksMaximos (byte i);

    //GET:
    public TipoBDebuffI getTipoBDebuff();
    public boolean isDebuff ();
    public byte getStacksMaximos ();

    //METODOS:
    public void aplicarDebuff(Caster Caster, Debuffeable target);
    public void actualizarTick (AuraI aura);

    //TODO CAMBIAR Y BORRAR:
    public float getValorTotal(Caster Caster, int statID);

}
