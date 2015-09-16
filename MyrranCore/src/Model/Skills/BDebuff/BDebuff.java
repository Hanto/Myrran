package Model.Skills.BDebuff;// Created by Hanto on 04/06/2014.


import DTOs.DTOsSkill;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.TipoBDebuffI;
import Model.Skills.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;

public class BDebuff extends Skill implements BDebuffI
{
    protected static final int STAT_Duracion = 0;

    protected boolean isDebuff = false;
    protected byte stacksMaximos = 0;
    protected TipoBDebuffI tipoBDebuff;

    @Override public void setIsDebuff (boolean b)                   { isDebuff = b; }
    @Override public void setStacksMaximos (byte i)                 { stacksMaximos = i; }
    @Override public void setTipoBDebuff(TipoBDebuffI tipoBDebuff)  { this.tipoBDebuff = tipoBDebuff; }
    @Override public String getTipoID()                             { return tipoBDebuff.getID(); }
    @Override public boolean isDebuff ()                            { return isDebuff; }
    @Override public byte getStacksMaximos ()                       { return stacksMaximos; }
    @Override public TipoBDebuffI getTipoBDebuff()                  { return tipoBDebuff; }

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public BDebuff (TipoBDebuffI tipoBDebuff)
    {
        super(tipoBDebuff);
        this.tipoBDebuff = tipoBDebuff;

        this.isDebuff = tipoBDebuff.getIsDebuff();
        this.stacksMaximos = tipoBDebuff.getStacksMaximos();
    }

    public BDebuff (BDebuffI debuff)
    {
        super(debuff);
        this.tipoBDebuff = debuff.getTipoBDebuff();

        this.isDebuff = debuff.isDebuff();
        this.stacksMaximos = debuff.getStacksMaximos();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    public float getValorTotal(Caster caster, int statID)
    {
        if (caster instanceof CasterPersonalizable)
        {   return ((CasterPersonalizable) caster).getSkillPersonalizado(id).getValorTotal(statID); }
        else return getSkillStat(statID).getValorBase();
    }

    @Override public void aplicarDebuff(Caster caster, Debuffeable target)
    {   target.añadirAura(this, caster, target); }

    @Override public void actualizarTick (AuraI aura)
    {   tipoBDebuff.actualizarTick(aura); }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.setSkillStat)
        {
            ((DTOsSkill.setSkillStat) evt.getNewValue()).skillID = id;
            notificarActualizacion("setSkillStat", null, evt.getNewValue());
        }
        else if (evt.getNewValue() instanceof DTOsSkill.setSpellSlot)
        {
            ((DTOsSkill.setSpellSlot) evt.getNewValue()).spellID = id;
            notificarActualizacion("setSpellSlot", null, evt.getNewValue());
        }
    }
}
