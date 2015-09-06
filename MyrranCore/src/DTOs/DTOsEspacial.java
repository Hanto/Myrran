package DTOs;

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Espacial;

public class DTOsEspacial
{
    public static class PosicionEspacial
    {
        public Espacial espacial;
        public int posX;
        public int posY;
        public PosicionEspacial() {}
        public PosicionEspacial (Espacial espacial)
        {   this.espacial = espacial;}
    }
}
