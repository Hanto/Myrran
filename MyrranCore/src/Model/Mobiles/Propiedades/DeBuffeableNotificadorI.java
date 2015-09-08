package Model.Mobiles.Propiedades;// Created by Hanto on 08/09/2015.

import Interfaces.Misc.Spell.AuraI;

public interface DeBuffeableNotificadorI
{
    public void notificarAñadirAura(AuraI aura);
    public void notificarEliminarAura(AuraI aura);

}
