package Controller;// Created by Hanto on 22/07/2014.

import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PCI;
import Model.Classes.Mobiles.PC;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ServidorInputs
{
    private Mundo mundo;
    private Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ServidorInputs(Mundo mundo, Controlador controlador)
    {
        this.mundo = mundo;
        this.controlador = controlador;
    }

    public void procesarInput(int connectionID, DTOsPlayer.PlayerDTOs netPlayer)
    {
        PCI player = mundo.getPC(connectionID);
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", connectionID); return; }
        Object dto;


        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOsPlayer.Animacion)
            {   player.setNumAnimacion(((DTOsPlayer.Animacion) dto).numAnimacion); }

            else if (dto instanceof DTOsPlayer.Posicion)
            {   player.setPosition(((DTOsPlayer.Posicion) dto).posX, ((DTOsPlayer.Posicion) dto).posY); }

            else if (dto instanceof DTOsPlayer.ParametrosSpell)
            {   player.setParametrosSpell(((DTOsPlayer.ParametrosSpell) dto).parametros); }

            else if (dto instanceof DTOsPlayer.SpellSeleccionado)
            {
                player.setSpellIDSeleccionado(((DTOsPlayer.SpellSeleccionado) dto).spellIDSeleccionado);
                player.setParametrosSpell(((DTOsPlayer.SpellSeleccionado) dto).parametrosSpell);
            }
            else if (dto instanceof DTOsPlayer.StopCastear)
            {   player.setCastear(false, ((DTOsPlayer.StopCastear) dto).screenX, ((DTOsPlayer.StopCastear) dto).screenY); }

            else if (dto instanceof DTOsPlayer.StartCastear)
            {   player.setCastear(true, ((DTOsPlayer.StartCastear) dto).screenX, ((DTOsPlayer.StartCastear) dto).screenY); }


            else if (dto instanceof DTOsPlayer.NumTalentosSkillPersonalizado)
            {
                player.setNumTalentosSkillPersonalizado(((DTOsPlayer.NumTalentosSkillPersonalizado) dto).skillID,
                ((DTOsPlayer.NumTalentosSkillPersonalizado) dto).statID, ((DTOsPlayer.NumTalentosSkillPersonalizado) dto).valor);
            }
        }
    }

    public void procesarLogIn(int connectionID)
    {
        //CREAR PC: hacerlo desde un DAO Factory, que lea y salve datos desde disco:
        //-------------------------------------------------------------------------------------------------------------
        PCI pc = new PC(connectionID, mundo);
        pc.añadirSkillsPersonalizados("Terraformar");
        pc.añadirSkillsPersonalizados("Heal");
        pc.setNumTalentosSkillPersonalizado("Heal", 0, 10);
        pc.setNumTalentosSkillPersonalizado("Heal", 1, 17);
        pc.setNumTalentosSkillPersonalizado("Hot", 1, 10);

        mundo.añadirPC(pc);
    }
}
