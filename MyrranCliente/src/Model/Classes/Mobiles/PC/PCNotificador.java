package Model.Classes.Mobiles.PC;// Created by Hanto on 24/07/2014.

import DTOs.DTOsAnimable;
import DTOs.DTOsEspacial;
import DTOs.DTOsPCStats;
import DTOs.DTOsVulnerable;
import InterfacesEntidades.EntidadesTipos.PCI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class PCNotificador extends SteerableAgent implements PCI
{
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsEspacial.Posicion posicionDTO;
    private DTOsAnimable.NumAnimacion animacionDTO;
    private DTOsVulnerable.ModificarHPs modificarHPsDTO;

    public PCNotificador()
    {
        posicionDTO = new DTOsEspacial.Posicion(this);
        animacionDTO = new DTOsAnimable.NumAnimacion(this);
        modificarHPsDTO = new DTOsVulnerable.ModificarHPs(this);
    }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            notificarActualizacion("posicion", null, posicionDTO);
        }
    }

    public void notificarSetNumAnimacion()
    {
        if (animacionDTO.numAnimacion != (short)getNumAnimacion())
        {
            animacionDTO.numAnimacion = (short) getNumAnimacion();
            notificarActualizacion("numAnimacion", null, animacionDTO);
        }
    }

    public void notificarSetModificarHPs (float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("ModificarHPs", null, modificarHPsDTO); }

    public void notificarSetNombre(String nombre)
    {   notificarActualizacion("nombre", null, new DTOsPCStats.Nombre(nombre)); }
}
