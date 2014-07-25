package View.Classes.Mobiles;// Created by Hanto on 10/04/2014.

import Controller.Controlador;
import DB.RSC;
import DTO.DTOsPC;
import Data.Settings;
import Interfaces.EntidadesPropiedades.Vulnerable;
import Model.Classes.Mobiles.Player;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.Texto;
import View.GameState.MundoView;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static Data.Settings.PIXEL_METROS;

public class PlayerView extends Group implements PropertyChangeListener
{
    //Modelo:
    public Player player;
    public MundoView mundoView;
    public Controlador controlador;

    //Vista:
    public PixiePC actor;
    public Body body;
    public NameplateView nameplateView;
    public Texto nombre;
    public PointLight luz;

    public float getCenterX()               { return (player.getX()+this.getWidth()/2); }
    public float getCenterY()               { return (player.getY()+this.getHeight()/2); }

    public PlayerView (Player player, MundoView mundoView, Controlador controlador)
    {
        this.player = player;
        this.mundoView = mundoView;
        this.controlador = controlador;

        crearActor();

        this.setPosition(player.getX(), player.getY());
        player.añadirObservador(this);
    }

    public void crearActor ()
    {
        mundoView.addActor(this);

        actor = new PixiePC("Golem");
        actor.setAnimacion(5, false);
        this.addActor(actor);
        this.setWidth(actor.getWidth());
        this.setHeight(actor.getHeight());

        nameplateView = new NameplateView((Vulnerable)player);
        nameplateView.setPosition(this.getWidth()/2 - nameplateView.getWidth() / 2, getHeight());
        this.addActor(nameplateView);

        nombre = new Texto("Player", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.WHITE, Color.BLACK, Align.center, Align.bottom, 1);
        nombre.setPosition(actor.getWidth()/2, actor.getHeight()+8);
        this.addActor(nombre);

        luz = new PointLight(mundoView.getRayHandler(), 300, new Color(0.3f,0.3f,0.3f,1.0f), 350 *PIXEL_METROS, 0, 0);
        luz.setSoft(true);
        luz.attachToBody(player.getBody(), 0, 0);
    }


    //VISTA:
    public void setNombre (String nuevoNombre)
    {   nombre.setTexto(nuevoNombre); }

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
        if (evt.getNewValue() instanceof DTOsPC.Posicion)
        {   setPosition(((DTOsPC.Posicion) evt.getNewValue()).posX, ((DTOsPC.Posicion) evt.getNewValue()).posY); }

        if (evt.getNewValue() instanceof DTOsPC.Animacion)
        {   setAnimacion(((DTOsPC.Animacion) evt.getNewValue()).numAnimacion); }

        if (evt.getNewValue() instanceof DTOsPC.Nombre)
        {   setNombre(((DTOsPC.Nombre) evt.getNewValue()).nombre); }

        if (evt.getNewValue() instanceof DTOsPC.ModificarHPs)
        {   modificarHPs(((DTOsPC.ModificarHPs) evt.getNewValue()).HPs); }
    }
}