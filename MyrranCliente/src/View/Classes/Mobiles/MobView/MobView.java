package View.Classes.Mobiles.MobView;// Created by Hanto on 13/08/2015.

import DB.RSC;
import DTO.DTOsMob;
import Interfaces.EntidadesPropiedades.IDentificable;
import Interfaces.EntidadesTipos.MobI;
import Model.Settings;
import View.Classes.Actores.Pixie;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MobView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    protected MobI mob;
    protected int iD;
    protected Pixie actor;

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                            { return iD; }
    @Override public void setID(int iD)                                     { this.iD = iD; }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public MobView(MobI mob)
    {
        this.mob = mob;
        this.iD = mob.getID();
        this.actor = new Pixie(RSC.pixieMobRecursosDAO.getPixieMobRecursosDaoDAO().getPixieMob("GrimReaper"));
        this.setPosition(mob.getX(), mob.getY());

        mob.añadirObservador(this);

        crearActor();
    }

    @Override public void dispose()
    {
        mob.eliminarObservador(this);
        this.actor = null;
    }

    private void crearActor()
    {
        this.addActor(actor);
        this.setWidth(actor.getWidth());
        this.setHeight(actor.getHeight());
    }

    public void setPosition(int x, int y)
    {
        this.clearActions();
        this.addAction(Actions.moveTo(x, y, Settings.FIXED_TimeStep + 0.03f, Interpolation.linear));
    }

    public void setOrientacion(float angulo)
    {
        Double grados = (double)Math.toDegrees(angulo);
        if (grados <0) grados += 360;

        if (225 < grados && grados < 315) actor.setAnimacion(0, false);
        if (135 < grados && grados < 225 ) actor.setAnimacion(1, false);
        if (0 <= grados && grados < 45 || 315 <= grados && grados <= 360 ) actor.setAnimacion(2 , false);
        if (45 < grados && grados < 135) actor.setAnimacion(3, false);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsMob.PosicionMob)
        {   setPosition(((DTOsMob.PosicionMob) evt.getNewValue()).posX, ((DTOsMob.PosicionMob) evt.getNewValue()).posY); }

        else if (evt.getNewValue() instanceof DTOsMob.OrientacionMob)
        {   setOrientacion(((DTOsMob.OrientacionMob) evt.getNewValue()).orientacion);}
    }
}