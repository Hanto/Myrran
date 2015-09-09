package View.Classes.Mobiles.PCView;// Created by Hanto on 08/04/2014.

import DB.RSC;
import DTOs.DTOsAnimable;
import DTOs.DTOsEspacial;
import DTOs.DTOsVulnerable;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesTipos.PCI;
import Model.Settings;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import View.Classes.Propiedades.DebuffeableView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PCView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    protected PCI pc;
    protected PixiePC actor;
    protected NameplateView nameplateView;
    protected DebuffeableView debuffView;
    protected Texto nombre;

    private int rAncho;
    private int rAlto;

    //IDENTIFICABLE:
    @Override public int getID()                        {   return pc.getID(); }
    @Override public void setID(int iD)                 {   this.pc.setID(iD); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public PCView (PCI pc, PixiePC pixieActor, NameplateView nameplate, DebuffeableView debuffView)
    {
        this.pc = pc;
        this.actor = pixieActor;
        this.nameplateView = nameplate;
        this.debuffView = debuffView;

        crearActor();
        crearNameplate();
        crearDebuffView();
        crearNombre();

        this.setPosition(pc.getX(), pc.getY());

        pc.añadirObservador(this);
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
        this.rAncho = (int)actor.getWidth()/2;
        this.rAlto = (int)actor.getHeight()/2;
    }

    private void crearNameplate()
    {
        nameplateView.setPosition(this.getWidth() / 2 - nameplateView.getWidth() / 2, getHeight());
        this.addActor(nameplateView);
    }

    private void crearDebuffView()
    {
        debuffView.setPosition(this.getWidth() + 10, this.getHeight());
        this.addActor(debuffView);
    }

    private void crearNombre()
    {
        nombre = new Texto("Player"+getID(), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.WHITE, Color.BLACK, Align.center, Align.bottom, 1);
        nombre.setPosition(actor.getWidth() / 2, actor.getHeight() + 8);
        this.addActor(nombre);
    }

    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(int x, int y)
    {
        super.setPosition(x -rAncho, y -rAlto);
        /*
        if (Math.abs(this.getX() - x) > 10 || Math.abs(this.getY() - y) > 10)
            super.setPosition(x -rAncho, y -rAlto);
        else
        {
            this.clearActions();
            this.addAction(Actions.moveTo(x-rAncho, y-rAlto, Settings.FIXED_TimeStep, Interpolation.linear));
        }*/
    }

    public void setAnimacion (int numAnimacion)
    {   actor.setAnimacion(numAnimacion, false); }

    public void modificarHPs(int HPs)
    {
        Texto texto = new Texto(Integer.toString(HPs), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                HPs < 0 ? Color.RED : Color.GREEN, Color.BLACK, Align.center, Align.bottom, 1);
        texto.setPosition(this.getWidth() / 2 + (float) Math.random() * 30 - 15, this.getHeight() + 15);
        texto.scrollingCombatText(this, 2f);
    }

    // CAMPOS OBSERVADOS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsEspacial.Posicion)
        {   setPosition(((DTOsEspacial.Posicion) evt.getNewValue()).posX, ((DTOsEspacial.Posicion) evt.getNewValue()).posY); }

        if (evt.getNewValue() instanceof DTOsVulnerable.ModificarHPs)
        {   modificarHPs( (int) (((DTOsVulnerable.ModificarHPs) evt.getNewValue()).HPs) ); }

        if (evt.getNewValue() instanceof DTOsAnimable.NumAnimacion)
        {   setAnimacion(((DTOsAnimable.NumAnimacion) evt.getNewValue()).numAnimacion); }
    }
}
