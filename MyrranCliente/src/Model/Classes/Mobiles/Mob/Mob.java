package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;
import Model.Cuerpos.Cuerpo;

public class Mob extends MobNotificador implements MobI
{
    protected int iD;                                                       // Identificable:
    protected Cuerpo cuerpo;                                                // Corporeo:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                    { return iD; }
    @Override public void setID(int iD)                             { this.iD = iD; }

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setPosition(float x, float y)             { cuerpo.setPosition(x, y); }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)            { }
    @Override public void setDireccion(float grados)                { }

    // ORIENTABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setOrientacion(float angulo)              { cuerpo.getBody().setTransform(cuerpo.getBody().getPosition().x, cuerpo.getBody().getPosition().y, angulo); }


    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Mob(int iD, Cuerpo cuerpo)
    {
        this.iD = iD;
        this.cuerpo = cuerpo;
    }

    @Override public void dispose()
    {   cuerpo.dispose(); }

    @Override public void actualizar(float delta, MundoI mundo)
    { }

}
