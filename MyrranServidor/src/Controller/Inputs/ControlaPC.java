package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DTO.NetPlayer;
import DTO.NetPlayer.*;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class ControlaPC
{
    private Mundo mundo;
    private Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ControlaPC(Mundo mundo, Controlador controlador)
    {
        this.mundo = mundo;
        this.controlador = controlador;
    }

    public void procesarInput(NetPlayer netPlayer)
    {
        if (netPlayer.isLogIn())
        {
            mundo.añadirPC(netPlayer.getConnectionID());

            mundo.getPC(netPlayer.getConnectionID()).añadirSkillsPersonalizados("Terraformar");
            mundo.getPC(netPlayer.getConnectionID()).añadirSkillsPersonalizados("Heal");
            mundo.getPC(netPlayer.getConnectionID()).setNumTalentosSkillPersonalizado("Heal", 0, 10);
            mundo.getPC(netPlayer.getConnectionID()).setNumTalentosSkillPersonalizado("Heal", 1, 17);
            mundo.getPC(netPlayer.getConnectionID()).setNumTalentosSkillPersonalizado("Hot", 1, 10);
        }

        PC player = mundo.getPC(netPlayer.getConnectionID());
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", netPlayer.getConnectionID()); return; }
        Object dto;

        Iterator<Object>iteratorDTOs = netPlayer.getListaDTOs();
        while (iteratorDTOs.hasNext())
        {
            dto = iteratorDTOs.next();

            if (dto instanceof Animacion)
            {   player.setNumAnimacion(((Animacion) dto).animacion); }

            else if (dto instanceof Posicion)
            {   player.setPosition(((Posicion) dto).posX, ((Posicion) dto).posY); }

            else if (dto instanceof ParametrosSpell)
            {   player.setParametrosSpell(((ParametrosSpell) dto).parametrosSpell); }

            else if (dto instanceof SpellSeleccionado)
            {
                player.setSpellIDSeleccionado(((SpellSeleccionado) dto).spellIDSeleccionado);
                player.setParametrosSpell(((SpellSeleccionado) dto).parametrosSpell);
            }
            else if (dto instanceof StopCastear)
            {   player.setCastear(false, ((StopCastear) dto).screenX, ((StopCastear) dto).screenY); }

            else if (dto instanceof StartCastear)
            {   player.setCastear(true, ((StartCastear) dto).screenX, ((StartCastear) dto).screenY); }
        }
    }
}
