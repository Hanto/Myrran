package DTOs;// Created by Hanto on 03/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;

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
}
