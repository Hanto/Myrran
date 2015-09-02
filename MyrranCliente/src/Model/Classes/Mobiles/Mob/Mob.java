package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;
import Model.Mobiles.Cuerpos.Cuerpo;

public class Mob extends MobNotificador implements MobI
{
    protected int iD;                                                       // Identificable:
    protected float actualHPs=1;                                            // Vulnerable:
    protected float maxHPs=2000;
    protected Cuerpo cuerpo;                                                // Corporeo:

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                    { return iD; }
    @Override public void setID(int iD)                             { this.iD = iD; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)            { }
    @Override public void setDireccion(float grados)                { }

    // VULNERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getActualHPs()                                   { return actualHPs; }
    @Override public float getMaxHPs()                                      { return maxHPs; }
    @Override public void setActualHPs(float HPs)                           { modificarHPs(HPs - actualHPs); }
    @Override public void setMaxHPs(float HPs)                              { maxHPs = HPs; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Mob(int iD, Cuerpo cuerpo)
    {
        this.iD = iD;
        this.cuerpo = cuerpo;
        this.setAncho(cuerpo.getAncho());
        this.setAlto(cuerpo.getAlto());
    }

    @Override public void dispose()
    {   cuerpo.dispose(); }

    @Override public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        cuerpo.setPosition(x, y);
        notificarSetPosition();
    }

    @Override public void setOrientacion(float radianes)
    {
        super.setOrientacion(radianes);
        cuerpo.getBody().setTransform(cuerpo.getBody().getPosition().x, cuerpo.getBody().getPosition().y, radianes);
        notificarSetOrientacion();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificarAddModificarHPs(HPs);
    }

    @Override public void actualizarFisica(float delta, MundoI mundo) {}

}
