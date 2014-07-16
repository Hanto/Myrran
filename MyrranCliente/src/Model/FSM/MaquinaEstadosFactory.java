package Model.FSM;// Created by Hanto on 16/07/2014.

import Interfaces.EntidadesPropiedades.Maquinable;
import Model.FSM.PlayerEstados.EstadoEste;
import Model.FSM.PlayerEstados.EstadoNorte;
import Model.FSM.PlayerEstados.EstadoOeste;
import Model.FSM.PlayerEstados.EstadoSur;

public enum MaquinaEstadosFactory
{
    PLAYER
    {
        @Override public MaquinaEstados nuevo(Maquinable entidad)
        {
            MaquinaEstados fsm = new MaquinaEstados(entidad);
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
