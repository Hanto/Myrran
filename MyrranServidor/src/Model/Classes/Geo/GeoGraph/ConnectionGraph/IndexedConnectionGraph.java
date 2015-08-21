package Model.Classes.Geo.GeoGraph.ConnectionGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.MapaGraph.IndexedMapaGraphI;
import Model.Classes.Geo.GeoGraph.NodeGraph.IndexedNodeGraph;
import com.badlogic.gdx.ai.pfa.Connection;

public class IndexedConnectionGraph implements Connection<IndexedNodeGraph>
{
    protected IndexedMapaGraphI mapa;
    protected IndexedNodeGraph fromNode;
    protected IndexedNodeGraph toNode;

    public IndexedConnectionGraph(IndexedMapaGraphI mapa, IndexedNodeGraph fromNode, IndexedNodeGraph toNode)
    {
        this.mapa = mapa;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override public float getCost ()
    {   return 1f; }

    @Override public IndexedNodeGraph getFromNode ()
    {   return fromNode; }

    @Override public IndexedNodeGraph getToNode ()
    {   return toNode; }

}
