package Model.Classes.UI;// Created by Hanto on 11/07/2014.

import Data.Settings;
import Interfaces.Model.AbstractModel;
import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;
import Model.Classes.Input.InputManager;

public class Casilla extends AbstractModel implements CasillaI
{
    private InputManager inputManager;

    private AccionI accion = null;
    private String keybind;
    private int keycode;

    @Override public AccionI getAccion()              { return accion; }
    @Override public String getKeybind()              { return keybind; }
    @Override public int getKeycode()                 { return keycode; }

    public Casilla (InputManager inputManager)
    {   this.inputManager = inputManager; }


    @Override public void setAccion(AccionI accion)
    {
        this.accion = accion;
        if (accion != null) inputManager.salvarKeybind(keycode, accion.getID());
    }

    @Override public void eliminarAccion()
    {
        accion = null;
        inputManager.eliminarKeybind(keycode);
    }

    @Override public void setKeycode(int keycode)
    {
        this.keycode = keycode;
        this.keybind = Settings.keycodeNames.get(keycode);
    }

    @Override public void setKeybind (String keybind)
    {   this.keybind = keybind; }
}
