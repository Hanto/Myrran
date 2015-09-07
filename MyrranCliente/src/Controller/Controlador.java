package Controller;// Created by Hanto on 08/04/2014.

import DTOs.DTOsNet;
import Interfaces.UI.AccionI;
import Model.Classes.Acciones.AccionFactory;
import Model.GameState.Mundo;
import Model.GameState.UI;
import View.GameState.Vista;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

//CLIENTE:
public class Controlador
{
    //MODEL:
    protected Mundo mundo;
    protected UI ui;
    //VISTA:
    protected Vista vista;
    protected InputMultiplexer inputMultiplexer;

    public Mundo getMundo()                     { return mundo; }
    public UI getUI()                           { return ui; }

    public Controlador (Mundo mundo, UI ui, Vista vista)
    {
        this.mundo = mundo;
        this.ui = ui;
        this.vista = vista;

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(vista.getUiView().getStage());
        inputMultiplexer.addProcessor(vista.getMundoView());
        inputMultiplexer.addProcessor(ui.getInputManager());
        Gdx.input.setInputProcessor(inputMultiplexer);

        // MIERDA PROVISIONAL:
        //--------------------------------------------------------------------------------------------------------------

        ui.añadirBarraAcciones(3, 9);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(1,0).setKeycode(9);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(2,0).setKeycode(10);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(3,0).setKeycode(11);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(4,0).setKeycode(12);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(5,0).setKeycode(13);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(6,0).setKeycode(14);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(7,0).setKeycode(15);
        ui.getConjuntoBarraAcciones().getBarraAcciones(0).getCasilla(8,0).setKeycode(16);

        ui.getInputManager().añadirAccion(AccionFactory.accionComando.IRNORTE.nuevo());
        ui.getInputManager().añadirAccion(AccionFactory.accionComando.IRSUR.nuevo());
        ui.getInputManager().añadirAccion(AccionFactory.accionComando.IRESTE.nuevo());
        ui.getInputManager().añadirAccion(AccionFactory.accionComando.IROESTE.nuevo());
        ui.getInputManager().añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("Terraformar"));
        ui.getInputManager().añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("Heal"));
        ui.getInputManager().añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("Bolt"));
        ui.getInputManager().añadirAccion(AccionFactory.accionSpell.SELECCIONARSPELL.nuevo("PoisonBolt"));

        ui.crearCasilla(0, 0, 0, "Terraformar", 8);
        ui.crearCasilla(0, 1, 0, "Heal", 9);
        ui.crearCasilla(0, 2, 0, "Bolt", 10);
        ui.crearCasilla(0, 3, 0, "PoisonBolt", 11);
        ui.crearCasilla(0, 1, 1, "IrNorte", 51);
        ui.crearCasilla(0, 1, 2, "IrSur", 47);
        ui.crearCasilla(0, 2, 2, "IrEste", 32);
        ui.crearCasilla(0, 0, 2, "IrOeste", 29);

        //moverPPC(mundo.getPlayer().getID(), 21000, 20000);
    }

    //UI:
    public void añadirAccion(AccionI accion)                                                { ui.getInputManager().añadirAccion(accion); }

    //Entidades:
    public void actualizarMapa(DTOsNet.Mapa mapaServidor)                           { mundo.actualizarMapa(mapaServidor); }
    public void actualizarMapTilesAdyacentes(DTOsNet.MapTilesAdyacentes mapTiles)   { mundo.mapTilesCargados = mapTiles.mapaAdyacencias; }
}
