package View.Classes.Propiedades;// Created by Hanto on 08/09/2015.

import DB.RSC;
import DTOs.DTOsAura;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.Misc.Spell.AuraI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
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
        aura.añadirObservador(this);
        this.setSize(nombre.getWidth(), nombre.getHeight());
    }


    private void crearNombre()
    {
        nombre = new Texto(
                aura.getDebuff().getNombre(),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                aura.getDebuff().isDebuff() ? Color.ORANGE : Color.GREEN,
                Color.BLACK,
                Align.left,
                Align.bottom,
                1);
        this.addActor(nombre);

        stacks = new Texto(
                Integer.toString(aura.getStacks()),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                Color.WHITE,
                Color.BLACK,
                Align.right,
                Align.bottom,
                1);
        this.addActor(stacks);

        duracion = new Texto(
                Integer.toString((int)(aura.getDuracionMax()/Settings.BDEBUFF_DuracionTick) - aura.getTicksAplicados()),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                Color.LIGHT_GRAY,
                Color.BLACK,
                Align.left,
                Align.bottom,
                1);

        this.addActor(duracion);
        duracion.setX(nombre.getWidth() + 3);
    }

    private void setStacks(int numStacks)
    {   stacks.setTexto(Integer.toString(numStacks)); }

    private void setDuracion(int numTicks)
    {   duracion.setTexto(Integer.toString((int)(aura.getDuracionMax()/Settings.BDEBUFF_DuracionTick) - numTicks)); }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsAura.SetStacks)
        {   setStacks(((DTOsAura.SetStacks) evt.getNewValue()).numStacks); }

        else if (evt.getNewValue() instanceof DTOsAura.SetTicksAplicados)
        {   setDuracion(((DTOsAura.SetTicksAplicados) evt.getNewValue()).numTicks);}
    }

    @Override
    public void dispose() {
        aura.eliminarObservador(this);
    }
}
