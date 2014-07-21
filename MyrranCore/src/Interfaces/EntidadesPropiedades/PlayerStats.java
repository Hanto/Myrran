package Interfaces.EntidadesPropiedades;// Created by Hanto on 21/07/2014.

public interface PlayerStats
{
    public int getConnectionID();
    public String getNombre();
    public int getNivel();

    public void setConnectionID(int connectionID);
    public void setNombre(String nombre);
    public void setNivel(int nivel);
}
