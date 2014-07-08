package Controller;// Created by Hanto on 07/04/2014.

import DTO.NetDTO;
import ch.qos.logback.classic.Logger;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.slf4j.LoggerFactory;

public class Servidor extends Server
{
    public Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Servidor (Controlador control)
    {
        super(512*1024, 4*1024);
        this.controlador = control;

        NetDTO.register(this);
        this.start();

        //Para activar el log completo de mensajes:
        //Log.TRACE();

        synchronized (controlador.getMundo())
        {

            this.addListener
                (new Listener.ThreadedListener
                    (new Listener()
                    {
                        public void connected (Connection con)              { controlador.añadirPC(con.getID()); }
                        public void disconnected (Connection con)           { controlador.eliminarPC(con.getID()); }
                        public void received (Connection con, Object obj)   { procesarMensajeCliente(con, obj); }
                    }));
        }


        try { this.bind(NetDTO.puertoTCP, NetDTO.puertoUDP); }
        catch (Exception e) { System.out.println("ERROR: Inicio Servidor: "+e); }
    }

    private void procesarMensajeCliente(Connection con, Object obj)
    {
        if (obj instanceof NetDTO.PosicionPPC)
        {
            int conID = con.getID();
            float x = ((NetDTO.PosicionPPC) obj).x;
            float y = ((NetDTO.PosicionPPC) obj).y;

            controlador.moverPC(conID, x, y);
        }

        if (obj instanceof NetDTO.AnimacionPPC)
        {
            int conID = ((NetDTO.AnimacionPPC) obj).connectionID;
            int numAnimacion = ((NetDTO.AnimacionPPC) obj).numAnimacion;
            controlador.cambiarAnimacionPC(conID, numAnimacion);
        }

        if (obj instanceof NetDTO.CastearPPC)
        {
            int conID = con.getID();
            boolean castear = ((NetDTO.CastearPPC) obj).castear;
            int targetX = ((NetDTO.CastearPPC) obj).targetX;
            int targetY = ((NetDTO.CastearPPC) obj).targetY;
            controlador.castear(conID, castear, targetX, targetY);
        }

        if (obj instanceof NetDTO.SetSpellIDSeleccionado)
        {
            String spellID = ((NetDTO.SetSpellIDSeleccionado) obj).spellID;
            Object parametros = ((NetDTO.SetSpellIDSeleccionado) obj).parametrosSpell;
            controlador.cambiarSpellSeleccionado(con.getID(), spellID, parametros);
        }

        if (obj instanceof NetDTO.SetParametrosSpell)
        {
            Object parametros = ((NetDTO.SetParametrosSpell) obj).parametrosSpell;
            controlador.cambiarParametrosSpell(con.getID(), parametros);
        }

        if (obj instanceof NetDTO.ModificarNumTalentosSkillPersonalizadoPPC)
        {
            int conID = con.getID();
            String skillID = ((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) obj).skillID;
            int statID = ((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) obj).statID;
            int valor = ((NetDTO.ModificarNumTalentosSkillPersonalizadoPPC) obj).valor;
            controlador.modificarSkillTalentoPC(conID, skillID, statID, valor);
        }
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
