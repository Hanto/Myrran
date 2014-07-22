package Model.GameState;// Created by Hanto on 08/04/2014.

import Controller.Cliente;
import DTO.NetDTO;
import Data.Settings;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
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
    private Cliente cliente;

    private List<PC> listaPlayers = new ArrayList<>();
    private Map<Integer,PC> mapaPlayers = new HashMap<>();

    private Player player;
    private Mapa mapa;
    private World world;

    public boolean[][] mapTilesCargados = new boolean[3][3];


    //Get:
    public List<? extends PC> listaPlayers()        { return listaPlayers; }
    public PC getPC (int connectionID)              { return mapaPlayers.get(connectionID); }
    public Player getPlayer()                       { return player; }
    public Mapa getMapa()                           { return mapa; }
    public World getWorld()                         { return world; }
    public Cliente getCliente()                     { return cliente; }

    //SET:
    public void setCliente(Cliente cliente)         { this.cliente = cliente; }

    public Mundo()
    {
        world = new World(new Vector2(0, 0), false);
        player = new Player(this);
        mapa = new Mapa(player);
    }

    //SE NOTIFICA:
    public void añadirPC (int connectionID, float x, float y)
    {
        PC pc = new PC(connectionID, world);
        pc.setPosition(x, y);
        listaPlayers.add(pc);
        mapaPlayers.put(pc.getConnectionID(), pc);
        Object añadirPC = new NetDTO.AñadirPPC(pc);
        notificarActualizacion("añadirPC", null, añadirPC);
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

    public void enviarDatosAServidor()
    {
        if (player.getNetPlayer().contieneDatos())
        {
            player.getNetPlayer().setConnectionID(cliente.getID());
            cliente.enviarAServidor(player.getNetPlayer());
            player.getNetPlayer().clear();
        }
        player.enviarComandosAServidor();
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


    public void actualizarMapa (NetDTO.ActualizarMapa mapaServidor)
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
