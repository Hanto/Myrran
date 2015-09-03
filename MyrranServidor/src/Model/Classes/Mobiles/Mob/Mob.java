package Model.Classes.Mobiles.Mob;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;

public class Mob extends MobNotificador implements MobI
{
    protected int iD;                                                       //Identificable:
    protected float actualHPs=1;                                            // Vulnerable:
    protected float maxHPs=2000;

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

    @Override public void checkColisionesConMob(Colisionable colisionable) {}
    @Override  public void checkColisionesConMuro() {}

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                                   { return actualHPs; }
    @Override public float getMaxHPs()                                      { return maxHPs; }
    @Override public void setActualHPs(float HPs)                           { modificarHPs(HPs - actualHPs); }
    @Override public void setMaxHPs(float HPs)                              { maxHPs = HPs; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Mob(int iD, int ancho, int alto)
    {
        this.iD = iD;
        this.setAncho(ancho);
        this.setAlto(alto);
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

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        if ( steeringBehavior != null)
            super.calcularSteering(delta);
    }
}
