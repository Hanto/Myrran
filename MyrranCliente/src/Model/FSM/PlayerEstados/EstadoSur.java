package Model.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.FSM.MaquinaEstados;

public class EstadoSur extends PlayerEstado
{
    public EstadoSur(MaquinaEstados maquinaEstados)
    {   super(maquinaEstados); }

    @Override public void enter()
    {
        output.setIrArriba(false);
        output.setIrAbajo(input.getIrAbajo());
        output.setirDerecha(input.getIrDerecha());
        output.setIrIzquierda(input.getirIzquierda());
        output.setNumAnimacion(0);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setScreenX(input.getScreenX());
        output.setScreenX(input.getScreenY());

        if (input.getIrArriba())        { fsm.setEstadoSiguiente(EstadoNorte.class); return; }
        if (!input.getIrAbajo())
        {
            if (input.getIrDerecha())   { fsm.setEstadoSiguiente(EstadoEste.class); return; }
            if (input.getirIzquierda()) { fsm.setEstadoSiguiente(EstadoOeste.class); return; }
        }
        if (input.getIrAbajo())
        {
            output.setirDerecha(input.getIrDerecha());
            output.setIrIzquierda(input.getirIzquierda()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   fsm.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
