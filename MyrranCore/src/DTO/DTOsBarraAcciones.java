package DTO;// Created by Hanto on 07/05/2014.

import Interfaces.UI.BarraAcciones.BarraAccionesI;

public class DTOsBarraAcciones
{
    public static class ActualizarCasillaAccion {}
    public static class ActualizarCasillaKey {}

    public static class EliminarFila
    {
        public int numFilas;
        public EliminarFila(int numFilas)
        {   this.numFilas = numFilas;}
    }

    public static class AñadirFila
    {
        public int numFilas;
        public AñadirFila(int numFilas)
        {   this.numFilas = numFilas;}
    }

    public static class EliminarColumna
    {
        public int numColumnas;
        public EliminarColumna(int numColumnas)
        {   this.numColumnas = numColumnas; }
    }

    public static class AñadirColumna
    {
        public int numColumnas;
        public AñadirColumna(int numColumnas)
        {   this.numColumnas = numColumnas; }
    }

    public static class AñadirBarraAcciones
    {
        public BarraAccionesI barraAcciones;
        public AñadirBarraAcciones(BarraAccionesI barraAcciones)
        {   this.barraAcciones = barraAcciones; }
    }
}
