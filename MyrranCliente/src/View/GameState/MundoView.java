package View.GameState;// Created by Hanto on 14/05/2014.

import DB.RSC;
import DTOs.DTOsMundo;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.Classes.Geo.Mapa;
import Model.Classes.Tweens.CamaraTween;
import Model.Classes.Tweens.TweenEng;
import Model.EstructurasDatos.ListaMapa;
import Model.GameState.Mundo;
import Model.Settings;
import View.Classes.Actores.Particula;
import View.Classes.Geo.MapaView;
import View.Classes.Mobiles.MobView.MobView;
import View.Classes.Mobiles.MobView.MobViewFactory;
import View.Classes.Mobiles.PCView.PCView;
import View.Classes.Mobiles.PCView.PCViewFactory;
import View.Classes.Mobiles.PlayerView.PlayerView;
import View.Classes.Mobiles.PlayerView.PlayerViewFactory;
import View.Classes.Mobiles.ProyectilView.ProyectilView;
import View.Classes.Mobiles.ProyectilView.ProyectilViewFactory;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;

import static Model.Settings.PIXEL_METROS;

public class MundoView extends Stage implements PropertyChangeListener
{
    //Controlador, Model:
    protected Mundo mundo;

    //View:
    protected PlayerView playerView;
    protected MapaView mapaView;
    protected ListaMapa<PCView> listaPCViews = new ListaMapa<>();
    protected ListaMapa<ProyectilView> listaProyectilViews = new ListaMapa<>();
    protected ListaMapa<MobView> listaMobsViews = new ListaMapa<>();
    private OrdenarPorProfundidad ordenarPorProfundidad;

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

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public MundoView(PlayerI player, Mundo mundo, MapaView mapaView,
                     OrthographicCamera camara, OrthographicCamera boxCamara, RayHandler rayHandler)
    {
        RayHandler.useDiffuseLight(true);
        RayHandler.setGammaCorrection(false);
        this.rayHandler = rayHandler;
        this.rayHandler.setAmbientLight(0.6f, 0.6f, 0.6f, 1.0f);
        this.camara = camara;
        this.boxCamara = boxCamara;
        this.mundo = mundo;
        this.mapaView = mapaView;

        this.ordenarPorProfundidad = new OrdenarPorProfundidad();

        añadirPlayerView(player);
        getViewport().setCamera(camara);

        mundo.añadirObservador(this);

        //particleCursor();
    }

    @Override public void dispose ()
    {
        mundo.eliminarObservador(this);
        super.dispose();

        logger.trace("DISPOSE: Liberando PlayerView");
        playerView.dispose();
        logger.trace("DISPOSE: Liberando SpriteBatch");
        batch.dispose();
        logger.trace("DISPOSE: Liberando RayHandler");
        rayHandler.dispose();
        logger.trace("DISPOSE: Liberando ShapeRenderer");
        shape.dispose();
        logger.trace("DISPOSE: Liberando mapaView");
        mapaView.dispose();
    }

    // PLAYER
    //------------------------------------------------------------------------------------------------------------------

    public void añadirPlayerView(PlayerI player)
    {
        playerView = PlayerViewFactory.ILUMINADO.nuevo(player, rayHandler);
        this.addActor(playerView);
    }

    // PC
    //------------------------------------------------------------------------------------------------------------------

    public void añadirPCView (PCI pc)
    {
        PCView pcView = PCViewFactory.SINLUZ.nuevo(pc);
        listaPCViews.add(pcView);
        this.addActor(pcView);
    }

    public void eliminarPCView (int iD)
    {
        PCView pcView = listaPCViews.remove(iD);
        this.getRoot().removeActor(pcView);
        pcView.dispose();
    }

    // PROYECTIL
    //------------------------------------------------------------------------------------------------------------------

    public void añadirProyectilView (ProyectilI proyectil)
    {
        ProyectilView proyectilView = ProyectilViewFactory.ILUMINADO.nuevo(proyectil, rayHandler);
        listaProyectilViews.add(proyectilView);
        this.addActor(proyectilView);
    }

    public void eliminarProyectilView (int iD)
    {
        ProyectilView proyectilView = listaProyectilViews.remove(iD);
        this.getRoot().removeActor(proyectilView);
        proyectilView.dispose();
    }

    // MOB
    //------------------------------------------------------------------------------------------------------------------

    public void añadirMobView (MobI mob)
    {
        MobView mobView = MobViewFactory.MOBVIEW.nuevo(mob);
        listaMobsViews.add(mobView);
        this.addActor(mobView);
    }

    public void eliminarMobView (int iD)
    {
        MobView mobView = listaMobsViews.remove(iD);
        this.getRoot().removeActor(mobView);
        mobView.dispose();
    }

