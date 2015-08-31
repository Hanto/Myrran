package Model.Classes.Geo.GeoGraph.ConnectionGraph;// Created by Hanto on 14/08/2015.

import Model.Classes.Geo.GeoGraph.MapaGraph.MapaFlatI;
import Model.Classes.Geo.GeoGraph.NodeGraph.NodoFlatI;
import com.badlogic.gdx.ai.pfa.Connection;

public class ConexionFlat implements Connection<NodoFlatI>
{
    protected MapaFlatI mapa;
    protected NodoFlatI fromNode;
    protected NodoFlatI toNode;

    public ConexionFlat(MapaFlatI mapa, NodoFlatI fromNode, NodoFlatI toNode)
    {
        this.mapa = mapa;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override public float getCost ()
    {   return 1f; }

    @Override public NodoFlatI getFromNode ()
    {   return fromNode; }

    @Override public NodoFlatI getToNode ()
    {   return toNode; }

}
