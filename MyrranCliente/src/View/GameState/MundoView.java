package View.GameState;// Created by Hanto on 14/05/2014.

import Controller.Controlador;
import DTO.NetDTO;
import Data.Settings;
import Model.Classes.Mobiles.PC;
import Model.Classes.Mobiles.Player;
import Model.GameState.Mundo;
import View.Classes.Geo.MapaView;
import View.Classes.Mobiles.PCView;
import View.Classes.Mobiles.PlayerView;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static Data.Settings.PIXEL_METROS;

public class MundoView extends Stage implements PropertyChangeListener
{
    //Controlador, Model:
    protected Controlador controlador;
    protected Mundo mundo;

    //View:
    protected PlayerView playerView;
    protected MapaView mapaView;
    protected Array<PCView> listaPCViews = new Array<>();

    //LibGDX Tools:
    protected SpriteBatch batch = new SpriteBatch();
    protected RayHandler rayHandler;
    protected ShapeRenderer shape = new ShapeRenderer();
    protected int nivelDeZoom = 0;

    protected OrthographicCamera camara;
    protected OrthographicCamera boxCamara;
    protected Box2DDebugRenderer worldRender = new Box2DDebugRenderer();

    public PlayerView getPlayerView()                   { return playerView; }
    public MapaView getMapaView()                       { return mapaView; }
    public OrthographicCamera getCamara()               { return camara; }
    public RayHandler getRayHandler()                   { return rayHandler; }
    public World getWorld()                             { return mundo.getWorld(); }

    public void eliminarPCView (PCView pcView)
    {   listaPCViews.removeValue(pcView, true); }

