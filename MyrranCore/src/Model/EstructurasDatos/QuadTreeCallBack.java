package Model.EstructurasDatos;// Created by Hanto on 03/09/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;

public interface QuadTreeCallBack
{
    public void returnCercano(Colisionable colisionable, Colisionable mobCercano);
}
