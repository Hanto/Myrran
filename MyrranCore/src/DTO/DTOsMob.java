package DTO;// Created by Hanto on 11/08/2015.

import InterfacesEntidades.EntidadesTipos.MobI;

public class DTOsMob
{
    public static class PosicionMob
    {
        public MobI mob;
        public int posX;
        public int posY;
        public PosicionMob(MobI mob)
        {   this.mob = mob; }
    }

    public static class OrientacionMob
    {
        public MobI mob;
        public float orientacion;
        public OrientacionMob (MobI mob)
        {   this.mob = mob; }
    }

    public static class ModificarHPsMob
    {
        public MobI mob;
        public float HPs;
        public ModificarHPsMob(MobI mob)
        {   this.mob = mob; }
        public ModificarHPsMob (MobI mob, float HPs)
        {   this.mob = mob; this.HPs = HPs; }
    }
}
