package Controller;// Created by Hanto on 09/04/2014.

import Interfaces.AI.ColisionMurosI;
import Interfaces.AI.ColisionProyectilesI;
import Interfaces.AI.SistemaAggroI;
import Interfaces.EstructurasDatos.QuadTreeI;
import Interfaces.Geo.MapaI;
import Interfaces.Network.MainLoopI;
import Model.AI.Colisiones.ColisionProyectilesQTree;
import Model.AI.Colisiones.CollisionMurosTiles;
import Model.AI.SistemaAggro;
import Model.Classes.Geo.Mapa;
import Model.EstructurasDatos.QuadTree;
import Model.GameState.Mundo;
import Model.Settings;
import View.Gamestate.MundoView;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Updater implements MainLoopI, Runnable
{
    private World world;
    private MapaI mapa;
    private Mundo mundo;
    private QuadTreeI quadTree;
    private SistemaAggroI aggro;
    private ColisionMurosI colisionMuros;
    private ColisionProyectilesI colisionProyectiles;
    private MundoView mundoView;
    private Controlador controlador;
    private Servidor servidor;

    private double timeStep = 0;
    private double newTime;
    private double currentTime;
    private double deltaTime;

    private boolean mundoActualizado = false;

    protected final List<Runnable> runnables = new ArrayList<>();
    protected final List<Runnable> executedRunnables = new ArrayList<>();

    //private int contador = 0;
    //private double total = 0;
    //private float media;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Updater()
    {
        //MODEL:
        //--------------------------------------------------------------------------------------------------------------

        world = new World(new Vector2(0,0), false);
        mapa = new Mapa();
        quadTree = new QuadTree(Settings.MAPA_Max_TilesX * Settings.TILESIZE, Settings.MAPA_Max_TilesY * Settings.TILESIZE);
        aggro = new SistemaAggro();
        colisionMuros = new CollisionMurosTiles(mapa);
        colisionProyectiles = new ColisionProyectilesQTree(quadTree, aggro);
        mundo = new Mundo(world, mapa, quadTree, aggro, colisionMuros, colisionProyectiles);

        //VISTA:
        //--------------------------------------------------------------------------------------------------------------

        mundoView = new MundoView(mundo);

        //CONTROLADOR:
        //--------------------------------------------------------------------------------------------------------------

        controlador = new Controlador(mundo, mundoView);

        //MULTIPLAYER:
        //--------------------------------------------------------------------------------------------------------------

        servidor = new Servidor(this, controlador);

        currentTime = TimeUtils.nanoTime() / 1000000000.0;

        //MONOHILO:
        //new Thread(this).start();
    }

    public void run()
    {
        //while (true)
        {
            synchronized (mundo)
            {
                newTime = TimeUtils.nanoTime() / 1000000000.0;
                deltaTime = (newTime - currentTime);
                currentTime = newTime;

                //total += deltaTime;
                timeStep += deltaTime;

                while (timeStep >= Settings.FIXED_TimeStep)
                {
                    //contador++;
                    //media = (float) total / (float) contador;
                    //logger.trace("TimeStep Medio: {}",media);

                    timeStep -= Settings.FIXED_TimeStep;

                    //INPUT:
                    executeRunnables();

                    //MODEL:
                    mundo.actualizarUnidades(Settings.FIXED_TimeStep, mundo);
                    mundo.actualizarFisica(Settings.FIXED_TimeStep, mundo);
                    mundo.checkColisiones();

                    mundoActualizado = true;
                }
                if (mundoActualizado)
                {
                    //VISTA:
                    mundoView.radar();
                    mundoView.enviarDTOs(servidor);

                    mundoActualizado = false;
                }
            }
            try { Thread.sleep((long)(1)); }
            catch (InterruptedException e) { logger.error("ERROR: Updateando la red: ", e); return; }
        }
    }

    @Override public void postRunnable(Runnable runnable)
    {
        synchronized (runnables)
        {   runnables.add(runnable); }
    }

    private boolean executeRunnables()
    {
        synchronized (runnables)
        {   executedRunnables.addAll(runnables);
            runnables.clear();
        }
        if (executedRunnables.size() == 0) return false;

        for (Runnable executedRunnable : executedRunnables)
        {   executedRunnable.run(); }

        executedRunnables.clear();
        return true;
    }
}
