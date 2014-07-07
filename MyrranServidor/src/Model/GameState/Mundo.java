package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.NetDTO;
import Data.Settings;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.PC;

import java.util.*;

public class Mundo extends AbstractModel
{
    private List<PC> listaPlayers = new ArrayList<>();
    private Map<Integer, PC> mapaPlayers = new HashMap<>();

    private Mapa mapa = new Mapa();

    public Mapa getMapa()                           { return mapa; }
    public Iterator<PC> getIteratorListaPlayers()   { return listaPlayers.iterator(); }


    public Mundo()
    {
        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   mapa.setTerreno(x,y,0,(short)0); }
        }
    }

    public void añadirPC (int connectionID)
    {
        PC pc = new PC(connectionID, mapa);
        listaPlayers.add(pc);
        mapaPlayers.put(pc.getConnectionID(), pc);
        Object añadirPC = new NetDTO.AñadirPPC(pc);
        notificarActualizacion("añadirPC", null, añadirPC);
    }

    public void eliminarPC (int connectionID)
    {
        PC PC = mapaPlayers.get(connectionID);
        listaPlayers.remove(PC);
        mapaPlayers.remove(connectionID);
        PC.eliminar();
    }

    public PC getPC (int connectionID)
    {   return mapaPlayers.get(connectionID); }
}
