package Controller;// Created by Hanto on 07/04/2014.

import DTOs.DTOsNet;
import DTOs.KryoDTOs;
import Interfaces.Misc.Network.MainLoopI;
import Interfaces.Misc.Network.ServidorI;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Servidor extends Server implements ServidorI
{
    public Controlador controlador;
    public MainLoopI mainLoop;
    protected ServidorInputs servidorInputs;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Servidor (MainLoopI mainLoop, Controlador controlador)
    {
        super(512*1024, 4*1024);
        this.controlador = controlador;
        this.mainLoop = mainLoop;
        this.servidorInputs = new ServidorInputs(controlador);

        KryoDTOs.register(this);
        this.start();

        //Para activar el log completo de mensajes:
        //Log.TRACE();
/*
        synchronized (this.controlador.getMundo())
        {

            this.addListener
                (new Listener.ThreadedListener
                    (new Listener()
                    {
                        public void connected (Connection con)              {  }
                        public void disconnected (Connection con)           { procesarDisconnected(con); }
                        public void received (Connection con, Object obj)   { procesarReceived(con, obj); }
                    }));

        }*/

        //Codigo Alternativo monoHilo:

        this.addListener
            (new Listener.QueuedListener
                (new Listener()
                    {
                        public void connected (Connection con)              { }
                        public void disconnected (Connection con)           { procesarDisconnected(con); }
                        public void received (Connection con, Object obj)   { procesarReceived(con, obj); }
                    })
                {
                    @Override protected void queue(Runnable runnable)
                    {   Servidor.this.mainLoop.postRunnable(runnable); }
                });

        try { this.bind(KryoDTOs.puertoTCP, KryoDTOs.puertoUDP); }
        catch (Exception e) { System.out.println("ERROR: Inicio Servidor: "+e); }
    }

    // DISCONNECT:
    //------------------------------------------------------------------------------------------------------------------

    private void procesarDisconnected(Connection con)
    {   controlador.eliminarPC(con.getID()); }

    // RECEIVED::
    //------------------------------------------------------------------------------------------------------------------

    private void procesarReceived(Connection con, Object obj)
    {
        if (obj instanceof DTOsNet.PlayerDTOs)
        {   servidorInputs.procesarInput(con.getID(), (DTOsNet.PlayerDTOs)obj); }

        if (obj instanceof DTOsNet.LogIn)
        {   servidorInputs.procesarLogIn(con.getID());}
    }

    // ENVIO DE DATOS (con calculo de tamaño de informacion enviada)
    //------------------------------------------------------------------------------------------------------------------

    @Override public void enviarACliente(int connectionID, Object obj)
    {
        String nombreDTOs ="";
        if (obj instanceof DTOsNet.PCDTOs)
        {
            DTOsNet.PCDTOs dtos = (DTOsNet.PCDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        else if (obj instanceof DTOsNet.MiscDTOs)
        {
            DTOsNet.MiscDTOs dtos = (DTOsNet.MiscDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        else if (obj instanceof DTOsNet.MobDTOs)
        {
            DTOsNet.MobDTOs dtos = (DTOsNet.MobDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        else if (obj instanceof DTOsNet.ProyectilDTOs)
    {
        DTOsNet.ProyectilDTOs dtos = (DTOsNet.ProyectilDTOs)obj;
        for (int i=0; i< dtos.listaDTOs.length; i++)
        {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
    }

        Connection[] connections = this.getConnections();
        for (int i = 0, n = connections.length; i < n; i++) {
            Connection connection = connections[i];
            if (connection.getID() == connectionID) {
                //logger.trace("ENVIAR: "+connectionID+" {} {} bytes"+nombreDTOs, obj.getClass().getSimpleName(), connection.sendTCP(obj));
                connection.sendTCP(obj);
                break;
            }
        }
    }

    @Override public void enviarATodosClientes(Object obj)
    {   this.sendToAllTCP(obj); }

    @Override public void enviarATodosClientesMenosUno(int connectionID, Object obj)
    {   this.sendToAllExceptTCP(connectionID, obj);}
}
