package DTO;// Created by Hanto on 24/07/2014.

import Interfaces.EntidadesTipos.MobPC;

public class DTOsMundo
{
    //Mundo (MundoView)
    public static class NuevoPlayer
    {
        public int connectionID;
        public NuevoPlayer() {}
        public NuevoPlayer(MobPC pc)
        {   connectionID = pc.getConnectionID(); }
    }
}
