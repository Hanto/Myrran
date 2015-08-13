package Model.GameState;// Created by Hanto on 08/04/2014.

import Controller.Cliente;
import DTO.DTOsMapView;
import DTO.DTOsMundo;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Model.AbstractClases.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.Player.Player;
import Model.Datos.ListaMapa;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.physics.box2d.World;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class Mundo extends AbstractModel implements MundoI
{
    private ListaMapa<PCI> listaMapaPlayers = new ListaMapa<>();
    private ListaMapa<ProyectilI> listaMapaProyectiles = new ListaMapa<>();
    private ListaMapa<MobI> listaMapaMobs = new ListaMapa<>();

    private World world;
    private Player player;
    private Mapa mapa;

    public Player getPlayer()                               { return player; }
    @Override public MapaI getMapa()                        { return mapa; }
    @Override public World getWorld()                       { return world; }

    public boolean[][] mapTilesCargados = new boolean[3][3];
    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    public Mundo(World world, Player pc, Mapa map)
    {
        this.world = world;
        this.player = pc;
        this.mapa = map;
    }

    // PLAYERS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirPC (PCI pc)
    {
        listaMapaPlayers.add(pc);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    @Override public void eliminarPC (int connectionID)
    {
        PCI pc = listaMapaPlayers.remove(connectionID);
        pc.dispose();

        DTOsMundo.EliminarPC eliminarPC = new DTOsMundo.EliminarPC(pc);
        notificarActualizacion("eliminarPC", null, eliminarPC);
    }

    @Override public PCI getPC (int connectionID)
    {   return listaMapaPlayers.get(connectionID); }

    @Override public Iterator<PCI> getIteratorPCs()
    {   return listaMapaPlayers.iterator(); }

    public Iterator<PCI> getIteratorPCs(int mapTileX, int mapTileY)
    {   return listaMapaPlayers.iterator(); }

    // PROYECTILES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirProyectil(ProyectilI proyectil)
    {
        listaMapaProyectiles.add(proyectil);

        DTOsMundo.AñadirProyectil nuevoProyectil = new DTOsMundo.AñadirProyectil(proyectil);
        notificarActualizacion("añadirProyectil", null, nuevoProyectil);
    }

    @Override public void eliminarProyectil(int iD)
    {
        ProyectilI proyectil = listaMapaProyectiles.remove(iD);
        proyectil.dispose();

        DTOsMundo.EliminarProyectil eliminarProyectil = new DTOsMundo.EliminarProyectil(proyectil);
        notificarActualizacion("eliminarProyectil", null, eliminarProyectil);
    }

    @Override public ProyectilI getProyectil (int iD)
    {   return listaMapaProyectiles.get(iD); }

    @Override public Iterator<ProyectilI> getIteratorProyectiles()
    {   return listaMapaProyectiles.iterator(); }

    @Override public Iterator<ProyectilI> getIteratorProyectiles(int mapTileX, int mapTileY)
    {   return listaMapaProyectiles.iterator(); }

    // MOBS:
    //--------------------------------------------------------------------------------------------------------------sw----

    @Override public void añadirMob (MobI mob)
    {
        listaMapaMobs.add(mob);
    }

    @Override public void eliminarMob (int iD)
    {
        listaMapaMobs.remove(iD);
    }

    @Override public MobI getMob (int iD)
    {   return listaMapaMobs.get(iD); }

    @Override public Iterator<MobI> getIteratorMobs()
    {
        return null;
    }
    @Override public Iterator<MobI> getIteratorMobs(int mapTileX, int mapTileY) { return null; }


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

    public void actualizarUnidades(float delta, MundoI mundo)
    {   //Actualizar a todas las unidades a partir de los datos ya interpolados

        //PLAYER:
        player.actualizar(delta, mundo);
        //PLAYERS:
        for (PCI pc: listaMapaPlayers)
        {   pc.actualizar(delta, mundo); }
        //PROYECTILES:
        Iterator<ProyectilI>iterator = listaMapaProyectiles.iterator(); ProyectilI pro;
        while (iterator.hasNext())
        {
            pro = iterator.next();
            if (pro.consumirse(delta))
            {
                iterator.remove();
                pro.dispose();

                DTOsMundo.EliminarProyectil eliminarProyectil = new DTOsMundo.EliminarProyectil(pro);
                notificarActualizacion("eliminarProyectil", null, eliminarProyectil);
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

        //calculamos los nuevos valores:
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