package Model.GameState;// Created by Hanto on 08/04/2014.

import Controller.Cliente;
import DTO.DTOsPC;
import DTO.DTOsMundo;
import Data.Settings;
import Interfaces.Model.AbstractModel;
import Model.Classes.Mobiles.PC;
import Model.Classes.Mobiles.Player;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mundo extends AbstractModel
{
    private List<PC> listaPlayers = new ArrayList<>();
    private Map<Integer,PC> mapaPlayers = new HashMap<>();

    private Player player;
    private Model.Classes.Geo.Mapa mapa;
    private World world;

    public boolean[][] mapTilesCargados = new boolean[3][3];


    //Get:
    public List<? extends PC> listaPlayers()        { return listaPlayers; }
    public PC getPC (int connectionID)              { return mapaPlayers.get(connectionID); }
    public Player getPlayer()                       { return player; }
    public Model.Classes.Geo.Mapa getMapa()                           { return mapa; }
    public World getWorld()                         { return world; }

    public Mundo()
    {
        world = new World(new Vector2(0, 0), false);
        player = new Player(this);
        mapa = new Model.Classes.Geo.Mapa(player);
    }

    //SE NOTIFICA:
    public void añadirPC (int connectionID, float x, float y)
    {
        PC pc = new PC(connectionID, world);
        pc.setPosition(x, y);
        listaPlayers.add(pc);
        mapaPlayers.put(pc.getConnectionID(), pc);
        Object nuevoPlayer = new DTOsMundo.NuevoPlayer(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    public void eliminarPC (int connectionID)
    {
        PC pc = mapaPlayers.get(connectionID);
        listaPlayers.remove(pc);
        mapaPlayers.remove(connectionID);
        pc.dispose();
    }

    public void actualizarFisica(float delta)
    {
        //Salvamos los ultimos valores para poder interpolarlos
        player.copiarUltimaPosicion();
        //calculamos los nuevos:
        world.step(delta, 8, 6);
    }

    public void enviarDatosAServidor(Cliente cliente)
    {
        if (player.getNotificador().contieneDatos())
        {   cliente.enviarAServidor(player.getNotificador().getDTOs()); }
    }

    public void actualizarUnidades(float delta)
    {
        //Actualizar a todas las unidades a partir de los datos ya interpolados
        player.actualizar(delta);
        //Actualizar a los demas jugador multiplayer:
        for (PC pc: listaPlayers)
        {   pc.actualizar(delta); }
    }

    public void interpolarPosicion(float alpha)
    {
        //Interpolamos las posiciones y angulos con el resto del TimeStep:
        player.interpolarPosicion(alpha);
    }


    public void actualizarMapa (DTOsPC.Mapa mapaServidor)
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
}
