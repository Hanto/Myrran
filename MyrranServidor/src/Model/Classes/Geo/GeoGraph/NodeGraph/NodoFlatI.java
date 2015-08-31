package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 30/08/2015.

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedNode;
import com.badlogic.gdx.utils.Array;

public interface NodoFlatI extends IndexedNode<NodoFlatI>
{
    public int getTamañoMinimo();
    public void setConnections(Array<Connection<NodoFlatI>> connections);
}
