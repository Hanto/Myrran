package Model.Classes.Geo.GeoGraph.MapaGraph;// Created by Hanto on 14/08/2015.

import Interfaces.Misc.Observable.AbstractModel;
import Model.Classes.Geo.GeoGraph.ConnectionGraph.ConexionFlat;
import Model.Classes.Geo.GeoGraph.NodeGraph.NodoFlatI;
import Model.Settings;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public abstract class MapaFlat extends AbstractModel implements MapaFlatI
{
    protected Array<NodoFlatI> listaNodos;

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public MapaFlat()
    {   this.listaNodos = new Array<>(Settings.MAPA_Max_TilesX * Settings.MAPA_Max_TilesY); }

    public MapaFlat(int tamaño)
    {   this.listaNodos = new Array<>(tamaño); }


    // MAPAGRAFOI:
    //------------------------------------------------------------------------------------------------------------------

    public abstract void init();

    @Override public NodoFlatI getNode(int x, int y)
    {   return listaNodos.get(x * Settings.MAPA_Max_TilesY + y); }

    @Override public NodoFlatI getNode(int indice)
    {   return listaNodos.get(indice); }

    @Override public Array<Connection<NodoFlatI>> getConnections(NodoFlatI fromNode)
    {   return listaNodos.get(fromNode.getIndex()).getConnections(); }

    @Override public int getNodeCount ()
    {   return listaNodos.size; }

    public void crearConexiones(int x, int y)
    {
        NodoFlatI nodoGrafo = getNode(x, y);

        Array<Connection<NodoFlatI>> conexiones = new Array<>(4);

        if (x > 0) conexiones.add
                (new ConexionFlat(this, nodoGrafo, getNode(x-1, y)));

        if (y > 0) conexiones.add
                (new ConexionFlat(this, nodoGrafo, getNode(x, y - 1)));

        if (x < Settings.MAPA_Max_TilesX -1) conexiones.add
                (new ConexionFlat(this, nodoGrafo, getNode(x + 1, y)));

        if (y < Settings.MAPA_Max_TilesY -1) conexiones.add
                (new ConexionFlat(this, nodoGrafo, getNode(x, y + 1)));

        nodoGrafo.setConnections(conexiones);
    }
}
