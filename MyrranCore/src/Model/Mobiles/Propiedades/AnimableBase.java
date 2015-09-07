package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import Interfaces.EntidadesPropiedades.Misc.Animable;

public class AnimableBase implements Animable
{
    protected int numAnimacion = 5;

    public int getNumAnimacion()                                    { return numAnimacion; }
    public void setNumAnimacion(int numAnimacion)                   { this.numAnimacion = numAnimacion; }
}
