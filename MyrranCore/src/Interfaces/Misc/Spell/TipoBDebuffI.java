package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.

public interface TipoBDebuffI extends SkillI
{
    //SET:
    public void setIsDebuff (boolean b);
    public void setStacksMaximos (byte i);

    //GET
    public boolean getIsDebuff();
    public byte getStacksMaximos();

    //METODOS:
    public void inicializarSkillStats();
    public void actualizarTick(AuraI aura);
}
