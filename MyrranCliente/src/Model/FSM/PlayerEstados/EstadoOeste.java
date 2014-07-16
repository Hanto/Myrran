package Model.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.FSM.MaquinaEstados;

public class EstadoOeste extends PlayerEstado
{
    public EstadoOeste(MaquinaEstados maquinaEstados)
    {   super(maquinaEstados); }

    @Override public void enter()
    {
        output.setIrArriba(input.getIrArriba());
        output.setIrAbajo(input.getIrAbajo());
        output.setirDerecha(false);
        output.setIrIzquierda(input.getirIzquierda());
        output.setNumAnimacion(0);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setScreenX(input.getScreenX());
        output.setScreenX(input.getScreenY());

        if (input.getIrDerecha())       { fsm.setEstadoSiguiente(EstadoEste.class); return; }
        if (!input.getirIzquierda())
        {
            if (input.getIrArriba())    { fsm.setEstadoSiguiente(EstadoNorte.class); return; }
            if (input.getIrAbajo())     { fsm.setEstadoSiguiente(EstadoSur.class); return; }
        }
        if (input.getirIzquierda())
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
