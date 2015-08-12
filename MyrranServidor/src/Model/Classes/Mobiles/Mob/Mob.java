package Model.Classes.Mobiles.Mob;// Created by Hanto on 10/08/2015.

import DTO.DTOsMob;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;
import Model.Classes.AI.Steering.SteerableAbstract;

public class Mob extends SteerableAbstract implements MobI
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

    public Mob(int iD)
    {   this.iD = iD; }

    public void dispose()
    { }

    //------------------------------------------------------------------------------------------------------------------

    @Override public void setPosition(float x, float y)
    {
        posicion.set(x, y);
        DTOsMob.PosicionMob posicionMob = new DTOsMob.PosicionMob(this);
        notificarActualizacion("posicionMob", null, posicionMob);
    }

    @Override public void actualizar(float delta, MundoI mundo)
    {
        if ( steeringBehavior != null)
        {
            int oldX = (int)getX();
            int oldY = (int)getY();
            int oldOrientacion = (int)getOrientacion();

            super.calcularSteering(delta);

            if (oldX != (int)getX() || oldY != (int)getY())
            {
                DTOsMob.PosicionMob posicionMob = new DTOsMob.PosicionMob(this);
                notificarActualizacion("posicionMob", null, posicionMob);
            }

            if (oldOrientacion != getOrientacion())
            {
                DTOsMob.OrientacionMob orientacionMob = new DTOsMob.OrientacionMob(this);
                notificarActualizacion("OrientacionMob", null, orientacionMob);
            }
        }
    }
}
