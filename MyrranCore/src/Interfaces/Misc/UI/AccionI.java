package Interfaces.Misc.UI;// Created by Hanto on 05/05/2014.

import Interfaces.EntidadesPropiedades.Propiedades.MaquinablePlayer;
import Interfaces.Misc.Observable.ModelI;

public interface AccionI
{
    //GET:
    public String getID();

    //METODOS:
    public void accionKeyDown(MaquinablePlayer player, ModelI model);
    public void accionKeyUp(MaquinablePlayer player, ModelI model);
}