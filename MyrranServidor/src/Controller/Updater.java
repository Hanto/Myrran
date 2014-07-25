package Controller;// Created by Hanto on 09/04/2014.

import Data.Settings;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Updater implements Runnable
{
    private Controlador controlador;
    private Mundo mundo;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    protected final List<Runnable> runnables = new ArrayList<>();
    protected final List<Runnable> executedRunnables = new ArrayList<>();

    private double timeStep = 0;

    private double newTime;
    private double currentTime;
    private double deltaTime;

    //private int contador = 0;
    //private double total = 0;
    //private float media;

    public void postRunnable(Runnable runnable)
    {
        synchronized (runnables)
        {   runnables.add(runnable); }
    }

    public Updater(Controlador controlador, Mundo mundo)
    {
        this.controlador = controlador;
        this.mundo = mundo;

        currentTime = TimeUtils.nanoTime() / 1000000000.0;

        new Thread(this).start();
    }

    @Override public void run()
    {
        while (true)
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

                    //executeRunnables();
                    mundo.actualizarUnidades(Settings.FIXED_TimeStep);
                    mundo.actualizarFisica(Settings.FIXED_TimeStep);
                    controlador.mundoView.enviarDatosAClientes();
                }
            }
            try { Thread.sleep((long)(1)); }
            catch (InterruptedException e) { logger.error("ERROR: Updateando la red: ", e); return; }
        }
    }


    private boolean executeRunnables()
    {
        synchronized (runnables)
        {   executedRunnables.addAll(runnables);
            runnables.clear();
        }
        if (executedRunnables.size() == 0) return false;

        for (int i=0; i< executedRunnables.size(); i++)
        {   executedRunnables.get(i).run(); }

        executedRunnables.clear();
        return true;
    }
}
