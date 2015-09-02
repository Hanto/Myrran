package Interfaces.EntidadesPropiedades;// Created by Hanto on 21/07/2014.

import Interfaces.GameState.MundoI;
import Interfaces.Model.ModelI;

public interface PCStats extends ModelI
{
    public int getIDProyectiles();
    public String getNombre();
    public int getNivel();

    public void setNombre(String nombre);
    public void setNivel(int nivel);

    //UPDATE:
    public void actualizarTimers(float delta);
    public void actualizarIA(float delta, MundoI mundo);
}
