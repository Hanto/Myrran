package View.Classes.Propiedades;// Created by Hanto on 08/09/2015.

import DB.RSC;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.Misc.Spell.AuraI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AuraView extends Group implements PropertyChangeListener, IDentificable
{
    private AuraI aura;
    private Texto nombre;

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
    }


    private void crearNombre()
    {
        nombre = new Texto(
                aura.getDebuff().getNombre(),
                RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                aura.getDebuff().isDebuff() ? Color.RED : Color.GREEN,
                Color.BLACK,
                Align.center,
                Align.bottom,
                1);
        this.addActor(nombre);
    }

    @Override public void propertyChange(PropertyChangeEvent event)
    {

    }
}
