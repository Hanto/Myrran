package Model.Classes.UI;// Created by Hanto on 11/07/2014.

import Data.Settings;
import Interfaces.Model.AbstractModel;
import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;
import Model.Classes.Input.InputManager;
import Model.DTO.BarraAccionesDTO;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class Casilla extends AbstractModel implements CasillaI
{
    private InputManager inputManager;

    private AccionI accion = null;
    private String keybind;
    private int keycode;
    private boolean movible = true;

    @Override public AccionI getAccion()                { return accion; }
    @Override public String getKeybind()                { return keybind; }
    @Override public int getKeycode()                   { return keycode; }
    @Override public boolean getMovible()               { return movible; }

    @Override public void setMovible (boolean b)        { movible = b;}

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Casilla (InputManager inputManager)
    {   this.inputManager = inputManager; }


    @Override public void setAccion(AccionI accion)
    {
        this.accion = accion;
        if (accion != null) inputManager.salvarKeybind(keycode, accion.getID());
        Object actualizarCasilla = new BarraAccionesDTO.ActualizarCasillaAccion();
        notificarActualizacion("setAccion", null, actualizarCasilla);
    }

    @Override public void setAccion (String idAccion)
    {
        AccionI nuevaAccion = inputManager.getAccion(idAccion);
        if (nuevaAccion == null) { logger.error("ERROR: no existe accion con este nombre: {}", idAccion); return; }
        else setAccion(nuevaAccion);
    }

    @Override public void eliminarAccion()
    {
        accion = null;
        inputManager.eliminarKeybind(keycode);
        Object actualizarCasilla = new BarraAccionesDTO.ActualizarCasillaAccion();
        notificarActualizacion("setAccion", null, actualizarCasilla);
    }

    @Override public void setKeycode(int keycode)
    {
        inputManager.eliminarKeybind(this.keycode);
        this.keycode = keycode;
        this.keybind = Settings.keycodeNames.get(keycode);
        if (accion != null) inputManager.salvarKeybind(keycode, accion.getID());
        Object actualizarCasillaKey = new BarraAccionesDTO.ActualizarCasillaKey();
        notificarActualizacion("setAccion", null, actualizarCasillaKey);
    }

    @Override public void setKeybind (String keybind)
    {   this.keybind = keybind;
        Object actualizarCasillaKey = new BarraAccionesDTO.ActualizarCasillaKey();
        notificarActualizacion("setAccion", null, actualizarCasillaKey);
    }
}
