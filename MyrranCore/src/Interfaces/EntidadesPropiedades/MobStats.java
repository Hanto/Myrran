package Interfaces.EntidadesPropiedades;// Created by Hanto on 03/09/2015.

import Interfaces.GameState.MundoI;

public interface MobStats
{
    //UPDATE:
    public void actualizarTimers(float delta);
    public void actualizarIA(float delta, MundoI mundo);
}
