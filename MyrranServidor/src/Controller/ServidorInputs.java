package Controller;// Created by Hanto on 22/07/2014.

import DTO.DTOsCampoVision;
import DTO.DTOsPlayer;
import InterfacesEntidades.EntidadesTipos.PCI;
import Model.Classes.Mobiles.PC.PCFactory;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ServidorInputs
{
    private Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ServidorInputs(Controlador controlador)
    {   this.controlador = controlador; }

    public void procesarInput(int connectionID, DTOsPlayer.PlayerDTOs netPlayer)
    {
        PCI player = controlador.getMundo().getPC(connectionID);
        if (player == null) { logger.error("ERROR: no existe Player con este ID: {}", connectionID); return; }
        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOsCampoVision.Posicion)
            {   player.setPosition(
                    ((DTOsCampoVision.Posicion) dto).posX,
                    ((DTOsCampoVision.Posicion) dto).posY); }

            else if (dto instanceof DTOsCampoVision.NumAnimacion)
            {   player.setNumAnimacion(((DTOsCampoVision.NumAnimacion) dto).numAnimacion); }

            else if (dto instanceof DTOsCampoVision.Castear)
            {   player.setCastear(
                    ((DTOsCampoVision.Castear) dto).spellID,
                    ((DTOsCampoVision.Castear) dto).parametrosSpell,
                    ((DTOsCampoVision.Castear) dto).screenX,
                    ((DTOsCampoVision.Castear) dto).screenY); }

            else if (dto instanceof DTOsCampoVision.SetNumTalentosSkillPersonalizado)
            {
                player.setNumTalentosSkillPersonalizado(
                        ((DTOsCampoVision.SetNumTalentosSkillPersonalizado) dto).skillID,
                        ((DTOsCampoVision.SetNumTalentosSkillPersonalizado) dto).statID,
                        ((DTOsCampoVision.SetNumTalentosSkillPersonalizado) dto).valor); }
        }
    }

    public void procesarLogIn(int connectionID)
    {
        //CREAR PC: hacerlo desde un DAO Factory, que lea y salve datos desde disco:
        //-------------------------------------------------------------------------------------------------------------

        PCI pc = PCFactory.NUEVOPC.nuevo(connectionID);
        //PCFactory.NUEVOPC.nuevo(connectionID, controlador.getMundo().getWorld());
        pc.añadirSkillsPersonalizados("Terraformar");
        pc.añadirSkillsPersonalizados("Heal");
        pc.añadirSkillsPersonalizados("Bolt");
        pc.añadirSkillsPersonalizados("PoisonBolt");
        pc.setNumTalentosSkillPersonalizado("PoisonBolt", 0, 50);
        pc.setNumTalentosSkillPersonalizado("PoisonBolt", 2, 50);
        pc.setNumTalentosSkillPersonalizado("Heal", 0, 10);
        pc.setNumTalentosSkillPersonalizado("Heal", 1, 17);
        pc.setNumTalentosSkillPersonalizado("Hot", 1, 10);

        controlador.getMundo().añadirPC(pc);
    }
}
