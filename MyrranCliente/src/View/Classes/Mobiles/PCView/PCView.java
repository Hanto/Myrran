package View.Classes.Mobiles.PCView;// Created by Hanto on 08/04/2014.

import DB.RSC;
import DTO.DTOsPlayer;
import Interfaces.EntidadesPropiedades.IDentificable;
import Interfaces.EntidadesTipos.PCI;
import Model.Settings;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import View.Classes.Actores.NameplateView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PCView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    protected PCI pc;
    protected int iD;
    protected PixiePC actor;
    protected NameplateView nameplateView;
    protected Texto nombre;

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public PCView (PCI pc, PixiePC pixieActor, NameplateView nameplate)
    {
        this.pc = pc;
        this.iD = pc.getID();
        this.actor = pixieActor;
        this.nameplateView = nameplate;
        this.setPosition(pc.getX(), pc.getY());

        pc.añadirObservador(this);

        crearActor();
        crearNameplate();
        crearNombre();
    }

    @Override public void dispose()
    {
        pc.eliminarObservador(this);

        nameplateView.dispose();
        this.actor = null;
        this.nameplateView = null;
    }

    // CREADORES VIEW:
    //------------------------------------------------------------------------------------------------------------------

    private void crearActor ()
    {
        actor.setAnimacion(5, false);
        this.addActor(actor);
        this.setWidth(actor.getWidth());
        this.setHeight(actor.getHeight());
    }

    private void crearNameplate()
    {
        nameplateView.setPosition(this.getWidth() / 2 - nameplateView.getWidth() / 2, getHeight());
        this.addActor(nameplateView);
    }

    private void crearNombre()
    {
        nombre = new Texto("Player"+iD, RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.WHITE, Color.BLACK, Align.center, Align.bottom, 1);
        nombre.setPosition(actor.getWidth()/2, actor.getHeight()+8);
        this.addActor(nombre);
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(int x, int y)
    {
        //TODO hay que hacerlo por setPosition y en cambio mover el pc interpoladamente, el destino sin decimales
        this.clearActions();
        this.addAction(Actions.moveTo(x, y, Settings.FIXED_TimeStep+0.03f, Interpolation.linear));
        //setPosition(x,y);
    }

    public void modificarHPs(int HPs)
    {
        Texto texto = new Texto(Integer.toString(HPs), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                HPs < 0 ? Color.RED : Color.GREEN, Color.BLACK, Align.center, Align.bottom, 1);
        texto.setPosition(this.getWidth() / 2 + (float) Math.random() * 30 - 15, this.getHeight() + 15);
        texto.scrollingCombatText(this, 2f);
    }

    public void setAnimacion (int numAnimacion)
    {   actor.setAnimacion(numAnimacion, false); }


    // CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPlayer.Posicion)
        {   setPosition(((DTOsPlayer.Posicion) evt.getNewValue()).posX, ((DTOsPlayer.Posicion) evt.getNewValue()).posY); }

        if (evt.getNewValue() instanceof DTOsPlayer.ModificarHPs)
        {   modificarHPs( (int) (((DTOsPlayer.ModificarHPs) evt.getNewValue()).HPs) ); }

        if (evt.getNewValue() instanceof DTOsPlayer.Animacion)
        {   setAnimacion(((DTOsPlayer.Animacion) evt.getNewValue()).numAnimacion); }

        if (evt.getNewValue() instanceof DTOsPlayer.EliminarPC)
        {   dispose(); }
    }
}
