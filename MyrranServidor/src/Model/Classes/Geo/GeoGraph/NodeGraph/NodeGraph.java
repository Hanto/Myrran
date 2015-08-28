package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 14/08/2015.

import Interfaces.Geo.CeldaI;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public abstract class NodeGraph<N extends NodeGraph<N>> implements IndexedNode<N>
{
    // El tipo de nodo es un parametro ya que puede ser tanto un nodo Indexed A* como un nodo Hierarquico
    //------------------------------------------------------------------------------------------------------------------

    public final int x;
    public final int y;
    public CeldaI celda;
    protected Array<Connection<N>>connections;

    public NodeGraph(int x, int y, CeldaI celda, Array<Connection<N>> connections)
    {
        this.x = x;
        this.y = y;
        this.celda = celda;
        this.connections = connections;
    }

    @Override public Array<Connection<N>> getConnections()
    {   return this.connections; }
}
