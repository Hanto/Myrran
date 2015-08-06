package Model.GameState;// Created by Hanto on 08/04/2014.

import Controller.Cliente;
import DTO.DTOsMapView;
import DTO.DTOsMundo;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.PC;
import Model.Classes.Mobiles.Player;
import Model.Datos.ListaMapa;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Mundo extends AbstractModel implements MundoI
{
    private ListaMapa<PCI> listaMapaPlayers = new ListaMapa<>();
    private ListaMapa<ProyectilI> listaMapaProyectiles = new ListaMapa<>();

    private Player player;
    private Mapa mapa;
    private World world;

    public boolean[][] mapTilesCargados = new boolean[3][3];
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //Get:
    @Override public World getWorld()                       { return world; }
    @Override public MapaI getMapa()                        { return mapa; }
    @Override public PCI getPC (int connectionID)           { return listaMapaPlayers.get(connectionID); }
    @Override public Iterator<PCI> getIteratorListaPCs()    { return listaMapaPlayers.iterator(); }
    public Player getPlayer()                               { return player; }

    public Mundo()
    {
        world = new World(new Vector2(0, 0), false);
        player = new Player(this);
        mapa = new Mapa(player);
    }

    // PLAYERS:
    //------------------------------------------------------------------------------------------------------------------

    public void añadirPC (int connectionID, float x, float y)
    {
        PCI pc = new PC(connectionID, this);
        pc.setPosition(x, y);
        listaMapaPlayers.add(pc);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    public void eliminarPC (int connectionID)
    {
        PCI pc = listaMapaPlayers.remove(connectionID);
        pc.dispose();
    }

    // PROYECTILES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirProyectil(ProyectilI proyectil)
    {
        listaMapaProyectiles.add(proyectil);

        DTOsMundo.AñadirProyectil nuevoProyectil = new DTOsMundo.AñadirProyectil(proyectil);
        notificarActualizacion("añadirProyectil", null, nuevoProyectil);
    }

    @Override public void eliminarProyectil(ProyectilI proyectil)
    {   listaMapaProyectiles.remove(proyectil); }


    // MAPA:
    //------------------------------------------------------------------------------------------------------------------

    public void actualizarMapa (DTOsMapView.Mapa mapaServidor)
    {
        for (int y=0; y< mapaServidor.mapa[0].length; y++)
        {
            for (int x=0; x< mapaServidor.mapa.length; x++)
            {
                for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
                {
                    short idTerreno = mapaServidor.mapa[x][y].celda[i];
                    mapa.setTerreno(x+mapaServidor.esquinaInfIzdaX,y+mapaServidor.esquinaInfIzdaY,i,idTerreno);
                }
            }
        }
    }

    // UPDATE:
    //------------------------------------------------------------------------------------------------------------------

    public void actualizarUnidades(float delta)
    {   //Actualizar a todas las unidades a partir de los datos ya interpolados

        //PLAYER:
        player.actualizar(delta);
        //PLAYERS:
        for (PCI pc: listaMapaPlayers)
        {   pc.actualizar(delta); }
        //PROYECTILES:
        Iterator<ProyectilI>iterator = listaMapaProyectiles.iterator(); ProyectilI pro;
        while (iterator.hasNext())
        {
            pro = iterator.next();
            if (pro.consumirse(delta))
            {
                pro.dispose();
                iterator.remove();
            }
        }
    }

    //Salvamos los ultimos valores para poder interpolarlos
    public void actualizarFisica(float delta)
    {
        //PLAYER:
        player.copiarUltimaPosicion();
        //PROYECTILES:
        for (ProyectilI proyectil: listaMapaProyectiles)
        {   proyectil.copiarUltimaPosicion(); }

        //calculamos los nuevos:
        world.step(delta, 8, 6);
    }

    public void enviarDatosAServidor(Cliente cliente)
    {
        if (player.getNotificador().contieneDatos())
        {   cliente.enviarAServidor(player.getNotificador().getDTOs()); }
    }

    //Interpolamos las posiciones y angulos con el resto del TimeStep:
    public void interpolarPosicion(float alpha)
    {
        //PLAYER:
        player.interpolarPosicion(alpha);
        //PROYECTILES:
        for (ProyectilI proyectil: listaMapaProyectiles)
        {   proyectil.interpolarPosicion(alpha); }
    }
}
