package Model.AI.FSM.PlayerEstados;// Created by Hanto on 16/07/2014.

import Model.AI.FSM.MaquinaEstados;

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
        output.setNumAnimacion(3);
    }

    @Override public void update(float deltaTime)
    {
        output.setStartCastear(input.getStartCastear());
        output.setStopCastear(input.getStopCastear());
        output.setSpellID(input.getSpellID());
        output.setScreenX(input.getScreenX());
        output.setScreenY(input.getScreenY());
        output.setMundoXY(input.getMundoX(), input.getMundoY());

        if ( input.getIrArriba() &&
            !input.getIrAbajo())        { maquinaEstados.setEstadoSiguiente(EstadoNorte.class); return; }
        if (!input.getIrAbajo())
        {
            if (input.getIrDerecha())   { maquinaEstados.setEstadoSiguiente(EstadoEste.class); return; }
            if (input.getirIzquierda()) { maquinaEstados.setEstadoSiguiente(EstadoOeste.class); return; }
        }
        if (input.getIrAbajo())
        {
            output.setirDerecha(input.getIrDerecha());
            output.setIrIzquierda(input.getirIzquierda()); return;
        }
        if (!input.getIrDerecha() && !input.getirIzquierda() && !input.getIrArriba() && !input.getIrAbajo())
        {   maquinaEstados.setEstadoSiguiente(EstadoQuieto.class); return; }
    }

    @Override public void exit()
    {

    }
}
