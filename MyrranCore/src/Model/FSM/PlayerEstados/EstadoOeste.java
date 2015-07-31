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
        output.setSpellID(input.getSpellID());
        output.setScreenX(input.getScreenX());
        output.setScreenY(input.getScreenY());

        if (input.getIrDerecha())       { maquinaEstados.setEstadoSiguiente(EstadoEste.class); return; }
        if (!input.getirIzquierda())
        {
            if (input.getIrArriba())    { maquinaEstados.setEstadoSiguiente(EstadoNorte.class); return; }
            if (input.getIrAbajo())     { maquinaEstados.setEstadoSiguiente(EstadoSur.class); return; }
        }
        if (input.getirIzquierda())
        {
            output.setIrArriba(input.getIrArriba());
            output.setIrAbajo(input.getIrAbajo()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   maquinaEstados.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
