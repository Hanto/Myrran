package Interfaces.EntidadesPropiedades;// Created by Hanto on 24/07/2015.

import Interfaces.GameState.MundoI;
import Interfaces.Model.ModelI;

public interface Actualizable extends ModelI
{
    public void actualizar(float delta, MundoI mundo);
}
