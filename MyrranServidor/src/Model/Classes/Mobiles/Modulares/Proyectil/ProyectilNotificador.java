package Model.Classes.Mobiles.Modulares.Proyectil;// Created by Hanto on 13/08/2015.

import DTO.DTOsProyectil;
import InterfacesEntidades.EntidadesTipos.ProyectilI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class ProyectilNotificador extends SteerableAgent implements ProyectilI
{
    private DTOsProyectil.PosicionProyectil posicionDTO;

    public ProyectilNotificador()
    {   posicionDTO = new DTOsProyectil.PosicionProyectil(this); }

    // NOTIFICACION LOCAL:
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
    {   notificarActualizacion("disposeProyectil", null, new DTOsProyectil.DisposeProyectil(this)); }
}
