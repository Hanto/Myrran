package Interfaces.UI.Acciones;// Created by Hanto on 05/05/2014.

import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.UI.ControladorUI;

public interface AccionI
{
    //GET:
    public String getID();

    //METODOS:
    public void accionKeyDown(MobPlayer player, ControladorUI controlador);
    public void accionKeyUp(MobPlayer player, ControladorUI controlador);
}