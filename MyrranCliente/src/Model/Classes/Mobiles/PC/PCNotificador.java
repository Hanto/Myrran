package Model.Classes.Mobiles.PC;// Created by Hanto on 24/07/2014.

import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PCI;
import Model.AI.Steering.SteerableAgent;

public abstract class PCNotificador extends SteerableAgent implements PCI
{
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsPlayer.Animacion animacionDTO = new DTOsPlayer.Animacion();
    private DTOsPlayer.Posicion posicionDTO = new DTOsPlayer.Posicion();
    private DTOsPlayer.ModificarHPs modificarHPsDTO = new DTOsPlayer.ModificarHPs();

    public PCNotificador()
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

    public void notificarSetDispose()
    {   notificarActualizacion("Eliminar", null, new DTOsPlayer.EliminarPC(getID())); }

}
