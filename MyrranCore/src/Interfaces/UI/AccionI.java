package Interfaces.UI;// Created by Hanto on 05/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Model.ModelI;

public interface AccionI
{
    //GET:
    public String getID();

    //METODOS:
    public void accionKeyDown(MaquinablePlayer player, ModelI model);
    public void accionKeyUp(MaquinablePlayer player, ModelI model);
}