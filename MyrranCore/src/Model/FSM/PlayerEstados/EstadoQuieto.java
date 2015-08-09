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
        output.setSpellID(input.getSpellID());
        output.setScreenX(input.getScreenX());
        output.setScreenY(input.getScreenY());
        output.setMundoXY(input.getMundoX(), input.getMundoY());

        if (input.getIrDerecha())       { maquinaEstados.setEstadoSiguiente(EstadoEste.class); return; }
        if (input.getirIzquierda())     { maquinaEstados.setEstadoSiguiente(EstadoOeste.class); return; }
        if (input.getIrArriba())        { maquinaEstados.setEstadoSiguiente(EstadoNorte.class); return; }
        if (input.getIrAbajo())         { maquinaEstados.setEstadoSiguiente(EstadoSur.class); return; }
    }

    @Override public void exit()
    {

    }
}
