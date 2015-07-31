package DTO;// Created by Hanto on 25/07/2014.

import Model.Settings;

public class DTOsMapView
{
    public static class MapTilesAdyacentes
    {
        public boolean[][] mapaAdyacencias;
        public MapTilesAdyacentes() {}
        public MapTilesAdyacentes(boolean[][] mapaAdyacencias)
        {   this.mapaAdyacencias = mapaAdyacencias; }
    }

    public static class Mapa
    {
        public static class Celda
        {   public short[] celda= new short[Settings.MAPA_Max_Capas_Terreno];
            public Celda() { }
        }

        public int esquinaInfIzdaX;
        public int esquinaInfIzdaY;
        public Celda[][] mapa;
        public Mapa() {}
        public Mapa(int esquinaInfIzdaX, int esquinaInfIzdaY, int tamañoX, int tamañoY)
        {
            this.esquinaInfIzdaX = esquinaInfIzdaX;
            this.esquinaInfIzdaY = esquinaInfIzdaY;
            mapa = new Celda[tamañoX][tamañoY];
            for (Celda[] fila: mapa)
            {   for (int i=0; i<fila.length; i++)
                {   fila[i] = new Celda(); }
            }
        }
    }
}
