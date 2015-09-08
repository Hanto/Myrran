package DTOs;// Created by Hanto on 06/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Animable;

public class DTOsAnimable
{
    public static class NumAnimacion
    {
        public Animable animable;
        public short numAnimacion;
        public NumAnimacion(Animable animable)
        {   this.animable = animable; }
        public void set(int numAnimacion)
        {   this.numAnimacion =  (short)numAnimacion; }
    }
}
