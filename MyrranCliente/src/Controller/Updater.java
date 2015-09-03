package Controller;// Created by Hanto on 08/04/2014.

import Model.Classes.Geo.Mapa;
import Model.Classes.Input.InputManager;
import Model.Classes.Mobiles.Player.Player;
import Model.Classes.Mobiles.Player.PlayerFactory;
import Model.Classes.UI.BarraTerrenos;
import Model.Classes.UI.ConjuntoBarraAcciones;
import Model.GameState.Mundo;
import Model.GameState.UI;
import View.Classes.Geo.MapaView;
import View.Classes.UI.BarraAccionesView.ConjuntoBarraAccionesView;
import View.Classes.UI.BarraTerrenosView.BarraTerrenosView;
import View.Classes.UI.UIViewController;
import View.GameState.MundoView;
import View.GameState.UIView;
import View.GameState.Vista;
import box2dLight.RayHandler;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;
import zMain.MyrranClient;

import static Model.Settings.FIXED_TimeStep;
import static Model.Settings.PIXEL_METROS;


public class Updater implements Screen
{
    private MyrranClient myrranCliente;
    //MODEL:
    private World world;
    private Player player;
    private Mapa mapa;
    private Mundo mundo;
    private InputManager inputManager;
    private ConjuntoBarraAcciones conjuntoBarraAcciones;
    private BarraTerrenos barraTerrenos;
    private UI ui;

    //VIEW:
    private Stage uiViewStage;
    private UIViewController uiViewController;
    private ConjuntoBarraAccionesView conjuntoBarraAccionesView;
    private BarraTerrenosView barraTerrenosView;
    private UIView uiView;

    private RayHandler rayhandler;
    private OrthographicCamera camara;
    private OrthographicCamera boxCamara;
    private MundoView mundoView;
    private MapaView mapaView;

    private Vista vista;

    //CONTROLER:
    private Controlador controlador;

    //MULTIPLAYER:
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

        // MODEL:
        //--------------------------------------------------------------------------------------------------------------

        world = new World(new Vector2(0, 0), false);
        player = PlayerFactory.GOLEM.nuevo(world);
        mapa = new Mapa(player);
        mundo = new Mundo(world, player, mapa);
        inputManager = new InputManager(player);
        conjuntoBarraAcciones = new ConjuntoBarraAcciones(player, inputManager);
        barraTerrenos = new BarraTerrenos(player);
        ui = new UI(inputManager, conjuntoBarraAcciones, barraTerrenos);

        // UIVIEW:
        //--------------------------------------------------------------------------------------------------------------

        uiViewStage = new Stage();
        uiViewController = new UIViewController(ui);
        conjuntoBarraAccionesView = new ConjuntoBarraAccionesView(uiViewController, conjuntoBarraAcciones, uiViewStage);
        barraTerrenosView = new BarraTerrenosView(uiViewController, barraTerrenos, uiViewStage);
        uiView = new UIView(uiViewController, inputManager, conjuntoBarraAccionesView, barraTerrenosView, uiViewStage);

        // MUNDOVIEW:
        //--------------------------------------------------------------------------------------------------------------

        rayhandler = new RayHandler(world);
        camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        boxCamara = new OrthographicCamera(Gdx.graphics.getWidth() * PIXEL_METROS, Gdx.graphics.getHeight() * PIXEL_METROS);
        mapaView = new MapaView(mapa, camara);
        mundoView = new MundoView(player, mundo, mapaView, camara, boxCamara, rayhandler);

        // VISTA:
        //--------------------------------------------------------------------------------------------------------------

        vista = new Vista(mundoView, uiView);

        // CONTROLADOR:
        //--------------------------------------------------------------------------------------------------------------

        controlador = new Controlador(mundo, ui, vista);

        // MULTIPLAYER:
        //--------------------------------------------------------------------------------------------------------------

        cliente = new Cliente(controlador);

        // Incializacion:
        //--------------------------------------------------------------------------------------------------------------

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

            player.getInput().coordenadasScreenAMundo(camara);
            mundo.actualizarUnidades(FIXED_TimeStep, mundo);
            mundo.actualizarFisica(FIXED_TimeStep, mundo);
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
        mundo.dispose();
        vista.dispose();
    }
}
