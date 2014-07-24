package Controller;// Created by Hanto on 07/04/2014.

import Model.GameState.Mundo;
import View.MundoView;

public class Controlador
{
    protected Servidor servidor;                  //Input principal de la simulacion
    protected Updater updater;

    protected ControlaPlayer controlaPlayer;

    protected Mundo mundo;
    protected MundoView mundoView;

    public Mundo getMundo()         { return mundo; }

    public Controlador (Mundo mundo)
    {
        this.mundo = mundo;

        controlaPlayer = new ControlaPlayer(mundo, this);
        mundoView = new MundoView(this, mundo);
        servidor = new Servidor(this);
        updater = new Updater(this, mundo);
    }

    public void enviarACliente(int connectionID, Object obj)
    {   servidor.enviarACliente(connectionID, obj); }

    public void eliminarPC (int connectionID)
    {   mundo.eliminarPC(connectionID); }

    public void postRunnable(Runnable runnable)
    {   updater.postRunnable(runnable); }
}
