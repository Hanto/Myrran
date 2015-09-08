package DTOs;// Created by Hanto on 08/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.DebuffeableI;
import Interfaces.Misc.Spell.AuraI;

public class DTOsDebuffeable
{
    public static class AñadirAura
    {
        public DebuffeableI debuffeable;
        public AuraI aura;
        public AñadirAura(DebuffeableI debuffeable)
        {   this.debuffeable = debuffeable; }
        public AñadirAura() {}
    }

    public static class EliminarAura
    {
        public DebuffeableI debuffeable;
        public AuraI aura;
        public EliminarAura(DebuffeableI debuffeable)
        {   this.debuffeable = debuffeable; }
        public EliminarAura() {}
    }

    public static class ModificarStacks
    {
        public DebuffeableI debuffeable;
        public AuraI aura;
        public ModificarStacks(DebuffeableI debuffeable)
        {   this.debuffeable = debuffeable; }
        public ModificarStacks() {}
    }
}
