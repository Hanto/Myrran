package Model.Classes.Mobiles.Mob;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesPropiedades.Propiedades.Vulnerable;
import Interfaces.EntidadesPropiedades.TipoMobile.MobStats;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Model.AI.Huellas.Huellas;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;

import java.util.Iterator;

public class Mob extends MobNotificador implements MobI
{
    private IDentificable identificable;
    private Vulnerable vulnerable;
    private Debuffeable debuffeable;
    private MobStats mobStats;

    public Mob(int iD, int ancho, int alto,
               IDentificable identificable, Vulnerable vulnerable,
               Debuffeable debuffeable, MobStats mobStats)
    {
        this.identificable = identificable;
        this.vulnerable = vulnerable;
        this.debuffeable = debuffeable;
        this.mobStats = mobStats;

        this.identificable.setID(iD);
        this.setAncho(ancho);
        this.setAlto(alto);
        this.setHuellas(new Huellas());
        this.setSeguible(true);
        this.setTiempoDecayHuellas(5f);

        this.vulnerable.setMaxHPs(10000);
        this.vulnerable.setActualHPs(10000);

        this.debuffeable.setNotificador(this);
    }

    @Override public void dispose()
    {   debuffeable.dispose(); }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                                    {   return identificable.getID(); }
    @Override public void setID(int iD)                             {   identificable.setID(iD); }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setDireccion(float x, float y)            { }
    @Override public void setDireccion(float grados)                { }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public float getActualHPs()                           {   return vulnerable.getActualHPs(); }
    @Override public float getMaxHPs()                              {   return vulnerable.getMaxHPs(); }
    @Override public void setActualHPs(float HPs)                   {   vulnerable.setActualHPs(HPs); }
    @Override public void setMaxHPs(float HPs)                      {   vulnerable.setMaxHPs(HPs); }

    // DEBUFFEABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public AuraI getAura(int auraID)
    {   return debuffeable.getAura(auraID); }
    @Override public Iterator<AuraI> getAuras()
    {   return debuffeable.getAuras(); }
    @Override public void añadirAura(BDebuffI debuff, Caster caster, Debuffeable target)
    {   debuffeable.añadirAura(debuff, caster, target);}
    @Override public void añadirAura(int iDAura, BDebuffI debuff, Caster caster, Debuffeable target)
    {   debuffeable.añadirAura(iDAura, debuff, caster, target); }
    @Override public void eliminarAura(int iDAura)
    {   debuffeable.eliminarAura(iDAura); }
    @Override public void actualizarAuras(float delta)
    {   debuffeable.actualizarAuras(delta); }
    @Override public void setNotificador(DeBuffeableNotificadorI notificador)
    {   debuffeable.setNotificador(notificador); }

    // CODIGO PERSONALIZADO:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setOrientacion(float radianes)
    {
        super.setOrientacion(radianes);
        notificarSetOrientacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        vulnerable.modificarHPs(HPs);
        notificarAddModificarHPs(HPs);
    }

    // ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta)
    {
        actualizarAuras(delta);
        actualizarHuellas(delta);
    }

    @Override public void actualizarIA(float delta, MundoI mundo)
    {

    }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        if (getSteeringBehavior() != null)
        {   calcularSteering(delta); }
    }


    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public void checkColisionesConMob(Colisionable colisionable)  {}
    @Override public void checkColisionesConMuro() {}
}
