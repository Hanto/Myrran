package Controller;// Created by Hanto on 09/04/2014.

import Data.Settings;
import Model.GameState.Mundo;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class Updater implements Runnable
{
    private Controlador controlador;
    private Mundo mundo;
    protected final List<Runnable> runnables = new ArrayList<>();
    protected final List<Runnable> executedRunnables = new ArrayList<>();

    private double timeStep = 0;

    private double newTime;
    private double currentTime;
    private double deltaTime;


    private double newTimeC;
    private double currentTimeC;
    private double deltaTimeC;


    private int contador = 0;
    private double total = 0;
    private float media;

    public void postRunnable(Runnable runnable)
    {
        synchronized (runnables)
        {   runnables.add(runnable); }
    }

    public Updater(Controlador controlador, Mundo mundo)
    {
        this.controlador = controlador;
        this.mundo = mundo;

        currentTime = TimeUtils.nanoTime() / 1000000.0;

        new Thread(this).start();
    }

    @Override public void run()
    {
        while (true)
        {
            synchronized (mundo)
            {
                newTime = TimeUtils.nanoTime() / 1000000.0;
                deltaTime = (newTime - currentTime);
                currentTime = newTime;

                total += deltaTime;
                timeStep += deltaTime;

                while (timeStep >= 30)
                {
                    contador++;
                    media = (float) total / (float) contador;
                    //System.out.println(media);

                    timeStep -= 30;

                    executeRunnables();
                    mundo.procesarInputs(Settings.FIXED_TimeStep);
                    mundo.actualizarUnidades(Settings.FIXED_TimeStep);
                    mundo.actualizarFisica(Settings.FIXED_TimeStep);
                    //mundo.enviarSnapshots();
                    netUpdate();
                }

                currentTimeC = TimeUtils.nanoTime();
                newTimeC = TimeUtils.nanoTime();
                deltaTimeC = (newTimeC - currentTimeC);
                //System.out.println("process Time: "+ deltaTimeC/1000000);
            }
            try { Thread.sleep((long)(1)); }
            catch (InterruptedException e) { System.out.println("ERROR: Updateando la red: "+e); return; }
        }
    }

    private void netUpdate()
    {   controlador.netUpdater(); }


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
