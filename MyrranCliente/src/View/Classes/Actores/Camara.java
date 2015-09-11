package View.Classes.Actores;// Created by Hanto on 11/09/2015.

import DTOs.DTOsEspacial;
import Interfaces.EntidadesPropiedades.Espaciales.Espacial;
import Interfaces.Misc.Observable.AbstractModel;
import Model.Settings;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
// Camara para el mundo y para la fisica que sigue a un espacial

public class Camara extends AbstractModel implements PropertyChangeListener, Espacial, Disposable
{
    private Espacial espacial;

    private OrthographicCamera camaraMundo;
    private OrthographicCamera camaraBox2D;

    public OrthographicCamera getCamaraMundo()          { return camaraMundo; }
    public OrthographicCamera getCamaraBox2D()          { return camaraBox2D; }

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------
    @Override public float getX()                       { return camaraMundo.position.x; }
    @Override public float getY()                       { return camaraMundo.position.y; }
    @Override public int getCuadranteX()                { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getCuadranteY()                { return (int)(getY()/ (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoCuadranteX()          { return getCuadranteX(); }
    @Override public int getUltimoCuadranteY()          { return getCuadranteY(); }
    @Override public void setUltimoMapTile(int x, int y){ }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Camara(Espacial espacial, float ancho, float alto)
    {
        this.camaraMundo = new OrthographicCamera(ancho, alto);
        this.camaraBox2D = new OrthographicCamera(ancho * Settings.PIXEL_METROS, alto * Settings.PIXEL_METROS);

        this.espacial = espacial;
        this.setPosition(espacial.getX(), espacial.getY());

        this.espacial.añadirObservador(this);
    }

    @Override public void dispose()
    {   this.espacial.eliminarObservador(this); }

    public void setEspacial(Espacial espacial)
    {
        this.espacial.eliminarObservador(this);
        this.espacial = espacial;
        this.espacial.añadirObservador(this);
    }

    public void setPosition(float x, float y)
    {
        camaraMundo.position.x = x;
        camaraMundo.position.y = y;

        camaraBox2D.position.x = x * Settings.PIXEL_METROS;
        camaraBox2D.position.y = y * Settings.PIXEL_METROS;
    }

    public void setPosition(Espacial espacial)
    {   setPosition(espacial.getX(), espacial.getY()); }

    public void resize(int anchura, int altura)
    {
        camaraMundo.setToOrtho(false, anchura, altura);
        camaraBox2D.setToOrtho(false, anchura * Settings.PIXEL_METROS, altura * Settings.PIXEL_METROS);
        setPosition(espacial);
    }

    public void update()
    {
        camaraMundo.update();
        camaraBox2D.update();
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsEspacial.Posicion)
            setPosition(((DTOsEspacial.Posicion) evt.getNewValue()).posX,
                        ((DTOsEspacial.Posicion) evt.getNewValue()).posY);
    }

}
