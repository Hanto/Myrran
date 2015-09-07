package View.Classes.UI.BarraAccionesView;// Created by Hanto on 06/05/2014.

import DB.RSC;
import DTOs.DTOsBarraAcciones;
import Interfaces.EntidadesPropiedades.Misc.IDentificable;
import Model.Settings;
import Interfaces.Misc.UI.BarraAccionesI;
import Interfaces.Misc.Controlador.ControladorBarraAccionI;
import View.Classes.UI.BarraAccionesView.CasillaView.CasillaView;
import View.Classes.UI.Ventana.Ventana;
import View.Classes.UI.Ventana.VentanaMoverListener;
import View.Classes.UI.Ventana.VentanaResizeListener;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BarraAccionesView extends Table implements PropertyChangeListener, IDentificable, Ventana, Disposable
{
    private int iD;
    private BarraAccionesI barraModel;
    private Stage stage;
    private DragAndDrop dad;
    private ConjuntoBarraAccionesView conjuntoBarraAccionesView;
    private ControladorBarraAccionI controlador;

    private Image moverBarra;
    private Image redimensionarBarra;
    private Image eliminarBarra;

    private Array<Array<CasillaView>> barraIconos = new Array<>();

    public BarraAccionesI getModel()                        { return barraModel; }
    public float getEsquinaSupIzdaX()                       { return this.getX(); }
    public float getEsquinaSupIzdaY()                       { return this.getY() + this.getHeight(); }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                            { return iD; }
    @Override public void setID(int iD)                     { this.iD = iD;}

    // VENTANA:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getAnchoElemento()               { return Settings.BARRASPELLS_Ancho_Casilla; }
    @Override public float getAltoElemento()                { return Settings.BARRASPELLS_Alto_Casilla; }

    protected Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public BarraAccionesView(BarraAccionesI barraAcciones, ConjuntoBarraAccionesView conjuntoBarraAccionesView, Stage stage, ControladorBarraAccionI controller)
    {
        this.iD = barraAcciones.getID();
        this.barraModel = barraAcciones;
        this.stage = stage;
        this.controlador = controller;
        this.conjuntoBarraAccionesView = conjuntoBarraAccionesView;
        this.dad = conjuntoBarraAccionesView.getDadAcciones();

        this.setWidth(barraModel.getNumColumnas()*(Settings.BARRASPELLS_Ancho_Casilla));
        this.setHeight(barraModel.getNumFilas() * (Settings.BARRASPELLS_Ancho_Casilla));

        this.bottom().left();
        this.setPosition(500, 0);

        moverBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        moverBarra.addListener(new VentanaMoverListener(moverBarra, this));
        this.addActor(moverBarra);

        redimensionarBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonON));
        redimensionarBarra.addListener(new VentanaResizeListener(redimensionarBarra, this, this));
        this.addActor(redimensionarBarra);

        eliminarBarra = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_RebindButtonOFF));
        this.addActor(eliminarBarra);
        eliminarBarra.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                controlador.eliminarBarraAcciones(barraModel);
                return true;
            }
        });

        crearBarraIconos();

        this.stage.addActor(this);

        barraModel.añadirObservador(this);
    }

    @Override public void dispose()
    {
        logger.trace("DISPOSE: Liberando BarraAccionesView");
        barraModel.eliminarObservador(this);
    }

    private void crearBarraIconos()
    {
        for (int y=0; y< barraModel.getNumFilas(); y++)
        {   añadirFila(); }
        recrearTabla();
    }

    private CasillaView crearIcono (int posX, int posY)
    {
        CasillaView icono = new CasillaView(barraModel.getCasilla(posX, posY), barraModel.getCaster(), controlador);
        icono.addDragAndDrop(dad, controlador);
        icono.addListener(new BarraAccionesViewRebindListener(icono, conjuntoBarraAccionesView, controlador));
        return icono;
    }

    public void recrearTabla ()
    {   //La tabla es la Vista, lo que se ve, cada vez que se añaden o quitan columnas, o filas hay que recrearla
        this.clear();

        float esquinaSupIzda = getEsquinaSupIzdaY();

        this.setWidth(barraModel.getNumColumnas()*(Settings.BARRASPELLS_Ancho_Casilla));
        this.setHeight(barraModel.getNumFilas()*(Settings.BARRASPELLS_Ancho_Casilla));

        this.setY(esquinaSupIzda-getHeight());

        for (int y=0; y< barraIconos.size; y++)
        {
            for (int x = 0; x < barraIconos.get(y).size; x++)
            {
                CasillaView icono = barraIconos.get(y).get(x);
                this.add(icono).left().height(Settings.BARRASPELLS_Alto_Casilla).width(Settings.BARRASPELLS_Ancho_Casilla);
            }
            this.row();
        }

        moverBarra.setPosition(-moverBarra.getWidth()-2,this.getHeight()-moverBarra.getHeight());
        this.addActor(moverBarra);
        redimensionarBarra.setPosition(this.getWidth(),-redimensionarBarra.getHeight());
        this.addActor(redimensionarBarra);
        eliminarBarra.setPosition(-eliminarBarra.getWidth()-2, this.getHeight()-moverBarra.getHeight()-eliminarBarra.getHeight());
        this.addActor(eliminarBarra);
    }

    @Override public void eventoVentanaResize (int columnasAdicionales, int filasAdicionales)
    {
        if (columnasAdicionales >0)
              { controlador.barraAñadirColumna(barraModel, columnasAdicionales); }
        else  { controlador.barraEliminarColumna(barraModel, Math.abs(columnasAdicionales)); }
        if (filasAdicionales >0)
              { controlador.barraAñadirFila(barraModel, filasAdicionales); }
        else  { controlador.barraEliminarFila(barraModel, Math.abs(filasAdicionales)); }
    }

    private void añadirFila (int numFilas)
    {
        for (int i=0; i<numFilas; i++)
            añadirFila();
        recrearTabla();
    }

    private void añadirColumna(int numColumnas)
    {
        for (int i=0; i<numColumnas; i++)
            añadirColumna();
        recrearTabla();
    }

    private void eliminarFila (int numFilas)
    {
        for (int i=0; i<numFilas; i++)
            eliminarFila();
        recrearTabla();
    }

    private void eliminarColumna(int numColumnas)
    {
        for (int i=0; i<numColumnas; i++)
            eliminarColumna();
        recrearTabla();
    }

    private void añadirFila()
    {
        int y = barraIconos.size;
        Array<CasillaView>array = new Array<>();

        for (int x = 0; x< barraModel.getNumColumnas(); x++)
        {
            CasillaView icono = crearIcono(x, y);
            array.add(icono);
        }
        barraIconos.add(array);
    }

    private void añadirColumna()
    {
        for (int y=0; y< barraIconos.size; y++)
        {
            int x = barraIconos.get(y).size;
            CasillaView icono = crearIcono(x, y);
            barraIconos.get(y).add(icono);
        }
    }

    private void eliminarFila ()
    {
        Array<CasillaView> array = barraIconos.peek();
        for (int i=0; i< array.size; i++)
        {   array.get(i).eliminarIcono(dad); }
        barraIconos.removeIndex(barraIconos.size - 1);
    }

    private void eliminarColumna()
    {
        for (int y=0; y< barraIconos.size; y++)
        {
            CasillaView icono = barraIconos.get(y).pop();
            icono.eliminarIcono(dad);
        }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if  (evt.getNewValue() instanceof DTOsBarraAcciones.EliminarFila)
        {
            int numFilas = ((DTOsBarraAcciones.EliminarFila) evt.getNewValue()).numFilas;
            eliminarFila(numFilas);
        }

        if (evt.getNewValue() instanceof DTOsBarraAcciones.AñadirFila)
        {
            int numFilas = ((DTOsBarraAcciones.AñadirFila) evt.getNewValue()).numFilas;
            añadirFila(numFilas);
        }

        if (evt.getNewValue() instanceof DTOsBarraAcciones.EliminarColumna)
        {
            int numColumnas = ((DTOsBarraAcciones.EliminarColumna) evt.getNewValue()).numColumnas;
            eliminarColumna(numColumnas);
        }

        if (evt.getNewValue() instanceof DTOsBarraAcciones.AñadirColumna)
        {
            int numColumnas = ((DTOsBarraAcciones.AñadirColumna) evt.getNewValue()).numColumnas;
            añadirColumna(numColumnas);
        }
    }
}
