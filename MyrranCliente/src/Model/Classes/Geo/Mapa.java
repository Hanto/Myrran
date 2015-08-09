package Model.Classes.Geo;// Created by Hanto on 19/05/2014.

import DAO.Terreno.TerrenoDAO;
import DB.DAO;
import DTO.DTOsMapa;
import DTO.DTOsPlayer;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.Geo.MapaI;
import Interfaces.Geo.TerrenoI;
import Interfaces.Model.AbstractModel;
import Model.Settings;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Mapa extends AbstractModel implements MapaI, PropertyChangeListener, Disposable
{
    private Celda[][] mapa;
    private Espacial espacial;

    public int mapTileCentroX = 0;
    public int mapTileCentroY = 0;
    private int numTilesX;
    private int numTilesY;
    private int reborde = 1;

    public Mapa(Espacial espacial)
    {
        this.espacial = espacial;
        espacial.añadirObservador(this);

        this.numTilesX = (int)Math.ceil((double) Settings.MAPTILE_Horizontal_Resolution /(double) Settings.TILESIZE);
        this.numTilesY = (int)Math.ceil((double) Settings.MAPTILE_Vertical_Resolution /(double) Settings.TILESIZE);
        mapa = new Celda[numTilesX*3+reborde*2][numTilesY*3+reborde*2];

        for (Celda[] fila: mapa)
        {   for (int i=0; i<fila.length; i++)
            {   fila[i] = new Celda(); }
        }
    }

    public void dispose()
    {   espacial.eliminarObservador(this); }

    private int getMapTileX()
    {   return (int)((espacial.getX() / (float)(numTilesX * Settings.TILESIZE))); }

    private int getMapTileY()
    {   return (int)((espacial.getY() / (float)(numTilesY * Settings.TILESIZE))); }

    private int getTileX(int tileX)
    {   return (tileX - (mapTileCentroX-1)*numTilesX)+reborde; }

    private int getTileY(int tileY)
    {   return (tileY - (mapTileCentroY-1)*numTilesY)+reborde; }

    public Celda getCelda(int tileX, int tileY)
    {
        int x = getTileX(tileX);
        int y = getTileY(tileY);

        if (x <0 || y <0 || x >= (numTilesX*3+reborde*2) || y >= (numTilesY*3+reborde*2)) return null;
        else return mapa[x][y];
    }

    @Override public TerrenoI getTerreno (int tileX, int tileY, int numCapa)
    {
        Celda celda = getCelda(tileX, tileY);

        if (celda == null) return null;
        else
        {
            TerrenoDAO terrenoDAO = DAO.terrenoDAOFactory.getTerrenoDAO();
            return terrenoDAO.getTerreno(celda.getTerrenoID(numCapa));
        }
    }

    @Override public short getTerrenoID (int tileX, int tileY, int numCapa)
    {
        Celda celda = getCelda(tileX, tileY);

        if (celda == null) return -1;
        else { return celda.getTerrenoID(numCapa); }
    }

    @Override public boolean setTerreno(int tileX, int tileY, int numCapa, TerrenoI terreno)
    {
        Celda celda = getCelda(tileX, tileY);

        if (celda == null) return false;
        else
        {
            if (celda.getTerreno(numCapa) != terreno)
            {
                celda.setTerreno(numCapa, terreno);
                DTOsMapa.SetTerreno cambioTerreno = new DTOsMapa.SetTerreno(tileX,tileY,numCapa,terreno.getID());
                notificarActualizacion("setTerreno", null, cambioTerreno);
                return true;
            }
            else return true;
        }
    }

    @Override public boolean setTerreno (int tileX, int tileY, int numCapa, short iDTerreno)
    {
        Celda celda = getCelda(tileX, tileY);

        if (celda == null) return false;
        else
        {
            if (celda.getTerrenoID(numCapa) != iDTerreno)
            {
                if (celda.setTerreno(numCapa, iDTerreno))
                {
                    DTOsMapa.SetTerreno cambioTerreno = new DTOsMapa.SetTerreno(tileX,tileY,numCapa,iDTerreno);
                    notificarActualizacion("setTerreno", null, cambioTerreno);
                    return true;
                }
                else return false;
            }
            else return true;
        }
    }

    private void desplazarArray (int incX, int incY)
    {
        if (incX > 0)   //Desplazar IZDA:
        {
            Celda[][] temp = new Celda[mapa.length][mapa[0].length];

            System.arraycopy(mapa, incX,                temp, 0,                    mapa.length-incX);
            System.arraycopy(mapa, 0,                   temp, temp.length-incX,     incX);
            mapa = temp;
        }

        if (incX < 0)   //Desplazar DCHA:
        {
            incX = -incX;
            Celda[][] temp = new Celda[mapa.length][mapa[0].length];

            System.arraycopy(mapa,  0,                  temp, incX,                 mapa.length-incX);
            System.arraycopy(mapa, temp.length-incX,    temp, 0,                    incX);
            mapa = temp;
        }

        if (incY > 0)   //Desplazar ABAJO:
        {
            for (int i=0; i< mapa.length; i++)
            {
                Celda[] tempo = new Celda[mapa[i].length];
                System.arraycopy(mapa[i], 0,                    tempo, incY,                mapa[i].length-incY);
                System.arraycopy(mapa[i], tempo.length-incY,    tempo, 0,                   incY);
                mapa[i]= tempo;
            }
        }

        if (incY < 0)   //Desplazar ARRIBA:
        {
            incY = -incY;
            for (int i=0; i< mapa.length; i++)
            {
                Celda[] tempo = new Celda[mapa[i].length];
                System.arraycopy(mapa[i], incY,                 tempo, 0,                   mapa[i].length-incY);
                System.arraycopy(mapa[i], 0,                    tempo, tempo.length-incY,   incY);
                mapa[i] = tempo;
            }
        }
    }

    public void comprobarCambioDeMapTile()
    {
        if (Math.abs(getMapTileX()-mapTileCentroX) >1 || Math.abs(getMapTileY()-mapTileCentroY) > 1)
        {   mapTileCentroX = getMapTileX();
            mapTileCentroY = getMapTileY();
            return;
        }

        if      (getMapTileX() > mapTileCentroX)    { desplazarArray( numTilesX,     0);        mapTileCentroX++; }
        else if (getMapTileX() < mapTileCentroX)    { desplazarArray(-numTilesX,     0);        mapTileCentroX--; }
        else if (getMapTileY() > mapTileCentroY)    { desplazarArray( 0,            -numTilesY);mapTileCentroY++; }
        else if (getMapTileY() < mapTileCentroY)    { desplazarArray( 0,             numTilesY);mapTileCentroY--; }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPlayer.Posicion)
        {   comprobarCambioDeMapTile(); }
    }
}
