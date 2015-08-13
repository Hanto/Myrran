package Model.Classes.Mobiles.Mob;// Created by Hanto on 13/08/2015.

import DTO.DTOsMob;
import Interfaces.EntidadesTipos.MobI;
import Model.AbstractClases.AbstractSteerable;

public abstract class MobNotificador extends AbstractSteerable implements MobI
{
    private DTOsMob.PosicionMob posicionDTO;
    private DTOsMob.OrientacionMob orientacionDTO;

    public MobNotificador()
    {
        posicionDTO = new DTOsMob.PosicionMob(this);
        orientacionDTO = new DTOsMob.OrientacionMob(this);
    }

    // NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            notificarActualizacion("posicionMob", null, posicionDTO);
        }
    }

    public void notificarSetOrientacion()
    {
        if (Math.abs(orientacionDTO.orientacion - getOrientacion()) > 0.1f)
        {
            orientacionDTO.orientacion = getOrientacion();
            notificarActualizacion("orientacionMob", null, orientacionDTO);
        }
    }
}
