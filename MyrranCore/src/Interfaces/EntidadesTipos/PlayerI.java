package Interfaces.EntidadesTipos;// Created by Hanto on 10/06/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Input.PlayerIOI;

public interface PlayerI extends PCI, MaquinablePlayer
{
    public PlayerIOI getInput ();
}
