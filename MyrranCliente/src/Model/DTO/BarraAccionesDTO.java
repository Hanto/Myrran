package Model.DTO;// Created by Hanto on 07/05/2014.

import Model.Classes.UI.BarraAcciones;

public class BarraAccionesDTO
{
    public static class ActualizarCasillaAccion {}
    public static class ActualizarCasillaKey {}

    public static class EliminarFilaDTO
    {
        public int numFilas;
        public EliminarFilaDTO(int numFilas)
        {   this.numFilas = numFilas;}
    }

    public static class AñadirFilaDTO
    {
        public int numFilas;
        public AñadirFilaDTO(int numFilas)
        {   this.numFilas = numFilas;}
    }

    public static class EliminarColumnaDTO
    {
        public int numColumnas;
        public EliminarColumnaDTO(int numColumnas)
        {   this.numColumnas = numColumnas; }
    }

    public static class AñadirColumnaDTO
    {
        public int numColumnas;
        public AñadirColumnaDTO(int numColumnas)
        {   this.numColumnas = numColumnas; }
    }

    public static class AñadirBarraAcciones
    {
        public BarraAcciones barraAcciones;
        public AñadirBarraAcciones(BarraAcciones barraAcciones)
        {   this.barraAcciones = barraAcciones; }
    }
}
