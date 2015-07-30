package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.DTOsMundo;
import DTO.DTOsPC;
import Data.Settings;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Interfaces.GameState.ListaPorCuadrantesI;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Mundo extends AbstractModel implements PropertyChangeListener, MundoI
{
    private Map<Integer, PCI> mapaPlayers = new HashMap<>();

    private List<PCI> listaPCs = new ArrayList<>();
    private ListaPorCuadrantesI<PCI> mapaPCs = new ListaPorCuadrantes<>();

    private Mapa mapa = new Mapa();
    private World world;

    public World getWorld()                                 { return world; }
    @Override public MapaI getMapa()                        { return mapa; }
    @Override public PCI getPC (int connectionID)           { return mapaPlayers.get(connectionID); }
    @Override public Iterator<PCI> getIteratorListaPCs()    { return listaPCs.iterator(); }
    public ListaPorCuadrantesI<PCI> getMapaPCs()            { return mapaPCs; }


    public Mundo()
    {
        world = new World(new Vector2(0,0), false);

        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   mapa.setTerreno(x,y,0,(short)0); }
        }
    }

    //PLAYERS:
    //-------------------------------------------------------------------------------------------------------------
    public void añadirPC (PCI pc)
    {
        mapaPlayers.put(pc.getConnectionID(), pc);
        listaPCs.add(pc);
        mapaPCs.put(pc);

        pc.añadirObservador(this);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    public void eliminarPC (int connectionID)
    {
        PCI pc = mapaPlayers.get(connectionID);
        mapaPlayers.remove(connectionID);
        listaPCs.remove(pc);
        mapaPCs.remove(pc);

        pc.eliminarObservador(this);
        pc.dispose();

        DTOsMundo.EliminarPC eliminarPlayer = new DTOsMundo.EliminarPC(pc);
        notificarActualizacion("eliminarPC", null, eliminarPlayer);
    }

    public void posicionPC (PCI pc)
    {   mapaPCs.update(pc); }

    //IA MUNDO:
    //-------------------------------------------------------------------------------------------------------------
    public void actualizarFisica(float delta)
    {   world.step(delta, 8, 6); }

    public void actualizarUnidades(float delta)
    {
        //actualizar PCs
        Iterator<PCI> iteratorPCs = getIteratorListaPCs();
        while (iteratorPCs.hasNext())
        {   iteratorPCs.next().actualizar(delta); }
    }

    //CAMPOS OBSERVADOS:
    //-------------------------------------------------------------------------------------------------------------
    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPC.PosicionPC)
        {   posicionPC(((DTOsPC.PosicionPC) evt.getNewValue()).pc); }
    }
}
