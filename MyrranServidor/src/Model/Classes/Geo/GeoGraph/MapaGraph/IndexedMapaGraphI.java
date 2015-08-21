package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.NodeGraph.AbstractNodeGraph;

public interface IndexedMapaGraphI<N extends AbstractNodeGraph<N>>
{
    public N getNode(int x, int y);
    public N getNode(int indice);
}
