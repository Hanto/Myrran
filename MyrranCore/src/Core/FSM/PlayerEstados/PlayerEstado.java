package Core.FSM.PlayerEstados;// Created by Hanto on 15/07/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Input.PlayerIOI;
import Core.FSM.Estado;
import Core.FSM.MaquinaEstados;

public abstract class PlayerEstado extends Estado
{
    protected PlayerIOI input;
    protected PlayerIOI output;

    public PlayerEstado(MaquinaEstados maquinaEstados)
    {
        super(maquinaEstados);
        input =  ((MaquinablePlayer)this.maquinaEstados.getMaquinable()).getInput();
        output = ((MaquinablePlayer)this.maquinaEstados.getMaquinable()).getOutput();
    }
}
