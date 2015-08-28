package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 13/08/2015.

import DTO.DTOsProyectil;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.AI.Steering.SteerableAgent;

public abstract class ProyectilNotificador extends SteerableAgent implements ProyectilI
{
    private DTOsProyectil.PosicionProyectil posicionDTO;

    public ProyectilNotificador()
    {
        posicionDTO = new DTOsProyectil.PosicionProyectil(this);
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

    public void notificarSetDispose()
    {   notificarActualizacion("EliminarProyectil", null, new DTOsProyectil.DisposeProyectil(this)); }
}
