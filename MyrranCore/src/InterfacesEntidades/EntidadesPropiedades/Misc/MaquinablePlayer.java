package InterfacesEntidades.EntidadesPropiedades.Misc;// Created by Hanto on 25/07/2014.

import Interfaces.Input.PlayerIOI;

public interface MaquinablePlayer extends Maquinable
{
    public PlayerIOI getInput();
    public PlayerIOI getOutput();
}
