package Model.GameState;// Created by Hanto on 06/05/2014.

import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.UI.AccionI;
import Interfaces.UI.CasillaI;
import Interfaces.UI.BarraAccionesI;
import Model.Classes.Input.InputManager;
import Model.Classes.UI.BarraTerrenos;
import Model.Classes.UI.ConjuntoBarraAcciones;


public class UI
{
    protected InputManager inputManager;
    protected ConjuntoBarraAcciones conjuntoBarraAcciones;
    protected BarraTerrenos barraTerrenos;

    public InputManager getInputManager()                   { return inputManager; }
    public ConjuntoBarraAcciones getConjuntoBarraAcciones() { return conjuntoBarraAcciones; }
    public BarraTerrenos getBarraTerrenos()                 { return barraTerrenos; }


    public UI (PlayerI player)
    {
        inputManager = new InputManager(player);
        conjuntoBarraAcciones = new ConjuntoBarraAcciones(player, inputManager);
        barraTerrenos  = new BarraTerrenos(player);
    }


    public void añadirBarraAcciones(int filas, int columnas)
    {   conjuntoBarraAcciones.añadirBarraAcciones(filas, columnas); }
    public void eliminarBarraAcciones(BarraAccionesI barra)
    {   conjuntoBarraAcciones.eliminarBarraAccion(barra); }

    public void barraTerrenosMoverTerreno(int posOrigen, int posDestino)
    {   barraTerrenos.moverTerreno(posOrigen, posDestino); }

    public void moverAccion (CasillaI casillaOrigen, CasillaI casillaDestino)
    {
        AccionI accionOrigen = casillaOrigen.getAccion();
        AccionI accionDestino = casillaDestino.getAccion();

        //El origen solo se machaca si los dos son movibles;
        if (casillaOrigen.getMovible() && casillaDestino.getMovible())
        {
            if (accionDestino == null) casillaOrigen.eliminarAccion();
            else casillaOrigen.setAccion(accionDestino);
        }

        //el destino solo se machaca si el destino es movible:
        if (casillaDestino.getMovible())
        {
            if (accionOrigen == null) casillaDestino.eliminarAccion();
            else casillaDestino.setAccion(accionOrigen);
        }
    }

    public void rebindearCasilla (CasillaI casilla, int keycode)
    {   conjuntoBarraAcciones.eliminarKeycode(keycode);
        casilla.setKeycode(keycode);
    }

    public void crearCasilla (int barraID, int posX, int posY, String skillID, int keycode)
    {
        CasillaI casilla = conjuntoBarraAcciones.getBarraAcciones(barraID).getCasilla(posX, posY);
        rebindearCasilla(casilla, keycode);
        casilla.setAccion(skillID);
    }
}
