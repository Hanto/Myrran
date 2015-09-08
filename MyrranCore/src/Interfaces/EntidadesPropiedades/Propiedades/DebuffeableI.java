package Interfaces.EntidadesPropiedades.Propiedades;// Created by Hanto on 04/06/2014.

import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;

public interface DebuffeableI
{
    public AuraI getAura(int auraID);

    public void añadirAura(BDebuffI debuff, Caster caster, DebuffeableI target);
    public void añadirAura(int iDAura, BDebuffI debuff, Caster caster, DebuffeableI target);
    public void eliminarAura(int iDAura);

    //UPDATE:
    public void actualizarAuras(float delta);
    public void setNotificador(DeBuffeableNotificadorI notificador);
}
