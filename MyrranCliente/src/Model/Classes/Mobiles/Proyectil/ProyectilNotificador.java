package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 13/08/2015.

import DTOs.DTOsEspacial;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class ProyectilNotificador extends SteerableAgent implements ProyectilI
{
    private DTOsEspacial.Posicion posicionDTO;

    public ProyectilNotificador()
    {
        posicionDTO = new DTOsEspacial.Posicion(this);
    }

    // NOTIFICADOR LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            notificarActualizacion("posicionProyectil", null, posicionDTO);
        }
    }
}
