package Controller;// Created by Hanto on 08/04/2014.

import DTO.DTOsMapView;
import DTO.DTOsPC;
import DTO.NetDTOs;
import DTO.DTOsPC.PCDTOs;
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

        NetDTOs.register(this);
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

        try { this.connect(NetDTOs.timeout, host, NetDTOs.puertoTCP, NetDTOs.puertoUDP); }
        catch (Exception IOException) { logger.error("ERROR: Imposible conectar con el Servidor: ", IOException); }
    }

    // DTOS de entrada:
    //------------------------------------------------------------------------------------------------------------------

    private void procesarMensajeServidor (Connection con, Object obj)
    {
        if (obj instanceof PCDTOs)
        {   controlador.clienteInputs.procesarInput( (PCDTOs) obj);}

        if (obj instanceof DTOsMapView.Mapa)
        {   controlador.actualizarMapa((DTOsMapView.Mapa)obj); }

        if (obj instanceof DTOsMapView.MapTilesAdyacentes)
        {   controlador.actualizarMapTilesAdyacentes(((DTOsMapView.MapTilesAdyacentes) obj));}
    }

    //------------------------------------------------------------------------------------------------------------------

    public void enviarAServidor(Object obj)
    {
        String nombreDTOs ="";
        if (obj instanceof DTOsPC.PlayerDTOs)
        {
            DTOsPC.PlayerDTOs dtos = (DTOsPC.PlayerDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        logger.trace("ENVIAR: {} {} bytes"+nombreDTOs, obj.getClass().getSimpleName(), this.sendTCP(obj));
    }
}
