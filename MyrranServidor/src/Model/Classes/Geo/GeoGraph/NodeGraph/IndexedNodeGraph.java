package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 14/08/2015.

import Interfaces.Geo.CeldaI;
import Model.Settings;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class IndexedNodeGraph extends NodeGraph<IndexedNodeGraph>
{
    //public final int x;
    //public final int y;
    //public CeldaI celda;
    //protected Array<Connection<IndexedNodeGraph>>connections;

    public IndexedNodeGraph(int x, int y, CeldaI celda, int numeroConexiones)
    {   super(x, y, celda, new Array<Connection<IndexedNodeGraph>>(numeroConexiones)); }

    @Override public int getIndex()
    {   return ( x * Settings.MAPTILE_NumTilesY + y); }
}
