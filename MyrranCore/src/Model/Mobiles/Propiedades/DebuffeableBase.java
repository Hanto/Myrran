package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import Interfaces.BDebuff.AuraI;
import InterfacesEntidades.EntidadesPropiedades.Debuffeable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebuffeableBase implements Debuffeable
{
    protected List<AuraI> listaDeAuras = new ArrayList<>();

    public void añadirAura(AuraI aura)                            { listaDeAuras.add(aura); }
    public void eliminarAura(AuraI aura)                          { listaDeAuras.remove(aura); }
    public Iterator<AuraI> getAuras()                             { return listaDeAuras.iterator(); }

    public void actualizarAuras (float delta)
    {
        AuraI aura;
        Iterator<AuraI> aurasIteator = getAuras();
        while (aurasIteator.hasNext())
        {
            aura = aurasIteator.next();
            aura.actualizarAura(delta);
            if (aura.getDuracion() >= aura.getDuracionMax())
                aurasIteator.remove();
        }
    }

}
