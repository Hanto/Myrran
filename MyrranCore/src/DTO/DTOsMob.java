package DTO;// Created by Hanto on 11/08/2015.

import Interfaces.EntidadesTipos.MobI;

public class DTOsMob
{
    public static class PosicionMob
    {
        public MobI mob;
        //public int posX;
        //public int posY;
        public PosicionMob(MobI mob)
        {   this.mob = mob; }
    }

    public static class OrientacionMob
    {
        public MobI mob;
        public OrientacionMob (MobI mob)
        {   this.mob = mob; }
    }
}
