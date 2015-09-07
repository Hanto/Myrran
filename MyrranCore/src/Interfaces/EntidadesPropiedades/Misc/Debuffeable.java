package Interfaces.EntidadesPropiedades.Misc;// Created by Hanto on 04/06/2014.

import Interfaces.Misc.BDebuff.AuraI;

import java.util.Iterator;

public interface Debuffeable
{
    public void añadirAura(AuraI aura);
    public void eliminarAura(AuraI aura);
    public Iterator<AuraI> getAuras();

    //UPDATE:
    public void actualizarAuras(float delta);
}
