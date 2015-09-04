package Model.Classes.Mobiles.Monoliticos.PC;// Created by Hanto on 24/07/2014.

import DTO.DTOsPlayer;
import DTOs.DTOsVulnerable;
import InterfacesEntidades.EntidadesTipos.PCI;
import Model.Mobiles.Steerables.SteerableAgentOld;

public abstract class PCOldNotificador extends SteerableAgentOld implements PCI
{
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsPlayer.Animacion animacionDTO = new DTOsPlayer.Animacion();
    private DTOsPlayer.Posicion posicionDTO = new DTOsPlayer.Posicion();
    private DTOsVulnerable.ModificarHPs modificarHPsDTO = new DTOsVulnerable.ModificarHPs();

    public PCOldNotificador()
    { }

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
    {   notificarActualizacion("nombre", null, new DTOsPlayer.Nombre(nombre)); }
}
