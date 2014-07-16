package Model.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.FSM.*;

public class EstadoEste extends PlayerEstado
{
    public EstadoEste(MaquinaEstados maquinaEstados)
    {   super(maquinaEstados); }

    @Override public void enter()
    {
        output.setIrArriba(input.getIrArriba());
        output.setIrAbajo(input.getIrAbajo());
        output.setirDerecha(input.getIrDerecha());
        output.setIrIzquierda(false);
        output.setNumAnimacion(1);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setScreenX(input.getScreenX());
        output.setScreenX(input.getScreenY());

        if (input.getirIzquierda())     { fsm.setEstadoSiguiente(EstadoOeste.class); return; }
        if (!input.getIrDerecha())
        {
            if (input.getIrArriba())    { fsm.setEstadoSiguiente(EstadoNorte.class); return; }
            if (input.getIrAbajo())     { fsm.setEstadoSiguiente(EstadoSur.class); return; }
        }
        if (input.getIrDerecha())
        {
            output.setIrArriba(input.getIrArriba());
            output.setIrAbajo(input.getIrAbajo()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   fsm.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
