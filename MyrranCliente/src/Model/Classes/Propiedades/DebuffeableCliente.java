package Model.Classes.Propiedades;// Created by Hanto on 09/09/2015.

import Interfaces.Misc.Spell.AuraI;
import Model.Mobiles.Propiedades.DebuffeableBase;
import com.badlogic.gdx.utils.Pool;

public class DebuffeableCliente extends DebuffeableBase
{
    public DebuffeableCliente() {}

    public DebuffeableCliente(Pool pool)
    {   super(pool); }

    @Override public void actualizarAuras (float delta)
    {
        for (AuraI aura : listaDeAuras)
        {   aura.actualizarAura(delta); }
    }
}
