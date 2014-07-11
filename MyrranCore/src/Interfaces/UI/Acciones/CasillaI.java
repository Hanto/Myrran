package Interfaces.UI.Acciones;// Created by Hanto on 11/07/2014.

public interface CasillaI
{
    public void setAccion(AccionI accion);
    public void setKeybind (String keybind);
    public void setKeycode (int keycode);

    public AccionI getAccion();
    public String getKeybind();
    public int getKeycode();
}
