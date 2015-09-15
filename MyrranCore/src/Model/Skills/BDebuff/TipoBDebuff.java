package Model.Skills.BDebuff;// Created by Hanto on 04/06/2014.

import Interfaces.Misc.Spell.TipoBDebuffI;
import Model.Skills.Skill;

import java.beans.PropertyChangeEvent;

public abstract class TipoBDebuff extends Skill implements TipoBDebuffI
{
    protected boolean isDebuff = false;
    private byte stacksMaximos = 0;


    @Override public void setIsDebuff (boolean b)                       { isDebuff = b; }
    @Override public void setStacksMaximos (byte i)                     { stacksMaximos = i; }

    @Override public boolean getIsDebuff ()                             { return isDebuff; }
    @Override public byte getStacksMaximos ()                           { return stacksMaximos; }
    @Override public String getTipoID()                                 { return null; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public TipoBDebuff()
    {
        setID(this.getClass().getSimpleName().toUpperCase());
        inicializarSkillStats();
    }

    @Override public void propertyChange(PropertyChangeEvent evt) {}
}
