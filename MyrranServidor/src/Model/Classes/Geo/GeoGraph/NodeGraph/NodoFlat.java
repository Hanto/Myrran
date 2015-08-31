package Model.Classes.Geo.GeoGraph.NodeGraph;// Created by Hanto on 30/08/2015.

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class NodoFlat extends NodoGrafo<NodoFlatI> implements NodoFlatI
{
    private int tamañoMinimo;

    public NodoFlat(int x, int y, int tamañoMinimo)
    {   super(x, y);
        this.tamañoMinimo = tamañoMinimo;
    }

    @Override   public int getTamañoMinimo()
    {   return tamañoMinimo; }

    @Override public void setConnections(Array<Connection<NodoFlatI>> connections)
    {   this.connections = connections; }
}
