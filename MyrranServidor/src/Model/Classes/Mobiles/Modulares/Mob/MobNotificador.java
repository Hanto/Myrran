package Model.Classes.Mobiles.Modulares.Mob;// Created by Hanto on 13/08/2015.

import DTO.DTOsMob;
import InterfacesEntidades.EntidadesTipos.MobI;
import Model.Mobiles.Steerables.SteerableAgentAutonomo;

public abstract class MobNotificador extends SteerableAgentAutonomo implements MobI
{
    private DTOsMob.PosicionMob posicionDTO;
    private DTOsMob.OrientacionMob orientacionDTO;
    private DTOsMob.ModificarHPsMob modificarHPsDTO;

    public MobNotificador()
    {
        posicionDTO = new DTOsMob.PosicionMob(this);
        orientacionDTO = new DTOsMob.OrientacionMob(this);
        modificarHPsDTO = new DTOsMob.ModificarHPsMob(this);
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

    public void notificarAddModificarHPs(float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("modificarHPsMob", null, modificarHPsDTO);
    }
}
