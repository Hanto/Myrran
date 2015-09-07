package Model.Classes.Geo;// Created by Hanto on 14/04/2014.

import DAO.Terreno.TerrenoDAO;
import DB.DAO;
import Model.Settings;
import Interfaces.Misc.Geo.CeldaI;
import Interfaces.Misc.Geo.TerrenoI;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Celda implements CeldaI,KryoSerializable
{
    //private Integer[] terrenosID = new Integer[MiscData.MAPA_Max_Capas_Terreno];
    private Short[] listaTerrenos = new Short[Settings.MAPA_Max_Capas_Terreno];
    private static final TerrenoDAO terrenoDAO = DAO.terrenoDAOFactory.getTerrenoDAO();

    //CONSTRUCTOR:
    public Celda()
    {
        for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
        {   listaTerrenos[i] = -1;}
    }

    //CONSTRUCTOR COPIA:
    public Celda(Celda celdaOrigen)
    {
        for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
        {   listaTerrenos[i] = celdaOrigen.getTerrenoID(i); }
    }

    @Override public short getTerrenoID(int numCapa)
    {
        return listaTerrenos[numCapa];
    }

    @Override public TerrenoI getTerreno(int numCapa)
    {
        //TerrenoDAO terrenoDAO = DAO.terrenoDAOFactory.getTerrenoDAO();
        return terrenoDAO.getTerreno(listaTerrenos[numCapa]);
    }

    @Override public void setTerreno(int numCapa, TerrenoI terreno)
    {   listaTerrenos[numCapa] = terreno.getID(); }

    @Override public boolean setTerreno(int numCapa, short terrenoID)
    {
        //TerrenoDAO terrenoDAO = DAO.terrenoDAOFactory.getTerrenoDAO();
        if (terrenoDAO.getTerreno(terrenoID) == null && terrenoID != -1) { return false; }
        else { listaTerrenos[numCapa] = terrenoID; return true; }
    }

    //KryoSerializable:
    @Override public void write(Kryo kryo, Output output)
    {
        for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
            output.writeInt(listaTerrenos[i]);
    }

    @Override public void read(Kryo kryo, Input input)
    {
        for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
            setTerreno(i, input.readShort());
    }
}
