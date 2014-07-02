package Controller;// Created by Hanto on 09/04/2014.

import Data.MiscData;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Updater implements Runnable
{
    private Controlador controlador;
    private Mundo mundo;
    protected final List<Runnable> runnables = new ArrayList<>();
    protected final List<Runnable> executedRunnables = new ArrayList<>();

    public void postRunnable(Runnable runnable)
    {
        synchronized (runnables)
        {   runnables.add(runnable); }
    }

    public Updater(Controlador controlador, Mundo mundo)
    {
        this.controlador = controlador;
        this.mundo = mundo;

        new Thread(this).start();
    }

    @Override public void run()
    {
        while (true)
        {
            executeRunnables();

            synchronized (mundo)
            {
                mundoUpdate(MiscData.SERVIDOR_Delta_Time);
                netUpdate();
            }
            try { Thread.sleep(MiscData.NETWORK_Update_Time); }
            catch (InterruptedException e) { System.out.println("ERROR: Updateando la red: "+e); return; }
        }
    }

    private void netUpdate()
    {   controlador.netUpdater(); }

    private void mundoUpdate(float delta)
    {
        //actualizar PCs
        Iterator<PC> iteratorPCs = mundo.getIteratorListaPlayers();
        while (iteratorPCs.hasNext())
        {   iteratorPCs.next().actualizar(delta); }
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
