package Interfaces.EntidadesPropiedades;// Created by Hanto on 21/07/2014.

public interface Dinamico extends Espacial
{
    //GET:
    public float getVelocidadMod();
    public float getVelocidadMax();

    //SET:
    public void setVelocidaMod(float velocidadMod);
    public void setVelocidadMax(float velocidadMax);
    public void setDireccion(float x, float y);
    public void setDireccion(float grados);
    public void setVectorDireccion(float x, float y);

}
