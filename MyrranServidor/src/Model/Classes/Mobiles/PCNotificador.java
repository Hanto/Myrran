package Model.Classes.Mobiles;// Created by Hanto on 24/07/2014.

import DTO.DTOsPC;
import Interfaces.EntidadesTipos.PCI;

public class PCNotificador
{
    public PCI pc;

    public DTOsPC.PosicionPC posicion;
    public DTOsPC.NumAnimacionPC animacion;
    public DTOsPC.ModificarHPsPC modificarHPs;

    public PCNotificador(PCI pc)
    {
        this.pc = pc;
        posicion = new DTOsPC.PosicionPC(pc);
        animacion = new DTOsPC.NumAnimacionPC(pc);
        modificarHPs = new DTOsPC.ModificarHPsPC(pc);
    }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition()
    {
        if (posicion.posX != (int)pc.getX() || posicion.posY != (int)pc.getY())
        {
            posicion.posX = (int) pc.getX();
            posicion.posY = (int) pc.getY();
            pc.notificarActualizacion("posicion", null, posicion);
        }
    }

    public void setNumAnimacion()
    {
        if (animacion.numAnimacion != (short)pc.getNumAnimacion())
        {
            animacion.numAnimacion = (short)pc.getNumAnimacion();
            pc.notificarActualizacion("numAnimacion", null, animacion);
        }
    }

    public void setDispose()
    {   pc.notificarActualizacion("dispose", null, new DTOsPC.EliminarPC(pc)); }

    public void addModificarHPs(float HPs)
    {
        modificarHPs.HPs = HPs;
        pc.notificarActualizacion("modificarHPs", null, modificarHPs);
    }

    public void addSpellPersonalizado(String spellID)
    {   pc.notificarActualizacion("skillPersonalizado", null, new DTOsPC.AñadirSpellPersonalizadoPC(pc, spellID)); }

    public void addNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   pc.notificarActualizacion("numTalentosSkillPersonalizado", null, new DTOsPC.NumTalentosSkillPersonalizadoPC(pc, skillID, statID, valor));}
}