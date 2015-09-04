package View.Classes.UI.BarraAccionesView.CasillaView;// Created by Hanto on 13/07/2014.

import DB.RSC;
import DTO.DTOsBarraAcciones;
import InterfacesEntidades.EntidadesPropiedades.CasterPersonalizable;
import Interfaces.UI.AccionI;
import Interfaces.UI.CasillaI;
import Interfaces.Controlador.ControladorBarraAccionI;
import Model.Classes.Acciones.TiposAccion.SeleccionarSpell;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Align;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CasillaView extends Actor implements PropertyChangeListener
{
    //Model:
    protected CasillaI casilla;
    protected CasterPersonalizable caster;
    protected ControladorBarraAccionI controlador;

    //View:
    protected TextureRegion casillaIcono;
    protected Texto keybind;
    protected Actor tooltip;
    protected CasillaSource source;
    protected CasillaTarget target;

    public AccionI getAccion()                      { return casilla.getAccion(); }
    public CasillaI getCasilla()                    { return casilla; }
    public CasterPersonalizable getCaster()         { return caster; }
    public ControladorBarraAccionI getControlador() { return controlador; }
    public Actor getApariencia()                    { return this; }
    public Actor getDragActor()                     { return new Image(casillaIcono);}

    //SET:
    public void setTooltip(Actor tooltip)           { this.tooltip = tooltip; }
    public void setTexto(String texto)              { this.keybind.setTexto(texto); }

    //Constructor:
    public CasillaView(CasillaI casilla, CasterPersonalizable caster, ControladorBarraAccionI controlador)
    {
        this.casilla = casilla;
        this.caster = caster;
        this.controlador = controlador;

        keybind = new Texto("", RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres), Color.ORANGE, Color.BLACK, Align.left, Align.top, 2);
        keybind.setTouchable(Touchable.disabled);

        this.addCaptureListener(new CasillaTooltipListener(this));
        this.setBounds(0, 0, Settings.ICONO_Accion_Ancho, Settings.ICONO_Accion_Alto);
        this.setIcono();
        this.setTexto(casilla.getKeybind());

        casilla.añadirObservador(this);
    }

    public void setIcono()
    {
        AccionI accion = getAccion();

        if (accion == null)
        {   casillaIcono = RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura(Settings.RECURSO_BARRASPELLS_Textura_Casillero); }
        else
        {
            if (accion instanceof SeleccionarSpell) { casillaIcono = RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(accion.getID()).getIcono(); }
            else { casillaIcono = RSC.accionRecursosDAO.getAccionRecursosDAO().getAccionRecurso(accion.getID()).getTextura(); }
        }
    }

    public boolean tieneDatos()
    {
        if (getAccion() != null) return true;
        else return false;
    }

    public void addDragAndDrop(DragAndDrop dad, ControladorBarraAccionI controlador)
    {
        source = new CasillaSource(this, dad);
        dad.addSource(source);

        target = new CasillaTarget(this, dad, controlador);
        dad.addTarget(target);
    }

    public void eliminarIcono (DragAndDrop dad)
    {
        if (source != null) dad.removeSource(source);
        if (target != null) dad.removeTarget(target);

        casilla.eliminarObservador(this);
    }

    @Override public void draw (Batch batch, float alpha)
    {   //Posicion Elementos:
        keybind.setPosition(getX(), getY() + 32 -4);
        if (tooltip != null) { tooltip.setPosition(getX(), getY() + 32 +8); }

        //Dibujado Elementos:
        batch.setColor(this.getColor());
        batch.draw(casillaIcono, getX(), getY());
        keybind.draw(batch, alpha);
        if (tooltip != null)
            tooltip.draw(batch, alpha);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsBarraAcciones.ActualizarCasillaAccion)
        {   setIcono(); }

        if (evt.getNewValue() instanceof DTOsBarraAcciones.ActualizarCasillaKey)
        {   setTexto(casilla.getKeybind()); }
    }
}
