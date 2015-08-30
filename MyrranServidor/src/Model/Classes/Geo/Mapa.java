package Model.Classes.Geo;// Created by Hanto on 14/04/2014.

import DTO.DTOsMapa;
import Interfaces.Geo.MapaI;
import Interfaces.Geo.TerrenoI;
import Model.Classes.Geo.GeoGraph.ConnectionGraph.IndexedConnectionGraph;
import Model.Classes.Geo.GeoGraph.MapaGraph.IndexedMapaGraph;
import Model.Classes.Geo.GeoGraph.NodeGraph.IndexedNodeGraph;
import Model.Settings;
import com.badlogic.gdx.utils.Array;

public class Mapa extends IndexedMapaGraph<IndexedNodeGraph> implements MapaI
{
    private Celda[][] matriz = new Celda[Settings.MAPA_Max_TilesX][Settings.MAPA_Max_TilesY];

    public Mapa()
    {
        super(Settings.MAPA_Max_TilesX * Settings.MAPA_Max_TilesY);

        for (Celda[] fila: matriz)
        {   for (int i=0; i<fila.length; i++)
            {   fila[i] = new Celda(); }
        }
        init();
    }

    // PATH FINDING INIT:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void init()
    {
        for (int x = 0; x< Settings.MAPA_Max_TilesX; x++)
        {
            for (int y = 0; y< Settings.MAPA_Max_TilesY; y++)
            {   matriz[x][y].setTerreno(0,(short)0); }
        }

        for (int x=0; x < Settings.MAPA_Max_TilesX; x++)
        {
            for (int y=0; y < Settings.MAPA_Max_TilesY; y++)
            {
                listaNodos.add(new IndexedNodeGraph(x, y, 5));
                crearConexiones(x, y);
            }
        }
    }

    // MAPAI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public TerrenoI getTerreno (int x, int y, int numCapa)
    {
        if (x<0 || y<0 || x>= Settings.MAPA_Max_TilesX || y>= Settings.MAPA_Max_TilesY) return null;
        return matriz[x][y].getTerreno(numCapa);
    }

    @Override public short getTerrenoID (int x, int y, int numCapa)
    {
        if (x<0 || y<0 || x>= Settings.MAPA_Max_TilesX || y>= Settings.MAPA_Max_TilesY) return -1;
        else return matriz[x][y].getTerrenoID(numCapa);
    }

    @Override public boolean setTerreno (int x, int y, int numCapa, TerrenoI terreno)
    {
        if (x<0 || y<0 || x>= Settings.MAPA_Max_TilesX || y>= Settings.MAPA_Max_TilesY) return false;
        else if (matriz[x][y].getTerreno(numCapa) != terreno)
        {
            matriz[x][y].setTerreno(numCapa, terreno);
            DTOsMapa.SetTerreno cambioTerreno = new DTOsMapa.SetTerreno(x,y,numCapa,terreno.getID());
            notificarActualizacion("setTerreno", null, cambioTerreno);
            return true;
        }
        else return true;
    }

    @Override public boolean setTerreno (int x, int y, int numCapa, short iDTerreno)
    {
        if (x<0 || y<0 || x>= Settings.MAPA_Max_TilesX || y>= Settings.MAPA_Max_TilesY) return false;
        else if (matriz[x][y].getTerrenoID(numCapa) != iDTerreno)
        {
            if (matriz[x][y].setTerreno(numCapa, iDTerreno))
            {
                DTOsMapa.SetTerreno cambioTerreno = new DTOsMapa.SetTerreno(x,y,numCapa,iDTerreno);
                notificarActualizacion("setTerreno", null, cambioTerreno);
                return true;
            }
            else return false;
        }
        else return true;
    }

    private void crearConexiones(int x, int y)
    {
        IndexedNodeGraph nodo = getNode(x, y);
        Array<IndexedConnectionGraph> conexiones = new Array<>(4);

        if (x > 0) nodo.getConnections().add
                (new IndexedConnectionGraph(this, nodo, getNode(x-1, y)));

        if (y > 0) nodo.getConnections().add
                (new IndexedConnectionGraph(this, nodo, getNode(x, y - 1)));

        if (x < Settings.MAPA_Max_TilesX -1) nodo.getConnections().add
                (new IndexedConnectionGraph(this, nodo, getNode(x + 1, y)));

        if (y < Settings.MAPA_Max_TilesY -1) nodo.getConnections().add
                (new IndexedConnectionGraph(this, nodo, getNode(x, y + 1)));

        
    }
}
