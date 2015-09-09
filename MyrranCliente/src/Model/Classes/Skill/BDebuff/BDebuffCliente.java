package Model.Classes.Skill.BDebuff;// Created by Hanto on 04/06/2014.


import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.TipoBDebuffI;
import Model.Skills.BDebuff.BDebuff;

public class BDebuffCliente extends BDebuff
{
    //CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public BDebuffCliente(TipoBDebuffI tipoBDebuff)
    {   super(tipoBDebuff); }

    @Override public void aplicarDebuff(Caster caster, Debuffeable target) {}
    @Override public void actualizarTick (AuraI aura) {}
}
