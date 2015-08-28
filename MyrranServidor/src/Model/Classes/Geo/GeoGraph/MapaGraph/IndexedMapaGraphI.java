package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.NodeGraph.NodeGraph;

public interface IndexedMapaGraphI<N extends NodeGraph<N>>
{
    public N getNode(int x, int y);
    public N getNode(int indice);
}
