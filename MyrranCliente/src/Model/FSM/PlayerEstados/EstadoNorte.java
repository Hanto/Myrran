package Model.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.FSM.*;

public class EstadoNorte extends PlayerEstado
{
    public EstadoNorte(MaquinaEstados maquinaEstados)
    {   super(maquinaEstados); }

    @Override public void enter()
    {
        output.setIrArriba(input.getIrArriba());
        output.setIrAbajo(false);
        output.setirDerecha(input.getIrDerecha());
        output.setIrIzquierda(input.getirIzquierda());
        output.setNumAnimacion(2);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setScreenX(input.getScreenX());
        output.setScreenX(input.getScreenY());

        if (input.getIrAbajo())         { fsm.setEstadoSiguiente(EstadoSur.class); return; }
        if (!input.getIrArriba())
        {
            if (input.getIrDerecha())   { fsm.setEstadoSiguiente(EstadoEste.class); return; }
            if (input.getirIzquierda()) { fsm.setEstadoSiguiente(EstadoOeste.class); return; }
        }
        if (input.getIrArriba())
        {
            output.setIrIzquierda(input.getirIzquierda());
            output.setirDerecha(input.getIrDerecha()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   fsm.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
