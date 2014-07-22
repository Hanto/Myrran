package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DTO.NetPlayer.*;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

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

    public void procesarInput(int connectionID, DTOs netPlayer)
    {
        PC player = mundo.getPC(connectionID);
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", connectionID); return; }
        Object dto;


        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof Animacion)
            {   player.setNumAnimacion(((Animacion) dto).animacion); }

            else if (dto instanceof Posicion)
            {   player.setPosition(((Posicion) dto).posX, ((Posicion) dto).posY); }

            else if (dto instanceof ParametrosSpell)
            {   player.setParametrosSpell(((ParametrosSpell) dto).parametros); }

            else if (dto instanceof SpellSeleccionado)
            {
                player.setSpellIDSeleccionado(((SpellSeleccionado) dto).spellIDSeleccionado);
                player.setParametrosSpell(((SpellSeleccionado) dto).parametrosSpell);
            }
            else if (dto instanceof StopCastear)
            {   player.setCastear(false, ((StopCastear) dto).screenX, ((StopCastear) dto).screenY); }

            else if (dto instanceof StartCastear)
            {   player.setCastear(true, ((StartCastear) dto).screenX, ((StartCastear) dto).screenY); }

            else if (dto instanceof NumTalentosSkillPersonalizado)
            {
                player.setNumTalentosSkillPersonalizado(((NumTalentosSkillPersonalizado) dto).skillID,
                ((NumTalentosSkillPersonalizado) dto).statID, ((NumTalentosSkillPersonalizado) dto).valor);
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
