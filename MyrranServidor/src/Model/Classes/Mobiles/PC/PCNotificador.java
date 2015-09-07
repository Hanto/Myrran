package Model.Classes.Mobiles.PC;// Created by Hanto on 24/07/2014.

import DTOs.*;
import InterfacesEntidades.EntidadesTipos.PCI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class PCNotificador extends SteerableAgent implements PCI
{
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

    public void notificarAddModificarHPs(float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("modificarHPsPC", null, modificarHPsDTO);
    }

    public void notificarSetDispose()
    {   notificarActualizacion("disposePC", null,
            new DTOsDisposable.Dispose(this)); }

    public void notificarAddSpellPersonalizado(String spellID)
    {   notificarActualizacion("skillPersonalizadoPC", null,
            new DTOsCasterPersonalizable.AñadirSpellPersonalizado(this, spellID)); }

    public void notificarAddNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   notificarActualizacion("numTalentosSkillPersonalizadoPC", null,
            new DTOsCasterPersonalizable.SetNumTalentosSkillPersonalizado(this, skillID, statID, valor));}
}