package Controller;// Created by Hanto on 07/04/2014.

import Model.GameState.Mundo;
import View.Gamestate.Vista2.MundoView2;
import View.Gamestate.Vistas.MundoView;

public class Controlador
{
    protected Servidor servidor;                  //Input principal de la simulacion
    protected ServidorInputs servidorInputs;
    protected Updater updater;

    protected Mundo mundo;
    protected MundoView mundoView;
    protected MundoView2 mundoView2;

    public Mundo getMundo()         { return mundo; }

    public Controlador (Mundo mundo)
    {
        this.mundo = mundo;

        mundoView = new MundoView(this, mundo);
        mundoView2 = new MundoView2(this, mundo);
        servidorInputs = new ServidorInputs(mundo, this);
        servidor = new Servidor(this);
        updater = new Updater(this, mundo);
    }

    public void actualizarRadarCampoVisiones()
    {   mundoView2.radar(); }

    public void enviarDatosAClientes()
    {   mundoView2.enviarDTOs(); }

    public void enviarACliente(int connectionID, Object obj)
    {   servidor.enviarACliente(connectionID, obj); }

    public void eliminarPC (int connectionID)
    {   mundo.eliminarPC(connectionID); }

    public void postRunnable(Runnable runnable)
    {   updater.postRunnable(runnable); }
}
