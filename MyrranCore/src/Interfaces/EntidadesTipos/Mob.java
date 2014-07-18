package Interfaces.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.Model.ModelI;

public interface Mob extends ModelI
{
    //GET:
    public int getTimestamp();
    public float getX();
    public float getY();
    public float getVelocidadMod();
    public float getVelocidadMax();
    public double getDireccion();

    public int getNumAnimacion();
    public void setNumAnimacion(int numAnimacion);

    //SET:
    public void setTimestamp(int timestap);
    public void setPosition(float x, float y);
    public void setVelocidaMod(float velocidadMod);
    public void setVelocidadMax(float velocidadMax);
    public void setDireccion(double direccion);
}
