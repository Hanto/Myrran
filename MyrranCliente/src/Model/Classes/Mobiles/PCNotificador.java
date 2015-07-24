package Model.Classes.Mobiles;// Created by Hanto on 24/07/2014.

import DTO.DTOsPC;
import Interfaces.EntidadesTipos.MobPC;

public class PCNotificador
{
    public MobPC mobPC;

    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOsPC.Animacion animacion = new DTOsPC.Animacion();
    public DTOsPC.Posicion posicion = new DTOsPC.Posicion();
    public DTOsPC.ModificarHPs modificarHPs = new DTOsPC.ModificarHPs();

    public PCNotificador(MobPC mobPC)
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
    {   mobPC.notificarActualizacion("nombre", null, new DTOsPC.Nombre(nombre)); }

    public void setDispose()
    {   mobPC.notificarActualizacion("Eliminar", null, new DTOsPC.Dispose(mobPC)); }

}
