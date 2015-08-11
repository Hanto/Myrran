package View.Classes.UI;// Created by Hanto on 10/08/2015.

import Interfaces.Controlador.ControladorUI;
import Interfaces.Model.AbstractModel;
import Interfaces.UI.BarraAccionesI;
import Interfaces.UI.CasillaI;
import Model.GameState.UI;

public class UIViewController extends AbstractModel implements ControladorUI
{
    //Si necesitamos realizar modificaciones sobre la vista, lanzamos eventos que capturara el UIVIEW.
    //Pero enroutamos el metodo aqui, de esta forma todas las notificaciones se lanzan desde un unico sitio.
    //------------------------------------------------------------------------------------------------------------------

    protected UI ui;

    public UIViewController(UI ui)
    {   this.ui = ui;}

    //CONTROLADOR UI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirBarraAcciones(int filas, int columnas)
    {   ui.añadirBarraAcciones(filas, columnas); }

    @Override public void eliminarBarraAcciones(BarraAccionesI barra)
    {   ui.eliminarBarraAcciones(barra); }

    @Override public void barraAñadirColumna(BarraAccionesI barra, int numColumnas)
    {   barra.añadirColumna(numColumnas); }

    @Override public void barraAñadirFila(BarraAccionesI barra, int numFilas)
    {   barra.añadirFila(numFilas); }

    @Override public void barraEliminarColumna(BarraAccionesI barra, int numColumnas)
    {   barra.eliminarColumna(numColumnas); }

    @Override public void barraEliminarFila(BarraAccionesI barra, int numFilas)
    {   barra.eliminarFila(numFilas); }

    @Override public void barraAccionMoverAccion(CasillaI casillaOrigen, CasillaI casillaDestino)
    {   ui.moverAccion(casillaOrigen, casillaDestino); }

    @Override public void barraAccionRebindear(CasillaI casilla, int keycode)
    {   ui.rebindearCasilla(casilla, keycode); }

    @Override public void barraTerrenosMoverTerreno(int posOrigen, int posDestino)
    {   ui.barraTerrenosMoverTerreno(posOrigen, posDestino); }
}
