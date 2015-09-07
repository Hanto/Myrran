package Interfaces.EntidadesPropiedades.TipoMobile;// Created by Hanto on 03/09/2015.

import Interfaces.Misc.GameState.MundoI;

public interface MobStats
{
    //UPDATE:
    public void actualizarTimers(float delta);
    public void actualizarIA(float delta, MundoI mundo);
}
