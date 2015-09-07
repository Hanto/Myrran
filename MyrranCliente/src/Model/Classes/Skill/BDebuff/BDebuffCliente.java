package Model.Classes.Skill.BDebuff;// Created by Hanto on 04/06/2014.


import Interfaces.Misc.BDebuff.AuraI;
import Interfaces.Misc.BDebuff.TipoBDebuffI;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Model.Skills.BDebuff.Aura;
import Model.Skills.BDebuff.BDebuff;

public class BDebuffCliente extends BDebuff
{
    //CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public BDebuffCliente(TipoBDebuffI tipoBDebuff)
    {   super(tipoBDebuff); }

    @Override public void aplicarDebuff(Caster Caster, Debuffeable target)
    {
        AuraI aura = new Aura(this, Caster, target);
        aura.setDuracionMax(getValorTotal(Caster, STAT_Duracion));
        target.añadirAura(aura);
    }

    @Override public void actualizarTick (AuraI aura) {}
}
