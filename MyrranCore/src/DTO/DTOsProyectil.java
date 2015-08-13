package DTO;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesTipos.ProyectilI;

public class DTOsProyectil
{
    public static class PosicionProyectil
    {
        public ProyectilI proyectil;
        public int posX;
        public int posY;
        public PosicionProyectil (ProyectilI proyectil)
        {   this.proyectil = proyectil; }
    }

    public static class DisposeProyectil
    {
        public ProyectilI proyectil;
        public DisposeProyectil (ProyectilI proyectil) { this.proyectil = proyectil; }
    }
}
