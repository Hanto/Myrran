package Controller;// Created by Hanto on 08/04/2014.

import DTO.NetDTO;
import DTO.NetPCServidor.PCDTOs;
import DTO.NetPlayerCliente;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.slf4j.LoggerFactory;

public class Cliente extends Client
{
    private Controlador controlador;
    public String host;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Cliente (Controlador controlador)
    {
        super(16*1024, 128*1024);
        this.controlador = controlador;

        NetDTO.register(this);
        this.start();

        //Para activar el log completo de mensajes:
        //Log.TRACE();

        this.addListener
            (new Listener.QueuedListener
                (new Listener()
                {
                     @Override public void connected (Connection con)   { }
                     @Override public void received (Connection con, Object obj) { procesarMensajeServidor(con, obj);}
                     @Override public void disconnected (Connection con) { }
                })
            {
                @Override protected void queue(Runnable runnable)
                {   Gdx.app.postRunnable(runnable); }
            });

        host = "localhost"; //(String) JOptionPane.showInputDialog(null, "Host:", "Connect to server", JOptionPane.QUESTION_MESSAGE, null, null, "localhost");

        try { this.connect(NetDTO.timeout, host, NetDTO.puertoTCP, NetDTO.puertoUDP); }
        catch (Exception IOException) { logger.error("ERROR: Imposible conectar con el Servidor: ", IOException); }
    }

    private void procesarMensajeServidor (Connection con, Object obj)
    {
        if (obj instanceof PCDTOs)
        {   controlador.controlaPlayer.procesarInput( (PCDTOs) obj);}

        if (obj instanceof NetDTO.SetTerreno)
        {
            int celdaX = ((NetDTO.SetTerreno) obj).celdaX;
            int celdaY = ((NetDTO.SetTerreno) obj).celdaY;
            int numCapa = ((NetDTO.SetTerreno) obj).numCapa;
            short iDTerreno = ((NetDTO.SetTerreno) obj).iDTerreno;
            controlador.setTerreno(celdaX, celdaY, numCapa, iDTerreno);
        }

        if (obj instanceof NetDTO.EliminarPPC)
        {   controlador.mundo.eliminarPC( ((NetDTO.EliminarPPC) obj).connectionID);}

        if (obj instanceof NetDTO.ActualizarMapa)
        {   controlador.actualizarMapa((NetDTO.ActualizarMapa)obj); }

        if (obj instanceof NetDTO.MapTilesAdyacentesEnCliente)
        {   controlador.actualizarMapTilesCargados((NetDTO.MapTilesAdyacentesEnCliente)obj); }
    }

    public void enviarAServidor(Object obj)
    {
        String nombreDTOs ="";
        if (obj instanceof NetPlayerCliente.PlayerDTOs)
        {
            NetPlayerCliente.PlayerDTOs dtos = (NetPlayerCliente.PlayerDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        logger.trace("ENVIAR: {} {} bytes"+nombreDTOs, obj.getClass().getSimpleName(), this.sendTCP(obj));
    }
}
