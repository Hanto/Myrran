package Controller;// Created by Hanto on 08/04/2014.

import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.Player;
import Model.GameState.Mundo;
import Model.GameState.UI;
import View.GameState.Vista;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;
import zMain.MyrranClient;

import static Model.Settings.FIXED_TimeStep;


public class Updater implements Screen
{
    private MyrranClient myrranCliente;
    private World world;
    private Player player;
    private Mapa mapa;
    private Mundo mundo;
    private UI ui;
    private Vista vista;
    private Controlador controlador;
    private Cliente cliente;

    private double timeStep = 0f;
    private double newTime;
    private double currentTime;
    private double deltaTime;

    //private int contador = 0;
    //private float total = 0;
    //private float media;
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    public Updater(MyrranClient myrranCliente)
    {
        this.myrranCliente = myrranCliente;

        //MODEL:
        world = new World(new Vector2(0, 0), false);
        player = new Player(world);
        mapa = new Mapa(player);
        mundo = new Mundo(world, player, mapa);
        ui = new UI(player);
        //VISTA:
        vista = new Vista(ui, mundo);
        //CONTROLADOR:
        controlador = new Controlador(mundo, ui, vista);
        //MULTIPLAYER:
        cliente = new Cliente(controlador);

        currentTime = TimeUtils.nanoTime() / 1000000000.0;
    }

    @Override public void render(float delta)
    {
        newTime = TimeUtils.nanoTime() / 1000000000.0;
        deltaTime = (newTime - currentTime);
        currentTime = newTime;

        //total += deltaTime;
        timeStep += deltaTime;

        while (timeStep >= FIXED_TimeStep)
        {
            //contador++;
            //media = total/contador;
            //logger.trace("TimeStep Medio: {}",media);

            timeStep -= FIXED_TimeStep;

            player.getInput().coordenadasScreenAMundo(vista.getMundoView().getCamara());
            mundo.actualizarUnidades(FIXED_TimeStep, mundo);
            mundo.actualizarFisica(FIXED_TimeStep);
            mundo.enviarDatosAServidor(cliente);
        }
        mundo.interpolarPosicion((float) timeStep / FIXED_TimeStep);
        vista.render(delta);
    }

    @Override public void show()
    {   logger.trace("SHOW (Inicializando Screen):"); }

    @Override public void pause()
    {   logger.trace("PAUSE (Pausando pantalla):"); }

    @Override public void resume()
    {   logger.trace("RESUME (Pantalla reanudada):"); }

    @Override public void resize(int anchura, int altura)
    {
        logger.trace("RESIZE (Redimensionando Screen) a: {} x {}", anchura, altura);
        vista.resize(anchura, altura);
    }

    @Override public void hide()
    {
        logger.trace("HIDE (Cerrando pantalla):");
        dispose();
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE (Liberando memoria):");
        vista.dispose();
    }
}
