package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DTO.Remote.DTOs;
import Model.Classes.Mobiles.PC;
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

    public void procesarInput(int connectionID, DTOs.PlayerDTOs netPlayer)
    {
        PC player = mundo.getPC(connectionID);
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", connectionID); return; }
        Object dto;


        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOs.Animacion)
            {   player.setNumAnimacion(((DTOs.Animacion) dto).numAnimacion); }

            else if (dto instanceof DTOs.Posicion)
            {   player.setPosition(((DTOs.Posicion) dto).posX, ((DTOs.Posicion) dto).posY); }

            else if (dto instanceof DTOs.ParametrosSpell)
            {   player.setParametrosSpell(((DTOs.ParametrosSpell) dto).parametros); }

            else if (dto instanceof DTOs.SpellSeleccionado)
            {
                player.setSpellIDSeleccionado(((DTOs.SpellSeleccionado) dto).spellIDSeleccionado);
                player.setParametrosSpell(((DTOs.SpellSeleccionado) dto).parametrosSpell);
            }
            else if (dto instanceof DTOs.StopCastear)
            {   player.setCastear(false, ((DTOs.StopCastear) dto).screenX, ((DTOs.StopCastear) dto).screenY); }

            else if (dto instanceof DTOs.StartCastear)
            {   player.setCastear(true, ((DTOs.StartCastear) dto).screenX, ((DTOs.StartCastear) dto).screenY); }


            else if (dto instanceof DTOs.NumTalentosSkillPersonalizado)
            {
                player.setNumTalentosSkillPersonalizado(((DTOs.NumTalentosSkillPersonalizado) dto).skillID,
                ((DTOs.NumTalentosSkillPersonalizado) dto).statID, ((DTOs.NumTalentosSkillPersonalizado) dto).valor);
            }
        }
    }

    public void procesarLogIn(int connectionID)
    {
        mundo.añadirPC(connectionID);

        mundo.getPC(connectionID).añadirSkillsPersonalizados("Terraformar");
        mundo.getPC(connectionID).añadirSkillsPersonalizados("Heal");
        mundo.getPC(connectionID).setNumTalentosSkillPersonalizado("Heal", 0, 10);
        mundo.getPC(connectionID).setNumTalentosSkillPersonalizado("Heal", 1, 17);
        mundo.getPC(connectionID).setNumTalentosSkillPersonalizado("Hot", 1, 10);
    }
}
