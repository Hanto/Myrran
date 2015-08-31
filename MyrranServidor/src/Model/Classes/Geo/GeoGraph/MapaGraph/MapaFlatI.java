package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.NodeGraph.NodoFlatI;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;

public interface MapaFlatI extends IndexedGraph<NodoFlatI>
{
    public NodoFlatI getNode(int x, int y);
    public NodoFlatI getNode(int indice);
}
