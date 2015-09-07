package DTOs;

import Interfaces.EntidadesPropiedades.Espaciales.Espacial;

public class DTOsEspacial
{
    public static class Posicion
    {
        public Espacial espacial;
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion(Espacial espacial)
        {   this.espacial = espacial;}
        public void set(float x, float y)
        {   this.posX = (int)x; this.posY = (int)y; }
    }
}
