package Model.Mobiles.Propiedades;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Consumible;

public class ConsumibleBase implements Consumible
{
    protected float duracionActual = 0.0f;
    protected float duracionMaxima = 5f;

    @Override public float getDuracionActual()                          { return duracionActual; }
    @Override public float getDuracionMaxima()                          { return duracionMaxima; }
    @Override public void setDuracionActual(float duracionActual)       { this.duracionActual = duracionActual; }
    @Override public void setDuracionMaxima(float duracionMaxima)       { this.duracionMaxima = duracionMaxima; }

    public boolean actualizarDuracion(float delta)
    {
        duracionActual += delta;
        return duracionActual > duracionMaxima;
    }
}
