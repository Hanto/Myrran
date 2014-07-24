package Model.Classes.Mobiles;// Created by Hanto on 24/07/2014.

import DTO.DTOsPC;
import Interfaces.Model.AbstractModel;

public class PCNotificador
{
    public AbstractModel model;

    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOsPC.Animacion animacion = new DTOsPC.Animacion();
    public DTOsPC.Posicion posicion = new DTOsPC.Posicion();
    public DTOsPC.ModificarHPs modificarHPs = new DTOsPC.ModificarHPs();

    public PCNotificador(AbstractModel model)
    {   this.model = model; }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(float x, float y)
    {
        posicion.posX = (int)x; posicion.posY = (int)y;
        model.notificarActualizacion("posicion", null, posicion);
    }

    public void setNumAnimacion(int numAnimacion)
    {
        animacion.numAnimacion = (short)numAnimacion;
        model.notificarActualizacion("numAnimacion", null, animacion);
    }

    public void setModificarHPs (float HPs)
    {
        modificarHPs.HPs = HPs;
        model.notificarActualizacion("ModificarHPs", null, modificarHPs); }

    public void setNombre(String nombre)
    {   model.notificarActualizacion("nombre", null, new DTOsPC.Nombre(nombre)); }

    public void setDispose()
    {   model.notificarActualizacion("Eliminar", null, new DTOsPC.Dispose()); }

}
