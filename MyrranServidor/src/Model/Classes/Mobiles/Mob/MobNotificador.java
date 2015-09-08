package Model.Classes.Mobiles.Mob;// Created by Hanto on 13/08/2015.

import DTOs.DTOsDebuffeable;
import DTOs.DTOsEspacial;
import DTOs.DTOsOrientable;
import DTOs.DTOsVulnerable;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.Misc.Spell.AuraI;
import Model.Mobiles.Propiedades.DeBuffeableNotificadorI;
import Model.Mobiles.Steerables.SteerableAgentAutonomo;

public abstract class MobNotificador extends SteerableAgentAutonomo implements MobI,
        DeBuffeableNotificadorI
{
    private DTOsEspacial.Posicion posicionDTO;
    private DTOsOrientable.Orientacion orientacionDTO;
    private DTOsVulnerable.ModificarHPs modificarHPsDTO;

    private DTOsDebuffeable.AñadirAura añadirAuraDTO;
    private DTOsDebuffeable.EliminarAura eliminarAuraDTO;
    private DTOsDebuffeable.ModificarStacks modificarStacksDTO;

    public MobNotificador()
    {
        posicionDTO = new DTOsEspacial.Posicion(this);
        orientacionDTO = new DTOsOrientable.Orientacion(this);
        modificarHPsDTO = new DTOsVulnerable.ModificarHPs(this);

        añadirAuraDTO = new DTOsDebuffeable.AñadirAura(this);
        eliminarAuraDTO = new DTOsDebuffeable.EliminarAura(this);
        modificarStacksDTO = new DTOsDebuffeable.ModificarStacks(this);
    }

    // NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            notificarActualizacion("posicionMob", null, posicionDTO);
        }
    }

    public void notificarSetOrientacion()
    {
        if (Math.abs(orientacionDTO.orientacion - getOrientacion()) > 0.1f)
        {
            orientacionDTO.orientacion = getOrientacion();
            notificarActualizacion("orientacionMob", null, orientacionDTO);
        }
    }

    public void notificarAddModificarHPs(float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("modificarHPsMob", null, modificarHPsDTO);
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

    public void notificarIncrementarStack(AuraI aura)
    {
        modificarStacksDTO.aura = aura;
        notificarActualizacion("setModificarStacks", null, modificarStacksDTO);
    }
}
