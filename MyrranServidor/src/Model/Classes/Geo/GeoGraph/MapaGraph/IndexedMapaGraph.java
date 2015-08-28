package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.NodeGraph.IndexedNodeGraph;
import com.badlogic.gdx.utils.Array;

public abstract class IndexedMapaGraph extends MapaGraph<IndexedNodeGraph>
{
    public IndexedMapaGraph()
    {   super(); }

    public IndexedMapaGraph(int capacidad)
    {   super(capacidad); }

    public IndexedMapaGraph(Array<IndexedNodeGraph> nodos)
    {   super(nodos); }
}
