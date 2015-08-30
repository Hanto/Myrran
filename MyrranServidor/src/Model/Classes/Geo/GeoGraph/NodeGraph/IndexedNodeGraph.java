package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 14/08/2015.

import Model.Settings;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class IndexedNodeGraph extends NodeGraph<IndexedNodeGraph>
{
    public IndexedNodeGraph(int x, int y, int tamañoMinimo)
    {   super(x, y, tamañoMinimo, new Array<Connection<IndexedNodeGraph>>(4)); }

    @Override public int getIndex()
    {   return ( x * Settings.MAPTILE_NumTilesY + y); }

}
