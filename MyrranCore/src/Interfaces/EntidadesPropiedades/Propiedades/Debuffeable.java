package Interfaces.EntidadesPropiedades.Propiedades;// Created by Hanto on 04/06/2014.

import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

public interface Debuffeable extends Disposable
{
    public AuraI getAura(int auraID);
    public Iterator<AuraI>getAuras();

    public void añadirAura(BDebuffI debuff, Caster caster, Debuffeable target);
    public void añadirAura(int iDAura, BDebuffI debuff, Caster caster, Debuffeable target);
    public void eliminarAura(int iDAura);

    //UPDATE:
    public void actualizarAuras(float delta);
    public void setNotificador(DeBuffeableNotificadorI notificador);
}
