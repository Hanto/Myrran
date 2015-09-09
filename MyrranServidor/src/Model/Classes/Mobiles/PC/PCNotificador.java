package Model.Classes.Mobiles.PC;// Created by Hanto on 24/07/2014.

import DTOs.*;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Misc.Spell.AuraI;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;
import Model.Mobiles.Steerables.SteerableAgent;

public abstract class PCNotificador extends SteerableAgent implements PCI,
        DeBuffeableNotificadorI
{
    private DTOsEspacial.Posicion posicionDTO;
    private DTOsAnimable.NumAnimacion animacionDTO;
    private DTOsVulnerable.ModificarHPs modificarHPsDTO;

    private DTOsDebuffeable.AñadirAura añadirAuraDTO;
    private DTOsDebuffeable.EliminarAura eliminarAuraDTO;
    private DTOsDebuffeable.ModificarStacks modificarStacksDTO;

    public PCNotificador()
    {
        posicionDTO = new DTOsEspacial.Posicion(this);
        animacionDTO = new DTOsAnimable.NumAnimacion(this);
        modificarHPsDTO = new DTOsVulnerable.ModificarHPs(this);

        añadirAuraDTO = new DTOsDebuffeable.AñadirAura(this);
        eliminarAuraDTO = new DTOsDebuffeable.EliminarAura(this);
        modificarStacksDTO = new DTOsDebuffeable.ModificarStacks(this);
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

    public void notificarAñadirAura(AuraI aura)
    {
        añadirAuraDTO.aura= aura;
        notificarActualizacion("añadirAura", null, añadirAuraDTO);
    }

    public void notificarEliminarAura(AuraI aura)
    {
        eliminarAuraDTO.aura = aura;
        notificarActualizacion("eliminarAura", null, eliminarAuraDTO);
    }

    public void notificarIncrementarStack(AuraI aura) {
        modificarStacksDTO.aura = aura;
        notificarActualizacion("setModificarStacks", null, modificarStacksDTO);
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