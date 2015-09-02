package Interfaces.EstructurasDatos;// Created by Hanto on 01/09/2015.

import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;

import java.util.List;

public interface QuadTreeI
{
    public void add(Colisionable colisionable);
    public List<Colisionable> getCercanos(List<Colisionable> objetosCercanos, Colisionable colisionable);
    public void clear();
}
