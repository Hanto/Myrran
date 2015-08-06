package View.Classes.Mobiles.ProyectilView;// Created by Hanto on 06/08/2015.

import DTO.DTOsProyectil;
import Interfaces.EntidadesTipos.ProyectilI;
import Model.Settings;
import View.Classes.Actores.Pixie;
import View.GameState.MundoView;
import box2dLight.PointLight;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProyectilView extends Group implements PropertyChangeListener, Disposable
{
    protected ProyectilI proyectil;
    protected MundoView mundoView;
    //protected Controlador controlador;

    protected Pixie animacionProyectil;
    protected PointLight luz;

    public ProyectilView(ProyectilI proyectil, MundoView mundoView, Pixie animacionProyectil, PointLight luz)
    {
        this.proyectil = proyectil;
        this.mundoView = mundoView;

        setAnimacionProyectil(animacionProyectil);
        setLuz(luz);

        this.proyectil.añadirObservador(this);
    }

    @Override public void dispose()
    {
        this.proyectil.eliminarObservador(this);
        this.luz.remove();
        this.mundoView.eliminarProyectilView(this);
    }

    public void setAnimacionProyectil (Pixie pixie)
    {
        animacionProyectil = pixie;
        animacionProyectil.setOrigin(animacionProyectil.getWidth() / 2, animacionProyectil.getHeight() / 2);
        this.addActor(animacionProyectil);
        this.setWidth(animacionProyectil.getWidth());
        this.setHeight(animacionProyectil.getHeight());
        setDireccion();
        this.setColor(0, 0, 0, 0);
        this.addAction(Actions.fadeIn(0.5f));
    }

    public void setLuz(PointLight luz)
    {
        this.luz = luz;
        luz.setSoft(true);
        luz.setColor(0.6f, 0.0f, 0.0f, 1.0f);
        luz.setDistance(300 * Settings.PIXEL_METROS);
        luz.attachToBody(proyectil.getCuerpo().getBody());
    }

    public void setDireccion()
    {
        float grados = proyectil.getCuerpo().getDireccion().angle();
        animacionProyectil.rotateBy(grados);
    }

    public void setPosition (int x, int y)
    {
        if (Math.abs(this.getX()-x) >= 1 || Math.abs(this.getY()-y) >= 1)
        {   super.setPosition(x, y); }

    }

    @Override public void setPosition (float x, float y)
    {   setPosition((int)x, (int)y); }


    //CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsProyectil.PosicionProyectil)
        {
            setPosition(((DTOsProyectil.PosicionProyectil) evt.getNewValue()).proyectil.getX(),
                    ((DTOsProyectil.PosicionProyectil) evt.getNewValue()).proyectil.getY());
        }
        else if (evt.getNewValue() instanceof DTOsProyectil.DisposeProyectil)
        {   dispose(); }
    }
}