    public MundoView(Controlador controlador, Player player, Mundo mundo)
    {
        this.controlador = controlador;
        this.mundo = mundo;

        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(mundo.getWorld());
        rayHandler.setAmbientLight(0.4f, 0.4f, 0.4f, 1.0f);
        mapaView = new MapaView(mundo.getMapa(), this, player.getX(), player.getY(), Settings.MAPAVIEW_TamañoX, Settings.MAPAVIEW_TamañoY);
        playerView = new PlayerView(player, this, controlador);
        camara = new OrthographicCamera (Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        boxCamara = new OrthographicCamera(Gdx.graphics.getWidth() * PIXEL_METROS, Gdx.graphics.getHeight() * PIXEL_METROS);

        getViewport().setCamera(camara);

        //controlador.addInputProcessor(this);
        mundo.añadirObservador(this);
    }

    @Override public void draw ()
    {
        //actualizamos las camaras:
        camara.position.x = getPlayerView().getCenterX();
        camara.position.y = getPlayerView().getCenterY();
        camara.update();

        boxCamara.position.x = camara.position.x * PIXEL_METROS;
        boxCamara.position.y = camara.position.y * PIXEL_METROS;
        boxCamara.update();

        //dibujamos el fondo:
        mapaView.render();

        //dibujamos los sprites a manopla:
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.end();

        //dibujamos los sprites:
        super.draw();

        //aplicamos las luces:
        rayHandler.setCombinedMatrix(boxCamara.combined);
        rayHandler.updateAndRender();

        //dibujamos la geometrica fisica de Debug:
        worldRender.render(getWorld(), boxCamara.combined);

        //dibujamos las lineas de debug:
        dibujarVision();
    }

    public void resize (int anchura, int altura)
    {
        camara.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override public void dispose ()
    {
        super.dispose();
        batch.dispose();
        mapaView.dispose();
        rayHandler.dispose();
        shape.dispose();
    }

    public void aplicarZoom(int incrementoZoom)
    {
        nivelDeZoom += incrementoZoom;

        float zoom = 1f;
        if (nivelDeZoom < 0) zoom = 1f/(Math.abs(nivelDeZoom)+1f);
        if (nivelDeZoom ==0) zoom = 1f;
        if (nivelDeZoom > 0) zoom = 1f+ nivelDeZoom *0.2f;
        camara.zoom = zoom;
        boxCamara.zoom = zoom;
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof NetDTO.AñadirPPC)
        {
            PC pc = mundo.getPC(((NetDTO.AñadirPPC) evt.getNewValue()).connectionID);

            pc.eliminarObservador(this);
            PCView pcView = new PCView(pc, this, controlador);
            listaPCViews.add(pcView);
        }
    }


    public void dibujarVision()
    {
        shape.setProjectionMatrix(camara.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //Dibujar Resolucion
        shape.setColor(new Color(1,1,1, 0.5f));
        shape.rect((int)(getPlayerView().getCenterX()- Settings.GDX_Horizontal_Resolution /2-1),
                   (int)(getPlayerView().getCenterY()- Settings.GDX_Vertical_Resolution /2-1),
                   Settings.GDX_Horizontal_Resolution +2,
                   Settings.GDX_Vertical_Resolution +2);

        //Dibujar alcance Vista Virtual:
        shape.setColor(Color.RED);
        shape.rect((int)(getPlayerView().getCenterX()- Settings.MAPTILE_Horizontal_Resolution /2-1),
                   (int)(getPlayerView().getCenterY()- Settings.MAPTILE_Vertical_Resolution /2-1),
                   Settings.MAPTILE_Horizontal_Resolution +2,
                   Settings.MAPTILE_Vertical_Resolution +2);


        //Dibujar alcance Mobs:
        shape.setColor(Color.GREEN);
        shape.rect((int)(getPlayerView().getCenterX()- Settings.MAPTILE_Horizontal_Resolution * Settings.NETWORK_DistanciaVisionMobs /2),
                   (int)(getPlayerView().getCenterY()- Settings.MAPTILE_Vertical_Resolution * Settings.NETWORK_DistanciaVisionMobs /2),
                   Settings.MAPTILE_Horizontal_Resolution * Settings.NETWORK_DistanciaVisionMobs,
                   Settings.MAPTILE_Vertical_Resolution * Settings.NETWORK_DistanciaVisionMobs);

        //Dibujar Limites Mapa:
        shape.setColor(Color.WHITE);
        shape.rect( 0, 0, Settings.MAPA_Max_TilesX* Settings.TILESIZE, Settings.MAPA_Max_TilesY* Settings.TILESIZE);

        //Dibujar limites Celda:
        shape.setColor(Color.GRAY);
        for (int i=1; i<= Settings.MAPA_Max_TilesX/ Settings.MAPTILE_NumTilesX; i++)
        {
            shape.line(i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE, 0,
                       i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE, Settings.MAPA_Max_TilesY* Settings.TILESIZE);
        }
        for (int i=1; i<= Settings.MAPA_Max_TilesY/ Settings.MAPTILE_NumTilesY; i++)
        {
            shape.line(0, i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE,
                       Settings.MAPA_Max_TilesX* Settings.TILESIZE, i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE);
        }

        //Dibujar Subsectores Celda:
        shape.setColor(Color.DARK_GRAY);
        for (int i=0; i<= Settings.MAPA_Max_TilesX/ Settings.MAPTILE_NumTilesX; i++)
        {
            shape.line(i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE+ Settings.MAPTILE_posHorNeg, 0,
                       i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE+ Settings.MAPTILE_posHorNeg, Settings.MAPA_Max_TilesY* Settings.TILESIZE);
            shape.line(i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE+ Settings.MAPTILE_posHorPos, 0,
                       i* Settings.MAPTILE_NumTilesX * Settings.TILESIZE+ Settings.MAPTILE_posHorPos, Settings.MAPA_Max_TilesY* Settings.TILESIZE);
        }

        for (int i=0; i<= Settings.MAPA_Max_TilesY/ Settings.MAPTILE_NumTilesY; i++)
        {
            shape.line(0, Settings.MAPTILE_posVerNeg+i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE,
                       Settings.MAPA_Max_TilesX* Settings.TILESIZE,  Settings.MAPTILE_posVerNeg+i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE);
            shape.line(0, Settings.MAPTILE_posVerPos+i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE,
                       Settings.MAPA_Max_TilesX* Settings.TILESIZE,  Settings.MAPTILE_posVerPos+i* Settings.MAPTILE_NumTilesY * Settings.TILESIZE);

        }

        //Dibuar MapTiles Adyacentes Cargados:
        shape.setColor(Color.ORANGE);
        for (int y=0; y<3; y++)
        {
            for (int x=0; x<3; x++)
            {
                if (mundo.mapTilesCargados[x][y] == true)
                {
                    int mapTileX = x-1 + mundo.getMapa().mapTileCentroX;
                    int mapTileY = 1-y + mundo.getMapa().mapTileCentroY;

                    shape.rect( mapTileX* Settings.MAPTILE_NumTilesX * Settings.TILESIZE, mapTileY* Settings.MAPTILE_NumTilesY * Settings.TILESIZE,
                            Settings.MAPTILE_NumTilesX * Settings.TILESIZE, Settings.MAPTILE_NumTilesY * Settings.TILESIZE);
                }
            }
        }
        shape.end();
    }
}
