package View.Classes.Mobiles.PlayerView;// Created by Hanto on 10/04/2014.

import DB.RSC;
import DTOs.DTOsAnimable;
import DTOs.DTOsEspacial;
import DTOs.DTOsPCStats;
import DTOs.DTOsVulnerable;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesTipos.PlayerI;
import Model.Settings;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import View.Classes.Propiedades.DebuffeableView;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static Model.Settings.PIXEL_METROS;

public class PlayerView extends Group implements PropertyChangeListener, IDentificable, Disposable
{
    protected PlayerI player;
    protected PixiePC actor;
    protected NameplateView nameplateView;
    protected DebuffeableView debuffView;
    protected Texto nombre;
    protected PointLight luz;

    private int rAncho;
    private int rAlto;

    public float getCenterX()                           {   return player.getX(); }
    public float getCenterY()                           {   return player.getY(); }

    //IDENTIFICABLE:
    @Override public int getID()                        {   return player.getID(); }
    @Override public void setID(int iD)                 {   this.player.setID(iD); }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public PlayerView (PlayerI player, PixiePC pixieActor, NameplateView nameplate, DebuffeableView debuffView, PointLight luz)
    {
        this.player = player;
        this.actor = pixieActor;
        this.nameplateView = nameplate;
        this.debuffView = debuffView;
        this.luz = luz;

        crearActor();
        crearNameplate();
        crearDebuffView();
        crearNombre();
        crearLuz();

        this.setPosition(player.getX(), player.getY());

        player.añadirObservador(this);

        actor.setPantalones("PantalonesGolem01");
        actor.setPeto("PetoGolem01");
        actor.setGuantes("GuantesGolem01");
        actor.setBotas("BotasGolem01");
    }

    @Override public void dispose()
    {
        player.eliminarObservador(this);

        luz.remove();
        nameplateView.dispose();
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
        nombre = new Texto("Player"+player.getID(), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.WHITE, Color.BLACK, Align.center, Align.bottom, 1);
        nombre.setPosition(actor.getWidth()/2, actor.getHeight()+8);
        this.addActor(nombre);
    }

    private void crearLuz()
    {
        luz.setSoft(true);
        luz.setColor(0.6f, 0.6f, 0.6f, 1.0f);
        luz.setDistance(350 * PIXEL_METROS);
        luz.attachToBody(player.getCuerpo().getBody(), 0, 0);
    }

    // VISTA:
    //------------------------------------------------------------------------------------------------------------------

    public void setNombre (String nuevoNombre)
    {   nombre.setTexto(nuevoNombre+player.getID()); }

    public void modificarHPs(float HPs)
    {
        Texto texto = new Texto(Integer.toString((int) HPs), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres),
                HPs < 0 ? Color.RED : Color.GREEN, Color.BLACK, Align.center, Align.bottom, 1);
        texto.setPosition(this.getWidth() / 2 + (float) Math.random() * 30 - 15, this.getHeight() + 15);
        texto.scrollingCombatText(this, 2f);
    }

    public void setPosition (int x, int y)
    {
        //if (Math.abs(this.getX()-x) >= 1 || Math.abs(this.getY()-y) >= 1)
        {   super.setPosition(x-rAncho, y-rAlto); }
    }

    @Override public void setPosition (float x, float y)
    {   setPosition((int)x, (int)y); }

    public void setAnimacion (int numAnimacion)
    {   actor.setAnimacion(numAnimacion, false); }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsEspacial.Posicion)
        {   setPosition(((DTOsEspacial.Posicion) evt.getNewValue()).posX, ((DTOsEspacial.Posicion) evt.getNewValue()).posY); }

        else if (evt.getNewValue() instanceof DTOsAnimable.NumAnimacion)
        {   setAnimacion(((DTOsAnimable.NumAnimacion) evt.getNewValue()).numAnimacion); }

        else if (evt.getNewValue() instanceof DTOsVulnerable.ModificarHPs)
        {   modificarHPs(((DTOsVulnerable.ModificarHPs) evt.getNewValue()).HPs); }

        else if (evt.getNewValue() instanceof DTOsPCStats.Nombre)
        {   setNombre(((DTOsPCStats.Nombre) evt.getNewValue()).nombre); }
    }
}