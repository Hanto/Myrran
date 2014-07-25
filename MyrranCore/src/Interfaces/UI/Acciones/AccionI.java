package Interfaces.UI.Acciones;// Created by Hanto on 05/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.UI.ControladorUI;

public interface AccionI
{
    //GET:
    public String getID();

    //METODOS:
    public void accionKeyDown(MaquinablePlayer player, ControladorUI controlador);
    public void accionKeyUp(MaquinablePlayer player, ControladorUI controlador);
}