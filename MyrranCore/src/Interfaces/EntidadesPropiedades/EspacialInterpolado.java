package Interfaces.EntidadesPropiedades;// Created by Hanto on 06/08/2015.

import Model.Cuerpos.Cuerpo;

public interface EspacialInterpolado extends Espacial
{
    public void copiarUltimaPosicion();
    public void interpolarPosicion(float alpha);
    public Cuerpo getCuerpo();
}
