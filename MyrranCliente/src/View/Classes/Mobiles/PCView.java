package View.Classes.Mobiles;// Created by Hanto on 08/04/2014.

import Controller.Controlador;
import DB.RSC;
import DTO.DTOsPlayer;
import Model.Settings;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.PCI;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import View.GameState.MundoView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PCView extends Group implements PropertyChangeListener
{
    public PCI pc;
    public MundoView mundoView;
    public Controlador controlador;

    public PixiePC actor;
    public NameplateView nameplateView;



    public PCView (PCI pc, MundoView vista, Controlador controlador)
    {
        this.pc = pc;
        this.mundoView = vista;
        this.controlador = controlador;

        this.setPosition(pc.getX(), pc.getY());

        pc.añadirObservador(this);

        crearActor();
    }

    public void crearActor ()
    {
        mundoView.addActor(this);

        actor = new PixiePC("Golem");
        actor.setAnimacion(5, false);
        this.addActor(actor);
        this.setWidth(actor.getWidth());
        this.setHeight(actor.getHeight());

        nameplateView = new NameplateView((Caster)pc);
        nameplateView.setPosition(this.getWidth()/2 - nameplateView.getWidth() / 2, getHeight());
        this.addActor(nameplateView);
    }

    public void dispose()
    {
        pc.eliminarObservador(this);
        mundoView.getRoot().removeActor(this);
        mundoView.eliminarPCView(this);
        nameplateView.dispose();
        this.actor = null;
        this.nameplateView = null;
    }

    public void mover(int x, int y)
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

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPlayer.Posicion)
        {   mover( ((DTOsPlayer.Posicion) evt.getNewValue()).posX,  ((DTOsPlayer.Posicion) evt.getNewValue()).posY ); }

        if (evt.getNewValue() instanceof DTOsPlayer.ModificarHPs)
        {   modificarHPs( (int) (((DTOsPlayer.ModificarHPs) evt.getNewValue()).HPs) ); }

        if (evt.getNewValue() instanceof DTOsPlayer.Animacion)
        {   setAnimacion(((DTOsPlayer.Animacion) evt.getNewValue()).numAnimacion); }

        if (evt.getNewValue() instanceof DTOsPlayer.EliminarPC)
        {   dispose(); }
    }
}
