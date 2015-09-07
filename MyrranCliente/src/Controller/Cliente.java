package Controller;// Created by Hanto on 08/04/2014.

import DTOs.KryoDTOs;
import DTOs.DTOsNet;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.slf4j.LoggerFactory;

public class Cliente extends Client
{
    protected Controlador controlador;
    protected ClienteInputs clienteInputs;
    public String host;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Cliente (Controlador controlador)
    {
        super(16*1024, 128*1024);
        this.controlador = controlador;
        this.clienteInputs = new ClienteInputs(controlador);

        KryoDTOs.register(this);
        this.start();

        //Para activar el log completo de mensajes:
        //Log.TRACE();

        this.addListener
            (new Listener.QueuedListener
                (new Listener()
                {
                     @Override public void connected (Connection con)   { procesarConnected(con); }
                     @Override public void received (Connection con, Object obj) { procesarReceived(con, obj);}
                     @Override public void disconnected (Connection con) { }
                })
            {
                @Override protected void queue(Runnable runnable)
                {   Gdx.app.postRunnable(runnable); }
            });

        host = "localhost";
        //host = (String) JOptionPane.showInputDialog(null, "Host:", "Connect to server", JOptionPane.QUESTION_MESSAGE, null, null, "localhost");

        while (true)
        {
            try { this.connect(KryoDTOs.timeout, host, KryoDTOs.puertoTCP, KryoDTOs.puertoUDP); break; }
            catch (Exception IOException) { logger.warn("ERROR: Imposible conectar con el Servidor: "); }
        }

    }

    // RECEIVED:
    //------------------------------------------------------------------------------------------------------------------

    private void procesarReceived(Connection con, Object obj)
    {
        if (obj instanceof DTOsNet.Mapa)
        {   controlador.actualizarMapa(((DTOsNet.Mapa) obj));}

        else if (obj instanceof DTOsNet.PCDTOs)
        {   clienteInputs.procesarActualizacionesPC((DTOsNet.PCDTOs) obj);}

        else if (obj instanceof DTOsNet.MobDTOs)
        {   clienteInputs.procesarActualizacionesMobs( (DTOsNet.MobDTOs)obj );}

        else if (obj instanceof DTOsNet.MiscDTOs)
        {   clienteInputs.procesarActualizacionesMisc((DTOsNet.MiscDTOs) obj);}

        else if (obj instanceof DTOsNet.ProyectilDTOs)
        {   clienteInputs.procesarActualizacionesProyectiles((DTOsNet.ProyectilDTOs) obj);}
    }

    // LOG IN
    //------------------------------------------------------------------------------------------------------------------

    private void procesarConnected(Connection con)
    {
        controlador.getMundo().getPlayer().setID(this.getID());
        enviarAServidor(new DTOsNet.LogIn());
    }

    //------------------------------------------------------------------------------------------------------------------

    public void enviarAServidor(Object obj)
    {
        String nombreDTOs ="";
        if (obj instanceof DTOsNet.PlayerDTOs)
        {
            DTOsNet.PlayerDTOs dtos = (DTOsNet.PlayerDTOs)obj;
            for (int i=0; i< dtos.listaDTOs.length; i++)
            {   nombreDTOs = nombreDTOs +" - "+dtos.listaDTOs[i].getClass().getSimpleName(); }
        }
        //logger.trace("ENVIAR: {} {} bytes"+nombreDTOs, obj.getClass().getSimpleName(), this.sendTCP(obj));
        this.sendTCP(obj);
    }
}
