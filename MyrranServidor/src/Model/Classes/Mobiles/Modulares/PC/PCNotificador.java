package Model.Classes.Mobiles.Modulares.PC;// Created by Hanto on 24/07/2014.

import DTO.DTOsPC;
import DTOs.DTOsEspacial;
import InterfacesEntidades.EntidadesTipos.PCSI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class PCNotificador extends SteerableAgent implements PCSI
{
    private DTOsEspacial.PosicionEspacial posicionDTO;
    private DTOsPC.NumAnimacionPC animacionDTO;
    private DTOsPC.ModificarHPsPC modificarHPsDTO;

    public PCNotificador()
    {
        posicionDTO = new DTOsEspacial.PosicionEspacial(this);
        animacionDTO = new DTOsPC.NumAnimacionPC(this);
        modificarHPsDTO = new DTOsPC.ModificarHPsPC(this);
    }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            notificarActualizacion("posicionPC", null, posicionDTO);
        }
    }

    public void notificarSetNumAnimacion()
    {
        if (animacionDTO.numAnimacion != (short)getNumAnimacion())
        {
            animacionDTO.numAnimacion = (short)getNumAnimacion();
            notificarActualizacion("numAnimacionPC", null, animacionDTO);
        }
    }

    public void notificarSetDispose()
    {   notificarActualizacion("disposePC", null, new DTOsPC.EliminarPC(this)); }

    public void notificarAddModificarHPs(float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("modificarHPsPC", null, modificarHPsDTO);
    }

    public void notificarAddSpellPersonalizado(String spellID)
    {   notificarActualizacion("skillPersonalizadoPC", null, new DTOsPC.AñadirSpellPersonalizadoPC(this, spellID)); }

    public void notificarAddNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   notificarActualizacion("numTalentosSkillPersonalizadoPC", null, new DTOsPC.NumTalentosSkillPersonalizadoPC(this, skillID, statID, valor));}
}