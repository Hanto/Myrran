package Model.FSM.PlayerEstados;// Created by Hanto on 15/07/2014.

import Interfaces.Input.PlayerIOI;
import Model.FSM.Estado;
import Model.FSM.MaquinaEstados;

public abstract class PlayerEstado extends Estado
{
    protected PlayerIOI input;
    protected PlayerIOI output;

    public PlayerEstado(MaquinaEstados maquinaEstados)
    {
        super(maquinaEstados);
        input = (PlayerIOI)fsm.getEntidad().getInput();
        output = (PlayerIOI)fsm.getEntidad().getOutput();
    }
}
