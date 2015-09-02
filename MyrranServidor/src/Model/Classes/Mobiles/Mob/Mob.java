package Model.Classes.Mobiles.Mob;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;

public class Mob extends MobNotificador implements MobI
{
    //Identificable:
    protected int iD;

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)                    { }
    @Override public void setDireccion(float grados)                        { }


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

    @Override public void actualizarFisica(float delta, MundoI mundo)
    {
        if ( steeringBehavior != null)
            super.calcularSteering(delta);
    }
}
