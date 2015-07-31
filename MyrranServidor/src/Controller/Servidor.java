package Controller;// Created by Hanto on 07/04/2014.

import DTO.DTOsCampoVision;
import DTO.DTOsPlayer;
import DTO.NetDTOs;
import ch.qos.logback.classic.Logger;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.slf4j.LoggerFactory;

public class Servidor extends Server
{
    public Controlador controlador;
    protected ServidorInputs servidorInputs;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Servidor (Controlador controlador)
    {
        super(512*1024, 4*1024);
        this.controlador = controlador;
        this.servidorInputs = new ServidorInputs(controlador);

        NetDTOs.register(this);
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
                    {   Servidor.this.controlador.postRunnable(runnable); }
                });

        try { this.bind(NetDTOs.puertoTCP, NetDTOs.puertoUDP); }
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
        if (obj instanceof DTOsPlayer.PlayerDTOs)
        {   servidorInputs.procesarInput(con.getID(), (DTOsPlayer.PlayerDTOs)obj); }

        if (obj instanceof DTOsPlayer.LogIn)
        {   servidorInputs.procesarLogIn(con.getID());}
    }

    // ENVIO DE DATOS (con calculo de tamaño de informacion enviada)
    //------------------------------------------------------------------------------------------------------------------

    public void enviarACliente(int connectionID, Object obj)
    {
        String nombreDTOs ="";
        if (obj instanceof DTOsCampoVision.PCDTOs)
        {
            DTOsCampoVision.PCDTOs dtos = (DTOsCampoVision.PCDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        else if (obj instanceof DTOsCampoVision.MiscDTOs)
        {
            DTOsCampoVision.MiscDTOs dtos = (DTOsCampoVision.MiscDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }

        Connection[] connections = this.getConnections();
        for (int i = 0, n = connections.length; i < n; i++) {
            Connection connection = connections[i];
            if (connection.getID() == connectionID) {
                logger.trace("ENVIAR: "+connectionID+" {} {} bytes"+nombreDTOs, obj.getClass().getSimpleName(), connection.sendTCP(obj));
                break;
            }
        }
    }

    public void enviarATodosClientes(Object obj)
    {   this.sendToAllTCP(obj); }

    public void enviarATodosClientesMenosUno(int connectionID, Object obj)
    {   this.sendToAllExceptTCP(connectionID, obj);}
}
