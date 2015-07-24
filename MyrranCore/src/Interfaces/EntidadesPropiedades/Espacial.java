package Interfaces.EntidadesPropiedades;// Created by Hanto on 21/07/2014.

import Interfaces.Model.ModelI;

public interface Espacial extends ModelI
{
    //GET:7
    public float getX();
    public float getY();
    public int getMapTileX();
    public int getMapTileY();
    public int getUltimoMapTileX();
    public int getUltimoMapTileY();

    //SET:
    public void setPosition(float x, float y);
    public void setUltimoMapTile (int x, int y);
}
