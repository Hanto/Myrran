package Interfaces.UI.Acciones;// Created by Hanto on 11/07/2014.

import Interfaces.Model.ModelI;

public interface CasillaI extends ModelI
{
    public void setAccion(AccionI accion);
    public void setAccion (String idAccion);
    public void setKeycode (int keycode);
    public void setKeybind (String keybind);
    public void setMovible (boolean b);

    public AccionI getAccion();
    public String getKeybind();
    public int getKeycode();
    public boolean getMovible();

    //Metodos:
    public void eliminarAccion();
}
