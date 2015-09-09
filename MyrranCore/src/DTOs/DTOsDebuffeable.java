package DTOs;// Created by Hanto on 08/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.Spell.AuraI;

public class DTOsDebuffeable
{
    public static class AñadirAura
    {
        public Debuffeable debuffeable;
        public AuraI aura;
        public AñadirAura(Debuffeable debuffeable)
        {   this.debuffeable = debuffeable; }
        public AñadirAura() {}
    }

    public static class EliminarAura
    {
        public Debuffeable debuffeable;
        public AuraI aura;
        public EliminarAura(Debuffeable debuffeable)
        {   this.debuffeable = debuffeable; }
        public EliminarAura() {}
    }

    public static class ModificarStacks
    {
        public Debuffeable debuffeable;
        public AuraI aura;
        public ModificarStacks(Debuffeable debuffeable)
        {   this.debuffeable = debuffeable; }
        public ModificarStacks() {}
    }
}
