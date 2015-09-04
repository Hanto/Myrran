package Model.Classes.Mobiles.Monoliticos.Mob;// Created by Hanto on 10/08/2015.

import Interfaces.BDebuff.AuraI;
import InterfacesEntidades.EntidadesPropiedades.Debuffeable;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Colisionable;
import InterfacesEntidades.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MobOld extends MobOldNotificador implements MobI, Debuffeable
{
    protected int iD;                                                       //Identificable:
    protected float actualHPs=10000;                                        // Vulnerable:
    protected float maxHPs=10000;
    protected List<AuraI> listaDeAuras = new ArrayList<>();                 // Debuffeable:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                    { }
    @Override public void setDireccion(float grados)                        { }

    // COLISION CALLBACKS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void checkColisionesConMob(Colisionable colisionable)  { }
    @Override  public void checkColisionesConMuro()                         { }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                                   { return actualHPs; }
    @Override public float getMaxHPs()                                      { return maxHPs; }
    @Override public void setActualHPs(float HPs)                           { modificarHPs(HPs - actualHPs); }
    @Override public void setMaxHPs(float HPs)                              { maxHPs = HPs; }

    // DEBUFFEABLE
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirAura(AuraI aura)                            { listaDeAuras.add(aura); }
    @Override public void eliminarAura(AuraI aura)                          { listaDeAuras.remove(aura); }
    @Override public Iterator<AuraI> getAuras()                             { return listaDeAuras.iterator(); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public MobOld(int iD, int ancho, int alto)
    {
        this.iD = iD;

        this.setAncho(ancho);
        this.setAlto(alto);
        this.setTiempoDecayHuellas(5f);
    }

    public void dispose()
    { }

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
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificarAddModificarHPs(HPs);
    }

    // METODOS ACTUALIZACION:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarAuras (float delta)
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

    @Override public void actualizarTimers(float delta)
    {
        actualizarAuras(delta);
        actualizarHuellas(delta);
    }

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        if ( steeringBehavior != null)
            super.calcularSteering(delta);
    }

    @Override public void actualizarIA(float delta, MundoI mundo) {}
}
