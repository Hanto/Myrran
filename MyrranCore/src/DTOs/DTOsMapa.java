package DTOs;// Created by Hanto on 24/07/2014.

public class DTOsMapa
{
    public static class SetTerreno
    {
        public int tileX;
        public int tileY;
        public short numCapa;
        public short iDTerreno;
        public SetTerreno() {}
        public SetTerreno(int tileX, int tileY, int numCapa, short iDTerreno)
        {   this.tileX = tileX; this.tileY = tileY;
            this.numCapa = (short)numCapa; this.iDTerreno = iDTerreno; }
    }
}
