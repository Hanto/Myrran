package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.AbstractModel;
import Model.Classes.Geo.GeoGraph.NodeGraph.AbstractNodeGraph;
import Model.Settings;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractMapaGraph<N extends AbstractNodeGraph<N>> extends AbstractModel implements
        IndexedGraph<N>, IndexedMapaGraphI<N>
{
    protected Array<N> listaNodos;

    public AbstractMapaGraph()
    {   this(new Array<N>()); }

    public AbstractMapaGraph(int capacity)
    {   this(new Array<N>(capacity)); }

    public AbstractMapaGraph(Array<N> nodes)
    {   this.listaNodos = nodes; }



    public abstract void init();



    @Override public N getNode(int x, int y)
    {   return listaNodos.get(x * Settings.MAPA_Max_TilesY + y); }

    @Override public N getNode(int indice)
    {   return listaNodos.get(indice); }

    @Override public Array<Connection<N>> getConnections(N fromNode)
    {   return listaNodos.get(fromNode.getIndex()).getConnections(); }

    @Override public int getNodeCount ()
    {   return listaNodos.size; }

}
