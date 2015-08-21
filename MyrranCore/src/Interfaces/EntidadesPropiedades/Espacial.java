package Interfaces.EntidadesPropiedades;// Created by Hanto on 21/07/2014.

import Interfaces.Model.ModelI;
import com.badlogic.gdx.math.Vector2;

public interface Espacial extends ModelI
{
    //GET:
    public Vector2 getPosition();
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
