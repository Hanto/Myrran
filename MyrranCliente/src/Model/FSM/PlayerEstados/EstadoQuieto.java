package Model.FSM.PlayerEstados;// Created by Hanto on 15/07/2014.

import Model.FSM.MaquinaEstados;

public class EstadoQuieto extends PlayerEstado
{
    public EstadoQuieto(MaquinaEstados maquinaEstados)
    {   super(maquinaEstados); }

    @Override public void enter()
    {
        output.setIrArriba(false);
        output.setIrAbajo(false);
        output.setirDerecha(false);
        output.setIrIzquierda(false);
        output.setNumAnimacion(5);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setScreenX(input.getScreenX());
        output.setScreenX(input.getScreenY());

        if (input.getIrDerecha())       { fsm.setEstadoSiguiente(EstadoEste.class); return; }
        if (input.getirIzquierda())     { fsm.setEstadoSiguiente(EstadoOeste.class); return; }
        if (input.getIrArriba())        { fsm.setEstadoSiguiente(EstadoNorte.class); return; }
        if (input.getIrAbajo())         { fsm.setEstadoSiguiente(EstadoSur.class); return; }
    }

    @Override public void exit()
    {

    }
}
