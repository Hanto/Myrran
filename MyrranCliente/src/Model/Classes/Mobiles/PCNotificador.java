package Model.Classes.Mobiles;// Created by Hanto on 24/07/2014.

import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PCI;

public class PCNotificador
{
    public PCI mobPC;

    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOsPlayer.Animacion animacion = new DTOsPlayer.Animacion();
    public DTOsPlayer.Posicion posicion = new DTOsPlayer.Posicion();
    public DTOsPlayer.ModificarHPs modificarHPs = new DTOsPlayer.ModificarHPs();

    public PCNotificador(PCI mobPC)
    {   this.mobPC = mobPC; }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(float x, float y)
    {
        posicion.posX = (int)x; posicion.posY = (int)y;
        mobPC.notificarActualizacion("posicion", null, posicion);
    }

    public void setNumAnimacion(int numAnimacion)
    {
        animacion.numAnimacion = (short)numAnimacion;
        mobPC.notificarActualizacion("numAnimacion", null, animacion);
    }

    public void setModificarHPs (float HPs)
    {
        modificarHPs.HPs = HPs;
        mobPC.notificarActualizacion("ModificarHPs", null, modificarHPs); }

    public void setNombre(String nombre)
    {   mobPC.notificarActualizacion("nombre", null, new DTOsPlayer.Nombre(nombre)); }

    public void setDispose()
    {   mobPC.notificarActualizacion("Eliminar", null, new DTOsPlayer.EliminarPC(mobPC.getConnectionID())); }

}
