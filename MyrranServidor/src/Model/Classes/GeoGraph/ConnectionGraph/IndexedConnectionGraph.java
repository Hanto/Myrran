package Model.Classes.GeoGraph.ConnectionGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.GeoGraph.NodeGraph.IndexedNodeGraph;
import com.badlogic.gdx.ai.pfa.Connection;

public class IndexedConnectionGraph implements Connection<IndexedNodeGraph>
{
    protected IndexedNodeGraph fromNode;
    protected IndexedNodeGraph toNode;

    public IndexedConnectionGraph(IndexedNodeGraph fromNode, IndexedNodeGraph toNode)
    {
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
