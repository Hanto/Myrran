package Interfaces.EntidadesPropiedades.Espaciales;// Created by Hanto on 21/07/2014.

import Interfaces.Model.ModelI;

public interface Espacial extends ModelI
{
    //GET:
    public float getX();
    public float getY();
    public int getCuadranteX();
    public int getCuadranteY();
    public int getUltimoCuadranteX();
    public int getUltimoCuadranteY();

    //SET:
    public void setPosition(float x, float y);
    public void setUltimoMapTile (int x, int y);
}
