package View.Classes.Mobiles.MobView;// Created by Hanto on 13/08/2015.

import DB.RSC;
import DTOs.DTOsEspacial;
import DTOs.DTOsOrientable;
import DTOs.DTOsVulnerable;
import InterfacesEntidades.EntidadesPropiedades.Misc.IDentificable;
import InterfacesEntidades.EntidadesTipos.MobI;
import Model.Settings;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.Pixie;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MobView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    protected MobI mob;
    protected int iD;
    protected Pixie actor;
    protected NameplateView nameplate;

    private int rAncho;
    private int rAlto;

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public MobView(MobI mob, Pixie actor, NameplateView nameplate)
    {
        this.mob = mob;
        this.iD = mob.getID();
        this.actor = actor;
        this.nameplate = nameplate;

        crearActor();
        crearNameplate();

        this.setPosition(mob.getX(), mob.getY());

        mob.añadirObservador(this);
    }

    @Override public void dispose()
    {
        mob.eliminarObservador(this);

        nameplate.dispose();
        this.actor = null;
        this.nameplate = null;
    }

    // CREADORES VIEW:
    //------------------------------------------------------------------------------------------------------------------

    private void crearActor()
    {
        this.addActor(actor);
        this.setWidth(actor.getWidth());
        this.setHeight(actor.getHeight());
        this.rAncho = (int)actor.getWidth()/2;
        this.rAlto = (int)actor.getHeight()/2;
    }

    private void crearNameplate()
    {
        nameplate.setPosition(this.getWidth() / 2 - nameplate.getWidth() / 2, getHeight());
        this.addActor(nameplate);
    }

    // VISTA:
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(int x, int y)
    {
        if (Math.abs(this.getX() - x) > 10 || Math.abs(this.getY() - y) > 10)
            super.setPosition(x-rAncho, y-rAlto);
        else
        {
            this.clearActions();
            this.addAction(Actions.moveTo(x - rAncho, y - rAlto, Settings.FIXED_TimeStep + 0.03f, Interpolation.linear));
        }
    }

    public void setOrientacion(float angulo)
    {
        Double grados = (Math.toDegrees(angulo) + 360) % 360;

        if (225 < grados && grados < 315) actor.setAnimacion(0, false);
        if (135 < grados && grados < 225 ) actor.setAnimacion(1, false);
        if (0 <= grados && grados < 45 || 315 <= grados && grados <= 360 ) actor.setAnimacion(2 , false);
        if (45 < grados && grados < 135) actor.setAnimacion(3, false);
    }

    public void modificarHPs(int HPs)
    {
        Texto texto = new Texto(Integer.toString(HPs), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                HPs < 0 ? Color.RED : Color.GREEN, Color.BLACK, Align.center, Align.bottom, 1);
        texto.setPosition(this.getWidth() / 2 + (float) Math.random() * 30 - 15, this.getHeight() + 15);
        texto.scrollingCombatText(this, 2f);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsEspacial.Posicion)
        {   setPosition(((DTOsEspacial.Posicion) evt.getNewValue()).posX, ((DTOsEspacial.Posicion) evt.getNewValue()).posY); }

        else if (evt.getNewValue() instanceof DTOsOrientable.Orientacion)
        {   setOrientacion(((DTOsOrientable.Orientacion) evt.getNewValue()).orientacion); }

        else if (evt.getNewValue() instanceof DTOsVulnerable.ModificarHPs)
        {   modificarHPs( (int) ((DTOsVulnerable.ModificarHPs) evt.getNewValue()).HPs); }
    }
}