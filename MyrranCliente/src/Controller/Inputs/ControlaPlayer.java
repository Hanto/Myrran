package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DTO.NetPCServidor;
import Interfaces.EntidadesTipos.MobPC;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ControlaPlayer
{
    private Mundo mundo;
    private Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ControlaPlayer(Mundo mundo, Controlador controlador)
    {
        this.mundo = mundo;
        this.controlador = controlador;
    }

    public void procesarInput(NetPCServidor.DTOs netPlayer)
    {
        MobPC player;
        if (controlador.getCliente().getID() == netPlayer.connectionID) { player = mundo.getPlayer(); }
        else { player = mundo.getPC(netPlayer.connectionID); }

        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof NetPCServidor.Posicion)
            {   player.setPosition(((NetPCServidor.Posicion) dto).posX, ((NetPCServidor.Posicion) dto).posY); }

            if (dto instanceof NetPCServidor.Nombre)
            {   player.setNombre(((NetPCServidor.Nombre) dto).nombre); }

            if (dto instanceof NetPCServidor.ActualHPs)
            {   player.setActualHPs(((NetPCServidor.ActualHPs) dto).HPs);}
        }
    }
}
