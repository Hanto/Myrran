package Controller;// Created by Hanto on 22/07/2014.

import DTO.DTOsPC;
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

    public void procesarInput(int connectionID, DTOsPC.PlayerDTOs netPlayer)
    {
        PC player = mundo.getPC(connectionID);
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", connectionID); return; }
        Object dto;


        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOsPC.Animacion)
            {   player.setNumAnimacion(((DTOsPC.Animacion) dto).numAnimacion); }

            else if (dto instanceof DTOsPC.Posicion)
            {   player.setPosition(((DTOsPC.Posicion) dto).posX, ((DTOsPC.Posicion) dto).posY); }

            else if (dto instanceof DTOsPC.ParametrosSpell)
            {   player.setParametrosSpell(((DTOsPC.ParametrosSpell) dto).parametros); }

            else if (dto instanceof DTOsPC.SpellSeleccionado)
            {
                player.setSpellIDSeleccionado(((DTOsPC.SpellSeleccionado) dto).spellIDSeleccionado);
                player.setParametrosSpell(((DTOsPC.SpellSeleccionado) dto).parametrosSpell);
            }
            else if (dto instanceof DTOsPC.StopCastear)
            {   player.setCastear(false, ((DTOsPC.StopCastear) dto).screenX, ((DTOsPC.StopCastear) dto).screenY); }

            else if (dto instanceof DTOsPC.StartCastear)
            {   player.setCastear(true, ((DTOsPC.StartCastear) dto).screenX, ((DTOsPC.StartCastear) dto).screenY); }


            else if (dto instanceof DTOsPC.NumTalentosSkillPersonalizado)
            {
                player.setNumTalentosSkillPersonalizado(((DTOsPC.NumTalentosSkillPersonalizado) dto).skillID,
                ((DTOsPC.NumTalentosSkillPersonalizado) dto).statID, ((DTOsPC.NumTalentosSkillPersonalizado) dto).valor);
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
