package Model.GameState;// Created by Hanto on 06/05/2014.

import Controller.Controlador;
import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;
import Interfaces.UI.BarraAcciones.BarraAccionesI;
import Model.Classes.Acciones.AccionFactory;
import Model.Classes.Input.InputManager;
import Model.Classes.Input.PlayerEstado;
import Model.Classes.Input.PlayerIO;
import Model.Classes.Mobiles.Player;
import Model.Classes.UI.BarraTerrenos;
import Model.Classes.UI.ConjuntoBarraAcciones;


public class UI
{
    protected InputManager inputManager;
    protected PlayerIO playerInput = new PlayerIO();
    protected PlayerIO playerOutput = new PlayerIO();
    protected PlayerEstado playerEstado = new PlayerEstado(playerInput, playerOutput);

    public ConjuntoBarraAcciones conjuntoBarraAcciones;
    public BarraTerrenos barraTerrenos;

    public InputManager getInputManager()       { return inputManager; }


    public UI (Player player, Controlador controlador)
    {
        inputManager = new InputManager(player, playerEstado, controlador);
        conjuntoBarraAcciones = new ConjuntoBarraAcciones(player, inputManager);
        barraTerrenos  = new BarraTerrenos(player);



        inputManager.añadirAccion(AccionFactory.accionComando.IRNORTE.nuevo());
        inputManager.añadirAccion(AccionFactory.accionComando.IRSUR.nuevo());
        inputManager.añadirAccion(AccionFactory.accionComando.IRESTE.nuevo());
        inputManager.añadirAccion(AccionFactory.accionComando.IROESTE.nuevo());
        inputManager.añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("Terraformar"));
        inputManager.añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("Heal"));
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
