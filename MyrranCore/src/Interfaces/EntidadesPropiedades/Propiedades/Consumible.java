package Interfaces.EntidadesPropiedades.Propiedades;// Created by Hanto on 06/08/2015.

public interface Consumible
{
    public float getDuracionActual();
    public float getDuracionMaxima();
    public void setDuracionMaxima(float duracionMaxima);
    public void setDuracionActual(float duracionActual);
    public boolean actualizarDuracion(float delta);
}