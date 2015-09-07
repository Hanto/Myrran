package Model.AI.FSM;// Created by Hanto on 16/07/2014.

import Model.AI.FSM.PlayerEstados.*;
import Interfaces.EntidadesPropiedades.Misc.Maquinable;

public enum MaquinaEstadosFactory
{
    PLAYER
    {
        @Override public MaquinaEstados nuevo(Maquinable entidad)
        {
            MaquinaEstados fsm = new MaquinaEstados(entidad);
            fsm.addEstado(new EstadoQuieto(fsm));
            fsm.addEstado(new EstadoEste(fsm));
            fsm.addEstado(new EstadoOeste(fsm));
            fsm.addEstado(new EstadoNorte(fsm));
            fsm.addEstado(new EstadoSur(fsm));
            return fsm;
        }
    };

    public abstract MaquinaEstados nuevo(Maquinable entidad);
    private MaquinaEstadosFactory() {}
}
