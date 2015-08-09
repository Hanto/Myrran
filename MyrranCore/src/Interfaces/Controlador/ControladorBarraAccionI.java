package Interfaces.Controlador;// Created by Hanto on 14/05/2014.


import Interfaces.UI.CasillaI;
import Interfaces.UI.BarraAccionesI;

public interface ControladorBarraAccionI
{
    public void añadirBarraAcciones(int filas, int columnas);
    public void eliminarBarraAcciones(BarraAccionesI barra);

    public void barraAñadirColumna(BarraAccionesI barra, int numColumnas);
    public void barraAñadirFila (BarraAccionesI barra, int numFilas);
    public void barraEliminarColumna (BarraAccionesI barra, int numColumnas);
    public void barraEliminarFila (BarraAccionesI barra, int numFilas);

    public void barraAccionMoverAccion(CasillaI casillaOrigen, CasillaI casillaDestino);
    public void barraAccionRebindear(CasillaI casilla, int keycode);
}
