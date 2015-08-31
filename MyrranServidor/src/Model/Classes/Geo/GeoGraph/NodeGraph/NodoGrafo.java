package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 14/08/2015.

import Model.Settings;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public abstract class NodoGrafo<N extends IndexedNode<N>> implements IndexedNode<N>
{
    // El tipo de nodo es un parametro ya que puede ser tanto un nodo Indexed A* como un nodo Hierarquico
    //------------------------------------------------------------------------------------------------------------------

    public final int x;
    public final int y;
    protected Array<Connection<N>> connections;

    public NodoGrafo(int x, int y)
    {   this.x = x; this.y = y; }

    @Override public Array<Connection<N>> getConnections()
    {   return this.connections; }

    @Override public int getIndex()
    {   return ( x * Settings.MAPTILE_NumTilesY + y); }
}
