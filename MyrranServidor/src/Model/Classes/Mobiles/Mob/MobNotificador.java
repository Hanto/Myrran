package Model.Classes.Mobiles.Mob;// Created by Hanto on 13/08/2015.

import DTOs.DTOsEspacial;
import DTOs.DTOsOrientable;
import DTOs.DTOsVulnerable;
import InterfacesEntidades.EntidadesTipos.MobI;
import Model.Mobiles.Steerables.SteerableAgentAutonomo;

public abstract class MobNotificador extends SteerableAgentAutonomo implements MobI
{
    private DTOsEspacial.Posicion posicionDTO;
    private DTOsOrientable.Orientacion orientacionDTO;
    private DTOsVulnerable.ModificarHPs modificarHPsDTO;

    public MobNotificador()
    {
        posicionDTO = new DTOsEspacial.Posicion(this);
        orientacionDTO = new DTOsOrientable.Orientacion(this);
        modificarHPsDTO = new DTOsVulnerable.ModificarHPs(this);
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
