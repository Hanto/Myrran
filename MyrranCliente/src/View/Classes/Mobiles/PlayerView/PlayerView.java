package View.Classes.Mobiles.PlayerView;// Created by Hanto on 10/04/2014.

import DB.RSC;
import DTO.DTOsPlayer;
import Model.Classes.Mobiles.Player.Player;
import Model.Settings;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static Model.Settings.PIXEL_METROS;

public class PlayerView extends Group implements PropertyChangeListener, Disposable
{
    public Player player;

    public PixiePC actor;
    public NameplateView nameplateView;
    public Texto nombre;
    public PointLight luz;

    public float getCenterX()               { return (player.getX()+this.getWidth()/2); }
    public float getCenterY()               { return (player.getY()+this.getHeight()/2); }

    public PlayerView (Player player, PixiePC pixieActor, NameplateView nameplate, PointLight luz)
    {
        this.player = player;
        this.actor = pixieActor;
        this.nameplateView = nameplate;
        this.luz = luz;
        this.setPosition(player.getX(), player.getY());

        crearActor();
        crearNameplate();
        crearNombre();
        crearLuz();

        player.añadirObservador(this);
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
    }

    private void crearNameplate()
    {
        nameplateView.setPosition(this.getWidth() / 2 - nameplateView.getWidth() / 2, getHeight());
        this.addActor(nameplateView);
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
        luz.setColor(0.3f, 0.3f, 0.3f, 1.0f);
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
        if (Math.abs(this.getX()-x) >= 1 || Math.abs(this.getY()-y) >= 1)
        {   super.setPosition(x, y); }
    }

    @Override public void setPosition (float x, float y)
    {   setPosition((int)x, (int)y); }

    public void setAnimacion (int numAnimacion)
    {   actor.setAnimacion(numAnimacion, false); }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsPlayer.Posicion)
        {   setPosition(((DTOsPlayer.Posicion) evt.getNewValue()).posX, ((DTOsPlayer.Posicion) evt.getNewValue()).posY); }

        else if (evt.getNewValue() instanceof DTOsPlayer.Animacion)
        {   setAnimacion(((DTOsPlayer.Animacion) evt.getNewValue()).numAnimacion); }

        else if (evt.getNewValue() instanceof DTOsPlayer.Nombre)
        {   setNombre(((DTOsPlayer.Nombre) evt.getNewValue()).nombre); }

        else if (evt.getNewValue() instanceof DTOsPlayer.ModificarHPs)
        {   modificarHPs(((DTOsPlayer.ModificarHPs) evt.getNewValue()).HPs); }
    }
}