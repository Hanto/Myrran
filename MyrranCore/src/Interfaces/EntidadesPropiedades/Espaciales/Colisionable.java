package Interfaces.EntidadesPropiedades.Espaciales;// Created by Hanto on 01/09/2015.

import com.badlogic.gdx.math.Rectangle;

public interface Colisionable extends Espacial, Solido
{
    public Rectangle getHitbox();

    //COLISION CALLBACKS:
    public void checkColisionesConMob(Colisionable colisionable);
    public void checkColisionesConMuro();
}
