package DTOs;// Created by Hanto on 06/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Orientable;

public class DTOsOrientable
{
    public static class Orientacion
    {
        public Orientable orientable;
        public float orientacion;
        public Orientacion (Orientable orientable)
        {   this.orientable = orientable; }
    }
}
