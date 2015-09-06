package DTOs;// Created by Hanto on 03/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Caster;

public class DTOsCaster
{
    public static class CastingTimePercent
    {
        public float castingTimePercent;
        public CastingTimePercent() {}
        public CastingTimePercent(Caster Caster)
        {   this.castingTimePercent = Caster.getActualCastingTime() == 0 && Caster.getTotalCastingTime() == 0 ? 100 :
                Caster.getActualCastingTime() / Caster.getTotalCastingTime(); }
    }

    public static class Castear
    {
        public String spellID;
        public Object parametrosSpell;
        public int screenX;
        public int screenY;
        public Castear() {}
        public Castear(String spellID, Object parametrosSpell, int screenX, int screenY)
        {   this.spellID = spellID; this.parametrosSpell = parametrosSpell; this.screenX = screenX; this.screenY = screenY; }
    }
}
