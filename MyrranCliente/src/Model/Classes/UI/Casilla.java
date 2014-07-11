package Model.Classes.UI;// Created by Hanto on 11/07/2014.

import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;

public class Casilla implements CasillaI
{
    public AccionI accion = null;
    public String keybind;
    public int keycode;

    @Override public void setAccion(AccionI accion)   { this.accion = accion; }
    @Override public void setKeybind(String keybind)  { this.keybind = keybind; }
    @Override public void setKeycode(int keycode)     { this.keycode = keycode; }

    @Override public AccionI getAccion()              { return accion; }
    @Override public String getKeybind()              { return keybind; }
    @Override public int getKeycode()                 { return keycode; }
}
