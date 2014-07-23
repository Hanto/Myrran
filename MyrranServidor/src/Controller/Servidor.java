package Controller;// Created by Hanto on 07/04/2014.

import DTO.NetDTO;
import DTO.NetPlayerCliente;
import ch.qos.logback.classic.Logger;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.slf4j.LoggerFactory;

public class Servidor extends Server
{
    public Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Servidor (Controlador controlador)
    {
        super(512*1024, 4*1024);
        this.controlador = controlador;

        NetDTO.register(this);
        this.start();

        //Para activar el log completo de mensajes:
        //Log.TRACE();

        synchronized (this.controlador.getMundo())
        {

            this.addListener
                (new Listener.ThreadedListener
                    (new Listener()
                    {
                        public void connected (Connection con)              {  }
                        public void disconnected (Connection con)           { Servidor.this.controlador.eliminarPC(con.getID()); }
                        public void received (Connection con, Object obj)   { procesarMensajeCliente(con, obj); }
                    }));

        }


        try { this.bind(NetDTO.puertoTCP, NetDTO.puertoUDP); }
        catch (Exception e) { System.out.println("ERROR: Inicio Servidor: "+e); }
    }

    private void procesarMensajeCliente(Connection con, Object obj)
    {
        if (obj instanceof NetPlayerCliente.DTOs)
        {   controlador.controlaPC.procesarInput(con.getID(), (NetPlayerCliente.DTOs)obj); }

        if (obj instanceof NetPlayerCliente.LogIn)
        {   controlador.controlaPC.procesarLogIn(con.getID());}
    }

    public void enviarACliente(int connectionID, Object obj)
    {
        Connection[] connections = this.getConnections();
        for (int i = 0, n = connections.length; i < n; i++) {
            Connection connection = connections[i];
            if (connection.getID() == connectionID) {
                logger.trace("ENVIAR: "+connectionID+" {} {} bytes", obj.getClass().getSimpleName(), connection.sendTCP(obj));
                break;
            }
        }
    }

    public void enviarATodosClientes(Object obj)
    {   this.sendToAllTCP(obj); }

    public void enviarATodosClientesMenosUno(int connectionID, Object obj)
    {   this.sendToAllExceptTCP(connectionID, obj);}
}
