package Model.Classes.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.GeoGraph.NodeGraph.IndexedNodeGraph;
import com.badlogic.gdx.utils.Array;

public abstract class IndexedMapaGraph extends AbstractMapaGraph<IndexedNodeGraph>
{
    public IndexedMapaGraph()
    {   super(); }

    public IndexedMapaGraph(int capacidad)
    {   super(capacidad); }

    public IndexedMapaGraph(Array<IndexedNodeGraph> nodos)
    {   super(nodos); }
}
