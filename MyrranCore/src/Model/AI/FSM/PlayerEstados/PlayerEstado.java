package Model.AI.FSM.PlayerEstados;// Created by Hanto on 15/07/2014.

import Interfaces.EntidadesPropiedades.Propiedades.MaquinablePlayer;
import Interfaces.Misc.Input.PlayerIOI;
import Model.AI.FSM.Estado;
import Model.AI.FSM.MaquinaEstados;

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
