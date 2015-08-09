package DTO;// Created by Hanto on 09/08/2015.

public class DTOsInputManager
{
    public static class MostrarBarraTerrenos {}

    public static class OcultarBarraTerrenos {}

    public static class AplicarZoom
    {
        public int nivelZoom;
        public AplicarZoom(int nivelZoom)
        {   this.nivelZoom = nivelZoom; }
    }

}
