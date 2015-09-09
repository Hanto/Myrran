package View.Classes.Propiedades;// Created by Hanto on 08/09/2015.

import DB.RSC;
import DTOs.DTOsAura;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.Misc.Spell.AuraI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuraView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    private AuraI aura;
    private Texto nombre;
    private Texto stacks;
    private Texto duracion;

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------
    @Override public int getID()                            { return aura.getID(); }
    @Override public void setID(int iD)                     { this.aura.setID(iD); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public AuraView(AuraI aura)
    {
        this.aura = aura;

        crearNombre();

        this.setSize(nombre.getWidth(), nombre.getHeight());
        this.setTransform(false);

        this.aura.añadirObservador(this);
    }

    @Override public void dispose()
    {   aura.eliminarObservador(this); }


    private void crearNombre()
    {
        nombre = new Texto(
                aura.getDebuff().getNombre(),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10),
                setColorDebuff(),
                Color.BLACK,
                Align.left,
                Align.bottom,
                1);
        this.addActor(nombre);

        stacks = new Texto(
                Integer.toString(aura.getStacks()),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10),
                Color.WHITE,
                Color.BLACK,
                Align.right,
                Align.bottom,
                1);
        this.addActor(stacks);

        duracion = new Texto(
                Integer.toString(aura.getTicksRestantes()),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10),
                new Color(0.6f, 0.6f, 0.6f, 1f),
                Color.BLACK,
                Align.left,
                Align.bottom,
                1);

        this.addActor(duracion);
        duracion.setX(nombre.getWidth() + 3);
    }

    private Color setColorDebuff()
    {
        Color color;

        if (aura.getDebuff().isDebuff())
        {
            if (aura.getCaster() instanceof PlayerI) color = (Color.ORANGE);
            else color = (Color.TAN);
        }
        else
        {
            if (aura.getCaster() instanceof PlayerI) color = (Color.GREEN);
            else color = (Color.LIME);
        }
        return color;
    }

    private void setStacks(int numStacks)
    {
        stacks.setTexto(Integer.toString(numStacks));
        this.clearActions();
        this.setColor(this.getColor().r, this.getColor().g, this.getColor().b, 1);
    }

    private void setDuracion(int numTicks)
    {
        duracion.setTexto(Integer.toString(aura.getTicksRestantes()));
        if ( aura.getTicksRestantes() == 1)
            this.addAction(Actions.fadeOut(1));
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsAura.SetStacks)
        {   setStacks(((DTOsAura.SetStacks) evt.getNewValue()).numStacks); }

        else if (evt.getNewValue() instanceof DTOsAura.SetTicksAplicados)
        {   setDuracion(((DTOsAura.SetTicksAplicados) evt.getNewValue()).numTicks);}
    }
}
