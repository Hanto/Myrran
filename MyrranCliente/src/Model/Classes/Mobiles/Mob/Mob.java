package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import DTO.DTOsMob;
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

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setDireccion(float x, float y)            { }
    @Override public void setDireccion(float grados)                { }



    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Mob(int iD, Cuerpo cuerpo)
    {
        this.iD = iD;
        this.cuerpo = cuerpo;
    }

    @Override public void dispose()
    {   cuerpo.dispose(); }

    @Override public void setPosition(float x, float y)
    {
        cuerpo.setPosition(x, y);
        posicion.set(x, y);

        DTOsMob.PosicionMob posicionMob = new DTOsMob.PosicionMob(this);
        notificarActualizacion("posicionMob", null, posicionMob);
    }

    @Override public void setOrientacion(float radianes)
    {
        super.setOrientacion(radianes);
        cuerpo.getBody().setTransform(cuerpo.getBody().getPosition().x, cuerpo.getBody().getPosition().y, radianes);

        DTOsMob.OrientacionMob orientacionMob = new DTOsMob.OrientacionMob(this);
        notificarActualizacion("orientacionMob", null, orientacionMob);
    }


    @Override public void actualizar(float delta, MundoI mundo)
    { }

}
