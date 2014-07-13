package Interfaces.UI.Acciones;// Created by Hanto on 11/07/2014.

import Interfaces.Model.ModelI;

public interface CasillaI extends ModelI
{
    public void setAccion(AccionI accion);
    public void setKeycode (int keycode);
    public void setKeybind (String keybind);

    public AccionI getAccion();
    public String getKeybind();
    public int getKeycode();

    //Metodos:
    public void eliminarAccion();
}
