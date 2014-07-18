package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.NetDTO;
import Data.Settings;
import Interfaces.Geo.MapaI;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Classes.Mobiles.PC;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.*;

public class Mundo extends AbstractModel
{
    private List<PC> listaPlayers = new ArrayList<>();
    private Map<Integer, PC> mapaPlayers = new HashMap<>();

    private Mapa mapa = new Mapa();
    private World world;

    public World getWorld()                         { return world; }
    public MapaI getMapa()                          { return mapa; }
    public Iterator<PC> getIteratorListaPlayers()   { return listaPlayers.iterator(); }


    public Mundo()
    {
        world = new World(new Vector2(0,0), false);

        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   mapa.setTerreno(x,y,0,(short)0); }
        }
    }

    public void añadirPC (int connectionID)
    {
        PC pc = new PC(connectionID, this);
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


    public void procesarInputs (float delta)
    {
        Iterator<PC> iteratorPCs = getIteratorListaPlayers();
        while (iteratorPCs.hasNext())
        {   iteratorPCs.next().procesarInput(delta); }
    }

    public void actualizarFisica(float delta)
    {   world.step(delta, 8, 6); }

    public void actualizarUnidades(float delta)
    {
        //actualizar PCs
        Iterator<PC> iteratorPCs = getIteratorListaPlayers();
        while (iteratorPCs.hasNext())
        {   iteratorPCs.next().actualizar(delta); }
    }

    public void enviarSnapshots()
    {
        Iterator<PC> iteratorPCs = getIteratorListaPlayers();
        while (iteratorPCs.hasNext())
        {   iteratorPCs.next().enviarPlayerSnapshot(); }
    }
}
