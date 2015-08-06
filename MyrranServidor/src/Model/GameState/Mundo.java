package Model.GameState;// Created by Hanto on 07/04/2014.

import DTO.DTOsMundo;
import DTO.DTOsPC;
import DTO.DTOsProyectil;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Geo.MapaI;
import Interfaces.Misc.ListaPorCuadrantesI;
import Interfaces.Model.AbstractModel;
import Model.Classes.Geo.Mapa;
import Model.Datos.ListaPorCuadrantes;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Mundo extends AbstractModel implements PropertyChangeListener, MundoI
{
    protected Map<Integer, PCI> mapaPlayers = new HashMap<>();

    protected List<PCI> listaPCs = new ArrayList<>();
    protected List<ProyectilI> listaProyectiles = new ArrayList<>();

    protected ListaPorCuadrantesI<PCI> mapaPCs = new ListaPorCuadrantes<>();
    protected ListaPorCuadrantesI<ProyectilI> mapaProyectiles = new ListaPorCuadrantes<>();

    private Mapa mapa = new Mapa();
    private World world;

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public World getWorld()                       { return world; }
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
    //------------------------------------------------------------------------------------------------------------------

    public void añadirPC (PCI pc)
    {
        if (mapaPlayers.containsKey(pc.getID()))
        {   logger.error("ERROR: No se puede volver a añadir personaje que ya existe, ID: {}", pc.getID()); return; }

        mapaPlayers.put(pc.getID(), pc);
        listaPCs.add(pc);
        mapaPCs.put(pc);

        pc.añadirObservador(this);

        DTOsMundo.AñadirPC nuevoPlayer = new DTOsMundo.AñadirPC(pc);
        notificarActualizacion("añadirPC", null, nuevoPlayer);
    }

    public void eliminarPC (int connectionID)
    {
        if (!mapaPlayers.containsKey(connectionID))
        {   logger.error("ERROR: No se puede eliminar personaje. No existe ID: {}", connectionID); return; }

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

    //PROYECTILES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirProyectil(ProyectilI proyectil)
    {
        mapaProyectiles.put(proyectil);
        listaProyectiles.add(proyectil);
        proyectil.añadirObservador(this);
    }

    @Override public void eliminarProyectil(ProyectilI proyectil)
    {
        mapaProyectiles.remove(proyectil);
        listaProyectiles.remove(proyectil);
        proyectil.eliminarObservador(this);
    }

    public void posicionProyectil(ProyectilI proyectilI)
    {   mapaProyectiles.update(proyectilI); }

    //IA MUNDO:
    //------------------------------------------------------------------------------------------------------------------

    public void actualizarFisica(float delta)
    {   world.step(delta, 8, 6); }

    public void actualizarUnidades(float delta)
    {
        //actualizar PCs
        for (PCI pc : listaPCs)
        {   pc.actualizar(delta); }
        //actualizar Proyectiles:
        for (ProyectilI proyectil : listaProyectiles)
        {   proyectil.actualizar(delta); }
    }

    //CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPC.PosicionPC)
        {   posicionPC(((DTOsPC.PosicionPC) evt.getNewValue()).pc); }

        if (evt.getNewValue() instanceof DTOsProyectil.PosicionProyectil)
        {   posicionProyectil(((DTOsProyectil.PosicionProyectil) evt.getNewValue()).proyectil); }
    }
}
