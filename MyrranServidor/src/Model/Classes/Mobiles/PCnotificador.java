package Model.Classes.Mobiles;// Created by Hanto on 24/07/2014.

import DTO.DTOsPC;
import Interfaces.Model.AbstractModel;

public class PCNotificador
{
    public AbstractModel model;

    public DTOsPC.Posicion posicion = new DTOsPC.Posicion();
    public DTOsPC.Animacion animacion = new DTOsPC.Animacion();
    public DTOsPC.ModificarHPs modificarHPs = new DTOsPC.ModificarHPs();

    public PCNotificador(AbstractModel model)
    {   this.model = model; }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            model.notificarActualizacion("posicion", null, posicion);
        }
    }

    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.numAnimacion != (short)numAnimacion)
        {
            animacion.numAnimacion = (short)numAnimacion;
            model.notificarActualizacion("numAnimacion", null, animacion);
        }
    }

    public void setNombre(String nombre)
    {   model.notificarActualizacion("nombre", null, new DTOsPC.Nombre(nombre)); }

    public void setDispose()
    {   model.notificarActualizacion("dispose", null, new DTOsPC.Dispose()); }

    public void añadirModificarHPs(float HPs)
    {
        modificarHPs.HPs = HPs;
        model.notificarActualizacion("modificarHPs", null, modificarHPs);
    }

    public void añadirSkillPersonalizado(String skillID)
    {   model.notificarActualizacion("skillPersonalizado", null, new DTOsPC.SkillPersonalizado(skillID)); }

    public void añadirNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   model.notificarActualizacion("numTalentosSkillPersonalizado", null, new DTOsPC.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

}