    // RENDER:
    //------------------------------------------------------------------------------------------------------------------

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
        //batch.setProjectionMatrix(camara.combined);
        //batch.begin();
        //batch.end();

        //ordenadomos los actores:
        //getActors().sort(ordenarPorProfundidad);

        //dibujamos los sprites:
        super.draw();

        //aplicamos las luces:
        rayHandler.setCombinedMatrix(boxCamara);
        rayHandler.updateAndRender();

        //dibujamos la geometrica fisica de DEBUG:
        //worldRender.render(getWorld(), boxCamara.combined);

        //dibujamos las lineas de debug:
        dibujarVision();
    }

    public void resize (int anchura, int altura)
    {
        camara.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void aplicarZoom(int incrementoZoom)
    {
        nivelDeZoom += incrementoZoom;

        float zoom = 1f;
        if (nivelDeZoom < 0) zoom = 1f/(Math.abs(nivelDeZoom)+1f);
        if (nivelDeZoom ==0) zoom = 1f;
        if (nivelDeZoom > 0) zoom = 1f+ nivelDeZoom *0.2f;

        Tween.to(camara, CamaraTween.ZOOM, 0.4f).target(zoom).ease(TweenEquations.easeOutBounce).start(TweenEng.getTweenManager());
        Tween.to(boxCamara, CamaraTween.ZOOM, 0.4f).target(zoom).ease(TweenEquations.easeOutBounce).start(TweenEng.getTweenManager());
    }

    //CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        // PC:
        if (evt.getNewValue() instanceof DTOsMundo.AñadirPC)
        {   añadirPCView(((DTOsMundo.AñadirPC) evt.getNewValue()).pc); }

        else if (evt.getNewValue() instanceof DTOsMundo.EliminarPC)
        {   eliminarPCView(((DTOsMundo.EliminarPC) evt.getNewValue()).pc.getID());}

        // PROYECTIL
        else if (evt.getNewValue() instanceof DTOsMundo.AñadirProyectil)
        {   añadirProyectilView(((DTOsMundo.AñadirProyectil) evt.getNewValue()).proyectil);}

        else if (evt.getNewValue() instanceof DTOsMundo.EliminarProyectil)
        {   eliminarProyectilView(((DTOsMundo.EliminarProyectil) evt.getNewValue()).proyectil.getID());}

        // MOB:
        else if (evt.getNewValue() instanceof DTOsMundo.AñadirMob)
        {   añadirMobView(((DTOsMundo.AñadirMob) evt.getNewValue()).mob); }

        else if (evt.getNewValue() instanceof DTOsMundo.EliminarMob)
        {   eliminarMobView(((DTOsMundo.EliminarMob) evt.getNewValue()).mob.getID());}
    }

    // ALGORITMO ORDENACION ACTORES:
    //------------------------------------------------------------------------------------------------------------------

    public class OrdenarPorProfundidad implements Comparator<Actor>
    {
        @Override public int compare(Actor o1, Actor o2)
        {
            float o1Y, o2Y;
            o1Y = o1.getY();
            o2Y = o2.getY();
            /*if (o1 instanceof Muro && ((Muro)o1).perspectiva < 0 )
            { o1Y = Muro.distanciaPerspectiva-((Muro)o1).muroTecho.getY(); }
            if (o2 instanceof Muro && ((Muro)o2).perspectiva < 0 )
            { o2Y = Muro.distanciaPerspectiva-((Muro)o2).muroTecho.getY(); }*/
            return (o1Y < o2Y ? 1 : (o1Y == o2Y ? 1 : -1));
        }
    }

    // CODIGO DEBUG:
    //------------------------------------------------------------------------------------------------------------------

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
                    int mapTileX = x-1 + ((Mapa)mundo.getMapa()).mapTileCentroX;
                    int mapTileY = 1-y + ((Mapa)mundo.getMapa()).mapTileCentroY;

                    shape.rect( mapTileX* Settings.MAPTILE_NumTilesX * Settings.TILESIZE, mapTileY* Settings.MAPTILE_NumTilesY * Settings.TILESIZE,
                            Settings.MAPTILE_NumTilesX * Settings.TILESIZE, Settings.MAPTILE_NumTilesY * Settings.TILESIZE);
                }
            }
        }
        shape.end();
    }

    private void particleCursor()
    {
        RSC.particulaRecursoDAO.getParticulaRecursosDAO().crearPool("prueba", 10, 10);
        Particula par = RSC.particulaRecursoDAO.getParticulaRecursosDAO().obtain("prueba");
        par.setScale(0.1f);
        par.camara = getCamara();
        par.luz = new PointLight(getRayHandler(), 300, new Color(0.7f,0.3f,0.3f,0.7f), 350 *PIXEL_METROS, 0, 0);
        par.luz.setSoft(true);

        this.addActor(par);
    }
}
