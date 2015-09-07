package View.Classes.Actores;

import DB.RSC;
import DB.Recursos.MiscRecursos.DAO.MiscRecursosDAO;
import DTOs.DTOsCaster;
import DTOs.DTOsVulnerable;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.Vulnerable;
import Interfaces.Misc.Model.ModelI;
import Model.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// @author Ivan Delgado Huerta
public class NameplateView extends Actor implements PropertyChangeListener, Disposable
{
    //Model:
    protected Vulnerable vulnerable;
    protected Caster Caster;
    protected ModelI model;

    protected boolean isCaster = false;
    protected boolean isVulnerable = false;

    protected boolean isCasting = false;

    //NAMEPLATES
    protected Sprite barraVidaTotal;                //Imagen que contiene el nameplateTotal de la vida del Player
    protected Sprite barraVidaActual;               //Imagen que contiene el MapaView de la vida del nameplateTotal del Player
    protected Sprite barraCasteoTotal;
    protected Sprite barraCasteoActual;

    public NameplateView(Vulnerable vulnerable)
    {
        this.model = (ModelI)vulnerable;
        this.vulnerable = vulnerable;
        this.isVulnerable = true;

        if (vulnerable instanceof Caster)
        {
            this.Caster = (Caster)vulnerable;
            this.isCaster = true;
        }

        crearActor();
        model.añadirObservador(this);
    }

    public NameplateView(Caster Caster)
    {
        this.model = (ModelI)Caster;
        this.Caster = Caster;
        this.isCaster = true;

        if (Caster instanceof Vulnerable)
        {
            this.vulnerable = (Vulnerable) Caster;
            this.isVulnerable = true;
        }

        crearActor();
        model.añadirObservador(this);
    }

    public void dispose()
    {   model.eliminarObservador(this); }

    //
    //------------------------------------------------------------------------------------------------------------------

    public void crearActor()
    {
        MiscRecursosDAO miscRecursosDAO = RSC.miscRecusosDAO.getMiscRecursosDAO();

        int alto = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate).getRegionHeight();
        int ancho = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate).getRegionWidth();

        if (isVulnerable)
        {
            barraVidaTotal = new Sprite(miscRecursosDAO.cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate));
            barraVidaActual = new Sprite(miscRecursosDAO.cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate_Fondo));
            barraVidaTotal.setColor(Color.GREEN);
            barraVidaTotal.setPosition(0, alto-1);
            barraVidaActual.setPosition(ancho-1, alto-1);
        }
        if (isCaster)
        {
            barraCasteoTotal = new Sprite(miscRecursosDAO.cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate));
            barraCasteoActual = new Sprite(miscRecursosDAO.cargarTextura(Settings.RECURSO_NAMEPLATE_Nameplate_Fondo));
            barraCasteoTotal.setColor(Color.ORANGE);
            barraCasteoActual.setPosition(ancho-1, 0);
        }

        this.setWidth(ancho);
        this.setHeight(alto);
        this.setBounds(0, 0, ancho, alto);
    }

    private void setHPsPercent ()
    {
        float HPsPercent = vulnerable.getActualHPs()/vulnerable.getMaxHPs();

        float tamaño = (1 - HPsPercent) * this.getWidth();
        if (tamaño != barraVidaActual.getWidth()) barraVidaActual.setSize(-(int) tamaño, this.getHeight());
    }

    private void setCastingTimePercent (float castingTimePercent)
    {
        if (castingTimePercent <100) isCasting = true;
        else isCasting = false;

        float tamaño = (1-castingTimePercent)*this.getWidth();
        if (tamaño != barraCasteoActual.getWidth()) barraCasteoActual.setSize(-(int)tamaño, this.getHeight());
    }



    @Override public void act(float delta)
    {
        float alto = this.getHeight();
        float ancho = this.getWidth();
        
        super.act(delta);

        if (isVulnerable)
        {
            barraVidaTotal.setPosition(this.getX(), this.getY() + alto - 1);
            barraVidaActual.setPosition(this.getX() + ancho - 1, this.getY() + alto - 1);
        }
        if (isCaster)
        {
            barraCasteoTotal.setPosition(this.getX(), this.getY());
            barraCasteoActual.setPosition(this.getX() + ancho - 1, this.getY());
        }
    }
    
    @Override public void draw (Batch batch, float alpha)
    {
        if (isVulnerable)
        {
            barraVidaTotal.draw(batch, alpha);
            barraVidaActual.draw(batch, alpha);
        }
        if (isCaster && isCasting)
        {
            barraCasteoTotal.draw(batch, alpha);
            barraCasteoActual.draw(batch, alpha);
        }
    }


    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsVulnerable.ModificarHPs)
        {   setHPsPercent(); }

        else if (evt.getNewValue() instanceof DTOsVulnerable.HPs)
        {   setHPsPercent();}

        else if (evt.getNewValue() instanceof DTOsCaster.CastingTimePercent)
        {   setCastingTimePercent(((DTOsCaster.CastingTimePercent) evt.getNewValue()).castingTimePercent); }
    }

}
