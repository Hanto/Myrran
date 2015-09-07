package View.Classes.UI.BarraTerrenosView;// Created by Hanto on 14/05/2014.

import DB.RSC;
import DTOs.DTOsBarraTerrenos;
import Interfaces.Controlador.ControladorBarraTerrenosI;
import Model.Classes.UI.BarraTerrenos;
import Model.Settings;
import View.Classes.Actores.Texto;
import View.Classes.UI.BarraTerrenosView.TerrenoView.TerrenoView;
import View.Classes.UI.Ventana.Ventana;
import View.Classes.UI.Ventana.VentanaMoverListener;
import View.Classes.UI.Ventana.VentanaResizeListener;
import View.Classes.UI.Ventana.VentanaScrollListener;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BarraTerrenosView extends Group implements PropertyChangeListener, Ventana, Disposable
{
    protected ControladorBarraTerrenosI controlador;
    protected Stage stage;
    protected BarraTerrenos barraTerrenos;

    protected Table tablaTerrenos = new Table().top().left();
    protected ScrollPane scrollPane;
    protected Array<TerrenoView> barraIconos;

    protected DragAndDrop dad;

    private Texto[] botonCapas = new Texto[Settings.MAPA_Max_Capas_Terreno];
    protected Image moverBarra;
    protected Image redimensionarBarra;
    protected Image botonBorrarTerreno;

    private float oldX;
    private float oldY;

    protected int numFilas = 4;
    protected int numColumnas =2;

    @Override public float getAnchoElemento()           { return Settings.TILESIZE*2; }
    @Override public float getAltoElemento()            { return Settings.TILESIZE*2; }

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public BarraTerrenosView (ControladorBarraTerrenosI controlador, final BarraTerrenos barraTerrenos, Stage stage)
    {
        this.controlador = controlador;
        this.barraTerrenos = barraTerrenos;
        this.stage = stage;

        scrollPane = new ScrollPane(tablaTerrenos);
        this.addActor(scrollPane);

        moverBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        moverBarra.addListener(new VentanaMoverListener(moverBarra, this));
        this.addActor(moverBarra);

        redimensionarBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        redimensionarBarra.addListener(new VentanaResizeListener(redimensionarBarra, this, this));
        this.addActor(redimensionarBarra);

        botonBorrarTerreno = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRATERRENOS_Borrar_Terreno));
        botonBorrarTerreno.setPosition(0, -botonBorrarTerreno.getHeight());
        this.addActor(botonBorrarTerreno);
        botonBorrarTerreno.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                barraTerrenos.setParametroTerrenoID((short) -1);
                return true;
            }
        });

        scrollPane.clearListeners();
        scrollPane.addListener(new VentanaScrollListener(scrollPane));

        crearBarraIconos();
        crearBotonCapas();

        setPosition(-getWidth() - redimensionarBarra.getWidth(), 500);
        oldX = moverBarra.getWidth();
        oldY = 500;

        barraTerrenos.añadirObservador(this);
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE: Liberando BarraTerrenosView");
        barraTerrenos.eliminarObservador(this);
    }

    private void crearBarraIconos()
    {
        barraIconos = new Array<>();
        dad = new DragAndDrop();
        dad.setDragTime(0);

        for (int x=0; x< barraTerrenos.getTamaño(); x++)
        {   barraIconos.add(crearIcono(x)); }

        recrearTabla();
    }

    private TerrenoView crearIcono(final int posX)
    {
        TerrenoView icono = new TerrenoView(barraTerrenos, posX);
        icono.addDragAndDrop(dad, controlador);
        return icono;
    }

    private void crearBotonCapas()
    {
        for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
        {
            final int numCapa = i;
            botonCapas[i] = new Texto("Capa "+numCapa, RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 2);
            botonCapas[i].setPosition(4, -Settings.TILESIZE*2-18-numCapa*17);
            this.addActor(botonCapas[i]);

            botonCapas[i].addListener(new InputListener()
            {
                @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
                {
                    for (int j=0; j<botonCapas.length; j++)
                    {   botonCapas[j].setColorNormal(Color.ORANGE); }
                    botonCapas[numCapa].setColorNormal(Color.GREEN);
                    barraTerrenos.setParametroNumCapa(numCapa);
                    return true;
                }
            });
        }
    }

    public void recrearTabla()
    {   //La tabla es la Vista, lo que se ve, cada vez que se redimensiona el ancho y alto de la zona de visualizacion hay que recrearla
        tablaTerrenos.clear();

        int columna = 0;

        for (TerrenoView icono: barraIconos)
        {
            columna++;
            tablaTerrenos.add(icono.getApariencia()).left().height(icono.getApariencia().getHeight()).width(icono.getApariencia().getWidth());
            if (columna%numColumnas == 0) tablaTerrenos.row();
        }

        scrollPane.setBounds(0,0, numColumnas* Settings.TILESIZE*2, numFilas* Settings.TILESIZE*2);

        float esquinaSupIzda = getY() + getHeight();

        this.setWidth(numColumnas* Settings.TILESIZE*2);
        this.setHeight(numFilas* Settings.TILESIZE*2);

        setY(esquinaSupIzda - getHeight());

        moverBarra.setPosition(-moverBarra.getWidth()-2,this.getHeight()-moverBarra.getHeight());
        redimensionarBarra.setPosition(this.getWidth(),-redimensionarBarra.getHeight());
    }

    @Override public void eventoVentanaResize(int columnasAdicionales, int filasAdicionales)
    {
        if (this.getStage() != null)
        {
            numColumnas += columnasAdicionales;
            numFilas += filasAdicionales;
            recrearTabla();
        }
    }

    public void mostrarBarraTerrenos()
    {
        this.clearActions();
        stage.addActor(this);
        this.addAction(Actions.moveTo(oldX, oldY, 0.2f, Interpolation.sine));
    }

    public void ocultarBarraTerrenos()
    {
        if (this.getActions().size == 0)
        {
            oldX = getX();
            oldY = getY();
        }
        else clearActions();
        addAction(Actions.sequence(Actions.moveTo(0-getWidth()-redimensionarBarra.getWidth(), getY(), 0.5f, Interpolation.sine),
                                   Actions.removeActor()));
    }

    public void actualizarApariencia(TerrenoView icono)
    {   icono.actualizarApariencia(); }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsBarraTerrenos.SetTerreno)
        {
            int posX = ((DTOsBarraTerrenos.SetTerreno) evt.getNewValue()).posX;
            actualizarApariencia(barraIconos.get(posX));
        }

        if (evt.getNewValue() instanceof DTOsBarraTerrenos.CrearBarraTerreno)
        {   crearBarraIconos(); }
    }
}
