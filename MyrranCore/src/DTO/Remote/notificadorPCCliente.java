package DTO.Remote;// Created by Hanto on 24/07/2014.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.Model.AbstractModel;

public class notificadorPCCliente
{
    public AbstractModel model;

    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOs.Animacion animacion = new DTOs.Animacion();
    public DTOs.Posicion posicion = new DTOs.Posicion();
    public DTOs.CastingTimePercent castingTime = new DTOs.CastingTimePercent();
    public DTOs.ModificarHPs modificarHPs = new DTOs.ModificarHPs();

    public notificadorPCCliente(AbstractModel model)
    {   this.model = model; }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setNumAnimacion(int numAnimacion)
    {
        animacion.numAnimacion = (short)numAnimacion;
        model.notificarActualizacion("numAnimacion", null, animacion);
    }

    public void setPosition(float x, float y)
    {
        posicion.posX = (int)x; posicion.posY = (int)y;
        model.notificarActualizacion("posicion", null, posicion);
    }

    public void setModificarHPs (float HPs)
    {
        modificarHPs.HPs = HPs;
        model.notificarActualizacion("ModificarHPs", null, modificarHPs); }

    public void setCastingTime(Caster caster)
    {
        castingTime.castingTimePercent = caster.getActualCastingTime() == 0 && caster.getTotalCastingTime() == 0 ? 100 :
                caster.getActualCastingTime() / caster.getTotalCastingTime();
        model.notificarActualizacion("CastingTime", null, castingTime);
    }

    public void setNombre(String nombre)
    {   model.notificarActualizacion("nombre", null, new DTOs.Nombre(nombre)); }

    public void setMaxHPs(float HPs)
    {   model.notificarActualizacion("MaxHPs", null, new DTOs.MaxHPs(HPs)); }

    public void setDispose()
    {   model.notificarActualizacion("Eliminar", null, new DTOs.Dispose()); }

}
