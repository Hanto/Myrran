package Interfaces.EntidadesPropiedades;// Created by Hanto on 13/08/2015.

public interface DinamicoSimple
{
    //GET:
    public float getVelocidadMax();
    public float getVelocidadMod();

    public void setVelocidadMax(float velocidadMax);
    public void setVelocidaMod(float velocidadMod);

    public void setDireccion(float x, float y);
    public void setDireccion(float grados);
}
