package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DTO.NetPlayer;
import DTO.NetPlayer.Animacion;
import DTO.NetPlayer.Posicion;
import Model.Classes.Mobiles.Player;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

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

    public void procesarInput(NetPlayer netPlayer)
    {
        Player player = mundo.getPlayer();
        Object dto;

        Iterator<Object> iteratorDTOs = netPlayer.getListaDTOs();
        while (iteratorDTOs.hasNext())
        {
            dto = iteratorDTOs.next();

            if (dto instanceof Animacion)
            {   player.setNumAnimacion(((Animacion) dto).animacion); }

            else if (dto instanceof Posicion)
            {   player.setPosition(((Posicion) dto).posX, ((Posicion) dto).posY); }
        }
    }
}
