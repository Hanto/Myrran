package Controller;// Created by Hanto on 08/04/2014.

import DTO.DTOsMapView;
import DTO.DTOsPC.LogIn;
import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;
import Interfaces.UI.BarraAcciones.BarraAccionesI;
import Interfaces.UI.ControladorUI;
import Model.GameState.Mundo;
import Model.GameState.UI;
import View.Vista;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;

//CLIENTE:
public class Controlador implements ControladorUI
{
    protected Cliente cliente;
    protected ClienteInputs clienteInputs;

    protected Mundo mundo;
    protected UI ui;
    protected Vista vista;

    //Input:
    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();
    public Cliente getCliente()                 { return cliente; }
    public UI getUI()                           { return ui; }

    public Controlador (Mundo mundo)
    {
        this.cliente = new Cliente(this);
        this.clienteInputs = new ClienteInputs(mundo, this);
        this.mundo = mundo;
        añadirPlayer(cliente.getID());

        ui = new UI(mundo.getPlayer(), this);
        vista = new Vista(this, ui, mundo);

        inputMultiplexer.addProcessor(vista.getUiView());
        inputMultiplexer.addProcessor(vista.getMundoView());
        inputMultiplexer.addProcessor(ui.getInputManager());
        Gdx.input.setInputProcessor(inputMultiplexer);

        ui.añadirBarraAcciones(3, 9);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(1,0).setKeycode(9);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(2,0).setKeycode(10);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(3,0).setKeycode(11);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(4,0).setKeycode(12);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(5,0).setKeycode(13);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(6,0).setKeycode(14);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(7,0).setKeycode(15);
        ui.conjuntoBarraAcciones.getBarraAcciones(0).getCasilla(8,0).setKeycode(16);

        ui.crearCasilla(0, 0, 0, "Terraformar", 8);
        ui.crearCasilla(0, 1, 0, "Heal", 9);
        ui.crearCasilla(0, 1, 1, "IrNorte", 51);
        ui.crearCasilla(0, 1, 2, "IrSur", 47);
        ui.crearCasilla(0, 2, 2, "IrEste", 32);
        ui.crearCasilla(0, 0, 2, "IrOeste", 29);

        enviarAServidor(new LogIn());
        //moverPPC(mundo.getPlayer().getConnectionID(), 21000, 20000);
    }

    //LibGDX:
    public void render (float delta)                                                    { vista.render(delta); }
    public void dispose()                                                               { vista.dispose(); }
    public void resize(int anchura, int altura)                                         { vista.resize(anchura, altura);}
    public OrthographicCamera getCamara()                                               { return vista.getMundoView().getCamara(); }

    //Kryo:
    public void enviarAServidor(Object obj)                                             { cliente.enviarAServidor(obj); }

    //UI:
    public void añadirAccion(AccionI accion)                                            { ui.getInputManager().añadirAccion(accion); }

    //Entidades:
    public void añadirPlayer(int connectionID)                                          { mundo.getPlayer().setConnectionID(connectionID); }

    public void actualizarMapa(DTOsMapView.Mapa mapaServidor)                                  { mundo.actualizarMapa(mapaServidor); }
    public void actualizarMapTilesAdyacentes(DTOsMapView.MapTilesAdyacentes mapTiles)          { mundo.mapTilesCargados = mapTiles.mapaAdyacencias; }
    public void aplicarZoom(int incrementoZoom)                                         { vista.aplicarZoom(incrementoZoom); }

    //BarraTerrenos:
    @Override public void mostrarBarraTerrenos()                                        { vista.getUiView().mostrarBarraTerreno(); }
    @Override public void ocultarBarraTerrenos()                                        { vista.getUiView().ocultarBarraTerreno(); }
    @Override public void barraTerrenosMoverTerreno(int posOrigen, int posDestino)      { ui.barraTerrenosMoverTerreno(posOrigen, posDestino); }

    //BarrasAccion:
    @Override public void añadirBarraAcciones(int filas, int columnas)                  { ui.añadirBarraAcciones(filas, columnas); }
    @Override public void eliminarBarraAcciones(BarraAccionesI barraAcciones)           { ui.eliminarBarraAcciones(barraAcciones); }
    @Override public void barraAñadirFila (BarraAccionesI barra, int numFilas)          { barra.añadirFila(numFilas); }
    @Override public void barraAñadirColumna(BarraAccionesI barra, int numColumnas)     { barra.añadirColumna(numColumnas); }
    @Override public void barraEliminarFila (BarraAccionesI barra, int numFilas)        { barra.eliminarFila(numFilas); }
    @Override public void barraEliminarColumna (BarraAccionesI barra, int numColumnas)  { barra.eliminarColumna(numColumnas); }
    @Override public void barraAccionMoverAccion(CasillaI origen, CasillaI destino)     { ui.moverAccion(origen, destino); }
    @Override public void barraAccionRebindear(CasillaI casilla, int keycode)           { ui.rebindearCasilla(casilla, keycode); }
}
