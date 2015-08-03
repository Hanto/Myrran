package Model.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.FSM.MaquinaEstados;

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
        output.setSpellID(input.getSpellID());
        output.setScreenX(input.getScreenX());
        output.setScreenY(input.getScreenY());

        if ( input.getIrAbajo() &&
            !input.getIrArriba())       { maquinaEstados.setEstadoSiguiente(EstadoSur.class); return; }
        if (!input.getIrArriba())
        {
            if (input.getIrDerecha())   { maquinaEstados.setEstadoSiguiente(EstadoEste.class); return; }
            if (input.getirIzquierda()) { maquinaEstados.setEstadoSiguiente(EstadoOeste.class); return; }
        }
        if (input.getIrArriba())
        {
            output.setIrIzquierda(input.getirIzquierda());
            output.setirDerecha(input.getIrDerecha()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   maquinaEstados.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
