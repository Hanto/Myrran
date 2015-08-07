package View.Gamestate.CampoVision;// Created by Hanto on 20/05/2014.

import Controller.Controlador;
import DTO.DTOsMapView;
import DTO.DTOsMapa;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.GameState.MundoI;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MapaView implements PropertyChangeListener, Disposable
{
    private Espacial espacial;
    private int connectionID;

    private MundoI mundo;
    private Controlador controlador;
    private CampoVision campoVision;

    private int mapTileCentroX;
    private int mapTileCentroY;
    private int posicionHoritontal = 0;
    private int posicionVertical = 0;

    private boolean[][] mapaEnviado = new boolean[3][3];

    private final int reborde = 1;
    private final int posHorNeg = Settings.MAPTILE_Horizontal_Resolution /4;
    private final int posHorPos = Settings.MAPTILE_Horizontal_Resolution - Settings.MAPTILE_Horizontal_Resolution /4;
    private final int posVerNeg = Settings.MAPTILE_Vertical_Resolution /4;
    private final int posVerPos = Settings.MAPTILE_Vertical_Resolution - Settings.MAPTILE_Vertical_Resolution /4;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public MapaView(Espacial espacial, int connectionID, MundoI mundo, Controlador controlador, CampoVision campoVision)
    {
        this.espacial = espacial;
        this.connectionID = connectionID;
        this.mundo = mundo;
        this.controlador = controlador;
        this.campoVision = campoVision;

        mundo.getMapa().añadirObservador(this);
    }

    @Override public void dispose()
    {   mundo.getMapa().eliminarObservador(this); }

    //
    //------------------------------------------------------------------------------------------------------------
    private void resetearEnviados()
    {
        for (boolean[] fila : mapaEnviado)
        {   for (int i=0; i<fila.length; i++)
                fila[i] = false;
        }
        mapTileCentroX = espacial.getMapTileX();
        mapTileCentroY = espacial.getMapTileY();
    }

    private boolean getMapaEnviado(int offSetX, int offSetY)        { return mapaEnviado[offSetX+1][-offSetY+1]; }
    private void setMapaEnviado(int offSetX, int offSetY, boolean b){ mapaEnviado[offSetX+1][-offSetY+1] = b; }

    private void enviarMapTilesAdyancentes()
    {
        DTOsMapView.MapTilesAdyacentes maptilesAdyacentes = new DTOsMapView.MapTilesAdyacentes(mapaEnviado);
        controlador.enviarACliente(connectionID, maptilesAdyacentes);
    }

    public void comprobarVistaMapa ()
    {   //Si las adyacencias tienen mas de uno de distancia, hay que resetearlo tod o para volverlo a enviar:
        if (Math.abs(espacial.getMapTileX()-mapTileCentroX) >1 || Math.abs(espacial.getMapTileY()-mapTileCentroY) > 1)  { resetearEnviados(); return; }

        int distX = (int) espacial.getX() - mapTileCentroX * Settings.MAPTILE_NumTilesX * Settings.TILESIZE;
        int distY = (int) espacial.getY() - mapTileCentroY * Settings.MAPTILE_NumTilesY * Settings.TILESIZE;

        if (distX < posHorNeg)      { posicionHoritontal = -1; }
        else if (distX > posHorPos) { posicionHoritontal = +1; }
        else                        { posicionHoritontal =  0; }

        if (distY < posVerNeg)      { posicionVertical = -1; }
        else if (distY > posVerPos) { posicionVertical = +1; }
        else                        { posicionVertical =  0; }

        if (posicionVertical != 0 && posicionHoritontal != 0)
        {   //Si estamso en una esquina, mandamos las adyacencias de la esquina:
            actualizarMapa(0, 0);
            actualizarMapa(posicionHoritontal, 0);
            actualizarMapa(0, posicionVertical);
            actualizarMapa(posicionHoritontal, posicionVertical);
        }
        if (posicionVertical == 0 && posicionHoritontal != 0)
        {   //Si estamos en el lateral, enviamos las dos esquina de ese lado
            actualizarMapa(0,  0);
            actualizarMapa(0, +1);
            actualizarMapa(0, -1);
            actualizarMapa(posicionHoritontal, +1);
            actualizarMapa(posicionHoritontal,  0);
            actualizarMapa(posicionHoritontal, -1);
        }
        if (posicionHoritontal == 0 && posicionVertical != 0)
        {   //Si estamos en el Lateral superior o inferior, enviarmos las dos esquinas de ese lado
            actualizarMapa( 0, 0);
            actualizarMapa(+1, 0);
            actualizarMapa(-1, 0);
            actualizarMapa(+1, posicionVertical);
            actualizarMapa( 0, posicionVertical);
            actualizarMapa(-1, posicionVertical);
        }
        if (posicionHoritontal == 0 && posicionVertical == 0)
        {   //Si estamos en el centro enviamos todas las esquinas
            actualizarMapa( 0,  0);
            actualizarMapa(+1, +1);
            actualizarMapa(+1,  0);
            actualizarMapa(+1, -1);
            actualizarMapa( 0, +1);
            actualizarMapa( 0, -1);
            actualizarMapa(-1, +1);
            actualizarMapa(-1,  0);
            actualizarMapa(-1, -1);
        }

        if      (espacial.getMapTileX() > mapTileCentroX)   { incrementarMapTile(1, 0); }
        else if (espacial.getMapTileX() < mapTileCentroX)   { incrementarMapTile(-1, 0); }
        else if (espacial.getMapTileY() > mapTileCentroY)   { incrementarMapTile(0, 1);  }
        else if (espacial.getMapTileY() < mapTileCentroY)   { incrementarMapTile(0, -1); }
    }

    private void actualizarMapa(int x, int y)
    {   //Convierte las referencias relativas al centro 0,0 en coordenadas de acceso a la matriz donde estan los datos
        if (!getMapaEnviado(x, y))
        {
            setMapaEnviado(x, y, true);
            enviarMapa(mapTileCentroX + x, mapTileCentroY + y);
            enviarMapTilesAdyancentes();
        }
    }

    private void incrementarMapTile (int incX, int incY)
    {
        desplazarArray(incX, incY);

        if (incX >0)
        {
            mapaEnviado[2][0] = false;
            mapaEnviado[2][1] = false;
            mapaEnviado[2][2] = false;
        }
        if (incX <0)
        {
            mapaEnviado[0][0] = false;
            mapaEnviado[0][1] = false;
            mapaEnviado[0][2] = false;
        }
        if (incY >0)
        {
            mapaEnviado[0][0] = false;
            mapaEnviado[1][0] = false;
            mapaEnviado[2][0] = false;
        }
        if (incY <0)
        {
            mapaEnviado[0][2] = false;
            mapaEnviado[1][2] = false;
            mapaEnviado[2][2] = false;
        }

        mapTileCentroX += incX;
        mapTileCentroY += incY;

        enviarMapTilesAdyancentes();
    }

    private void desplazarArray (int incX, int incY)
    {
        boolean[] temp;
        boolean tempo;

        if (incX > 0)
        {
            temp = mapaEnviado[0];
            System.arraycopy(mapaEnviado, 1, mapaEnviado, 0, mapaEnviado.length-1);
            mapaEnviado[mapaEnviado.length-1] = temp;

        }
        if (incX < 0)
        {
            temp = mapaEnviado[mapaEnviado.length-1];
            System.arraycopy(mapaEnviado, 0, mapaEnviado, 1, mapaEnviado.length-1);
            mapaEnviado[0] = temp;
        }
        if (incY < 0)
        {
            for (int i=0; i< mapaEnviado.length; i++)
            {
                tempo = mapaEnviado[i][0];
                System.arraycopy(mapaEnviado[i], 1, mapaEnviado[i], 0, mapaEnviado[i].length - 1);
                mapaEnviado[i][mapaEnviado.length-1] = tempo;
            }
        }
        if (incY > 0)
        {
            for (int i=0; i< mapaEnviado.length; i++)
            {
                tempo = mapaEnviado[i][mapaEnviado.length-1];
                System.arraycopy(mapaEnviado[i], 0, mapaEnviado[i], 1, mapaEnviado[i].length - 1);
                mapaEnviado[i][0] = tempo;
            }
        }
    }

    private void enviarMapa(int mapTileInicialX, int mapTileInicialY)
    {
        logger.debug("ENVIAR: "+ connectionID+" MapTile: ["+mapTileInicialX+" "+mapTileInicialY+"]");
        if (mapTileInicialX <0 || mapTileInicialY < 0) { return; }

        int ancho = Settings.MAPTILE_NumTilesX + reborde*2;
        int alto = Settings.MAPTILE_NumTilesY + reborde*2;

        int esquinaInfIzdaX = mapTileInicialX * Settings.MAPTILE_NumTilesX - reborde;
        int esquinaInfIzdaY = mapTileInicialY * Settings.MAPTILE_NumTilesY - reborde;

        DTOsMapView.Mapa actualizarMapa = new DTOsMapView.Mapa(esquinaInfIzdaX, esquinaInfIzdaY, ancho, alto);
        for (int x=0; x< ancho; x++)
        {
            for (int y = 0; y< alto; y++)
            {
                for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
                {   actualizarMapa.mapa[x][y].celda[i] = mundo.getMapa().getTerrenoID(x +esquinaInfIzdaX, y +esquinaInfIzdaY, i); }
            }
        }
        controlador.enviarACliente(connectionID, actualizarMapa);
    }

    public void cambioTerreno (int tileX, int tileY, int numCapa, short iDTerreno)
    {   //Solo se notifican por cambios los terrenos adyacentes que han sido enviados:
        int offsetX = tileX/ Settings.MAPTILE_NumTilesX - mapTileCentroX;
        int offsetY = tileY/ Settings.MAPTILE_NumTilesY - mapTileCentroY;

        if ( Math.abs(offsetX) <= 1 && Math.abs(offsetY) <= 1)
        {
            if (getMapaEnviado(offsetX, offsetY))
            {
                campoVision.buffer.addCambioTerreno(tileX, tileY, numCapa, iDTerreno);
                logger.trace("Editando SetTerreno: ["+tileX+"]["+tileY+"]");
            }
        }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        //TERRENOS:
        if (evt.getNewValue() instanceof DTOsMapa.SetTerreno)
        {
            cambioTerreno(
                ((DTOsMapa.SetTerreno) evt.getNewValue()).tileX,
                ((DTOsMapa.SetTerreno) evt.getNewValue()).tileY,
                ((DTOsMapa.SetTerreno) evt.getNewValue()).numCapa,
                ((DTOsMapa.SetTerreno) evt.getNewValue()).iDTerreno);
        }
    }
}
