package View.Classes.Geo;// Created by Hanto on 16/04/2014.

import DTOs.DTOsMapa;
import Interfaces.Geo.MapaI;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MapaView implements PropertyChangeListener, Disposable
{
    private MapaI mapaModel;

    private OrthographicCamera camara;
    private OrthographicCamera camaraMundoView;

    private SubMapaView[] listaSubMapas;

    private float xActual;                          //Coordenadas del centro de vision
    private float yActual;

    private int mapTileBordeE;                      //MapTile que hay justo a la derecha del limite de vision
    private int mapTileBordeO;                      //MapTile que hay justo a la izquierda del limite de vision
    private int mapTileBordeN;                      //MapTile que hay justo arriba del limite de vision
    private int mapTileBordeS;                      //MapTile que hay justo abajo del limtie de vision

    private int tamañoX;                            //numero de subMapas en el ejeX
    private int tamañoY;                            //numero de subMapas en el ejeY

    private int numTilesX;                          //numero de Tiles de ancho de cada submapa
    private int numTilesY;                          //numero de Tiles de alto de cada submapa

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public MapaView(MapaI mapaModel, OrthographicCamera camaraMundoView)
    {
        this.mapaModel = mapaModel;
        this.camaraMundoView = camaraMundoView;
        this.tamañoX = Settings.MAPAVIEW_TamañoX;
        this.tamañoY = Settings.MAPAVIEW_TamañoY;
        this.mapaModel.añadirObservador(this);

        this.numTilesX = (int)Math.ceil((double) Settings.MAPTILE_Horizontal_Resolution /(double)(tamañoX -1)/(double) Settings.TILESIZE);
        this.numTilesY = (int)Math.ceil((double) Settings.MAPTILE_Vertical_Resolution /(double)(tamañoY -1)/(double) Settings.TILESIZE);

        camara = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        listaSubMapas = new SubMapaView[tamañoX * tamañoY];
        for (int i=0; i<listaSubMapas.length;i++)
        {   listaSubMapas[i] = new SubMapaView(this.mapaModel, numTilesX, numTilesY); }

        setPosition(camara.position.x, camara.position.y);
    }

    @Override public void dispose()
    {
        for (SubMapaView subMapaView: listaSubMapas)
        {   subMapaView.dispose(); logger.trace("DISPOSE: Liberando SubMapaView(TiledMap) {}", subMapaView);}

    }

    //
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(float posX, float posY)
    {
        int mapTileInicialX = (int)(posX / (numTilesX * Settings.TILESIZE));
        int mapTileInicialY = (int)(posY / (numTilesY * Settings.TILESIZE));

        int xInicial, xFinal, yInicial;

        //tamañoX Impar:
        if (tamañoX %2 > 0)
        {   xInicial = -(tamañoX -1)/2;
            xFinal = (tamañoX -1)/2;
        }
        //tamañoX Par:
        else
        {   xInicial = -tamañoX /2;
            xFinal = tamañoX /2-1;
        }
        //TamañoY Par/Impar:
        if (tamañoY%2 >0) yInicial = (tamañoY -1)/2;
        else  yInicial = tamañoY /2-1;


        int x = xInicial; int y = yInicial;

        synchronized (listaSubMapas)
        {
            for (SubMapaView subMapaView : listaSubMapas)
            {
                subMapaView.crearTiledMap(x + mapTileInicialX, y + mapTileInicialY);
                x++; if (x > xFinal) { x = xInicial; y--; }
            }
        }
    }

    public void setView (SubMapaView subMapaView)
    {
        camara.zoom = camaraMundoView.zoom;
        camara.position.x = camaraMundoView.position.x - subMapaView.getMapTileX() * numTilesX * Settings.TILESIZE;
        camara.position.y = camaraMundoView.position.y - subMapaView.getMapTileY() * numTilesY * Settings.TILESIZE;
        camara.update();

        subMapaView.setView(camara);
    }

    public void render()
    {
        xActual = (camaraMundoView.position.x );
        yActual = (camaraMundoView.position.y );

        if (Math.abs(listaSubMapas[0].getMapTileX() -  (xActual /  (numTilesX * Settings.TILESIZE))) > tamañoX ||
            Math.abs(listaSubMapas[0].getMapTileY() -  (yActual /  (numTilesY * Settings.TILESIZE))) > tamañoY )
            setPosition(xActual, yActual);

        for (SubMapaView subMapaView: listaSubMapas)
        {
            mapaVistaLoader(subMapaView);
            setView(subMapaView);
            subMapaView.render();
        }
    }

    public void mapaVistaLoader(SubMapaView subMapaView)
    {
        mapTileBordeE = (int)(xActual + Settings.MAPTILE_Horizontal_Resolution /2) /  (numTilesX * Settings.TILESIZE);
        mapTileBordeO = (int)(xActual - Settings.MAPTILE_Horizontal_Resolution /2) /  (numTilesX * Settings.TILESIZE);
        mapTileBordeN = (int)(yActual + Settings.MAPTILE_Vertical_Resolution /2) /    (numTilesY * Settings.TILESIZE);
        mapTileBordeS = (int)(yActual - Settings.MAPTILE_Vertical_Resolution /2) /    (numTilesY * Settings.TILESIZE);

        if (mapTileBordeE >= (subMapaView.getMapTileX() + tamañoX))
        {   subMapaView.crearTiledMap(subMapaView.getMapTileX() + tamañoX,  subMapaView.getMapTileY()); }

        if (mapTileBordeO <= (subMapaView.getMapTileX() - tamañoX))
        {   subMapaView.crearTiledMap(subMapaView.getMapTileX() - tamañoX,  subMapaView.getMapTileY()); }

        if (mapTileBordeN >= (subMapaView.getMapTileY() + tamañoY))
        {   subMapaView.crearTiledMap(subMapaView.getMapTileX()         ,   subMapaView.getMapTileY() + tamañoY); }

        if (mapTileBordeS <= (subMapaView.getMapTileY() - tamañoY))
        {   subMapaView.crearTiledMap(subMapaView.getMapTileX()         ,   subMapaView.getMapTileY() - tamañoY); }
    }

    public void crearTile(int celdaX, int celdaY, int numCapa)
    {
        int mapTileX = celdaX/ numTilesX;
        int mapTileY = celdaY/ numTilesY;

        for (SubMapaView subMapaView: listaSubMapas)
        {
            if (subMapaView.getMapTileX() == mapTileX && subMapaView.getMapTileY() == mapTileY)
            {   subMapaView.crearTile(celdaX, celdaY, numCapa); }
        }
    }

    public void setTerreno(int x, int y, int numCapa)
    {
        crearTile(x - 1, y - 1, numCapa);
        crearTile(x - 1, y + 0, numCapa);
        crearTile(x - 1, y + 1, numCapa);
        crearTile(x + 0, y - 1, numCapa);
        crearTile(x + 0, y + 0, numCapa);
        crearTile(x + 0, y + 1, numCapa);
        crearTile(x + 1, y - 1, numCapa);
        crearTile(x + 1, y + 0, numCapa);
        crearTile(x + 1, y + 1, numCapa);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsMapa.SetTerreno)
        {   setTerreno(
                ((DTOsMapa.SetTerreno) evt.getNewValue()).tileX,
                ((DTOsMapa.SetTerreno) evt.getNewValue()).tileY,
                ((DTOsMapa.SetTerreno) evt.getNewValue()).numCapa);

        }
    }
}
