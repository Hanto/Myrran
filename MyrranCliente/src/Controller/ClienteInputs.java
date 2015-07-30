package Controller;// Created by Hanto on 22/07/2014.

import DB.DAO;
import DTO.DTOsCampoVision;
import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Spell.SpellI;
import Interfaces.UI.Acciones.AccionI;
import Model.Classes.Acciones.AccionFactory;
import Model.Classes.Mobiles.Player;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ClienteInputs
{
    private Mundo mundo;
    private Controlador controlador;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ClienteInputs(Mundo mundo, Controlador controlador)
    {
        this.mundo = mundo;
        this.controlador = controlador;
    }

    public void procesarInput(DTOsPlayer.PCDTOs netPlayer)
    {
        PCI player;
        if (controlador.getCliente().getID() == netPlayer.connectionID) { player = mundo.getPlayer(); }
        else { player = mundo.getPC(netPlayer.connectionID); }

        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOsPlayer.Posicion)
            {
                if (player == null) continue;
                player.setPosition(((DTOsPlayer.Posicion) dto).posX, ((DTOsPlayer.Posicion) dto).posY);
            }

            else if (dto instanceof DTOsPlayer.Animacion)
            {
                if (player == null) continue;
                player.setNumAnimacion(((DTOsPlayer.Animacion) dto).numAnimacion);
            }

            else if (dto instanceof DTOsPlayer.ModificarHPs)
            {
                if (player == null) continue;
                player.modificarHPs(( (DTOsPlayer.ModificarHPs)dto).HPs ); }


            else if (dto instanceof DTOsPlayer.HPs)
            {
                if (player == null) continue;
                player.setMaxHPs(((DTOsPlayer.HPs) dto).maxHPs);
                player.setActualHPs(((DTOsPlayer.HPs) dto).actualHPs);
            }

            else if (dto instanceof DTOsPlayer.Nombre)
            {
                if (player == null) continue;
                player.setNombre(((DTOsPlayer.Nombre) dto).nombre); }

            else if (dto instanceof DTOsPlayer.EliminarPC)
            {
                PCI otro = mundo.getPC(((DTOsPlayer.EliminarPC) dto).connectionID);
                if (otro == null) { logger.error("No se puede eliminar un PC con ID que no existe: {}", ((DTOsPlayer.EliminarPC) dto).connectionID); return; }
                else mundo.eliminarPC(((DTOsPlayer.EliminarPC) dto).connectionID);
            }

            else if (dto instanceof DTOsPlayer.CrearPC)
            {
                int id = ((DTOsPlayer.CrearPC) dto).connectionID;

                if (mundo.getPC(id) == null && mundo.getPlayer().getConnectionID() != id)
                {
                    mundo.añadirPC(id, ((DTOsPlayer.CrearPC) dto).posX, ((DTOsPlayer.CrearPC) dto).posY);
                    PCI otro = mundo.getPC(id);
                    otro.setNombre(((DTOsPlayer.CrearPC) dto).nombre);
                    otro.setNivel(((DTOsPlayer.CrearPC) dto).nivel);
                    otro.setMaxHPs(((DTOsPlayer.CrearPC) dto).maxHPs);
                    otro.setActualHPs(((DTOsPlayer.CrearPC) dto).actualHPs);
                    otro.setNumAnimacion(((DTOsPlayer.CrearPC) dto).numAnimacion);
                }
                else logger.warn("No se puede Crear PC, ConnectionID: {} pertecene al jugador principal o ya existe", id);
            }

            else if (dto instanceof DTOsPlayer.SkillPersonalizado)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((DTOsPlayer.SkillPersonalizado) dto).skillID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((DTOsPlayer.SkillPersonalizado) dto).skillID); return; }
                else
                {
                    logger.debug("Añadido Spell: {}", spell.getID());
                    mundo.getPlayer().añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof DTOsPlayer.NumTalentosSkillPersonalizado)
            {
                String skillID = ((DTOsPlayer.NumTalentosSkillPersonalizado) dto).skillID;
                int statID = ((DTOsPlayer.NumTalentosSkillPersonalizado) dto).statID;
                int valor = ((DTOsPlayer.NumTalentosSkillPersonalizado) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                mundo.getPlayer().setNumTalentosSkillPersonalizadoFromServer(skillID, statID, valor);
            }

            else if (dto instanceof DTOsPlayer.CambioTerreno)
            {
                int tileX = ((DTOsPlayer.CambioTerreno) dto).tileX;
                int tileY = ((DTOsPlayer.CambioTerreno) dto).tileY;
                int numCapa = (int) ((DTOsPlayer.CambioTerreno) dto).numCapa;
                short iDTerreno = ((DTOsPlayer.CambioTerreno) dto).iDTerreno;

                mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
            }
        }
    }

    public void procesarActualizacionesPC(DTOsCampoVision.PCDTOs pcDTOs)
    {
        PCI pc;
        Object dto;

        if (pcDTOs.connectionID == mundo.getPlayer().getConnectionID()) pc = mundo.getPlayer();
        else if (mundo.getPC(pcDTOs.connectionID) == null)
        {
            mundo.añadirPC(pcDTOs.connectionID, -5000, -5000);
            pc = mundo.getPC(pcDTOs.connectionID);
        }
        else pc = mundo.getPC(pcDTOs.connectionID);


        for (int i=0; i<pcDTOs.listaDTOs.length; i++)
        {
            dto = pcDTOs.listaDTOs[i];

            if (dto instanceof DTOsCampoVision.EliminarPC)
            {   mundo.eliminarPC(pcDTOs.connectionID); }

            else if (dto instanceof DTOsCampoVision.PosicionPC)
            {   pc.setPosition(((DTOsCampoVision.PosicionPC) dto).posX, ((DTOsCampoVision.PosicionPC) dto).posY); }

            else if (dto instanceof DTOsCampoVision.NumAnimacionPC)
            {   pc.setNumAnimacion(((DTOsCampoVision.NumAnimacionPC) dto).numAnimacion); }

            else if (dto instanceof DTOsCampoVision.NombrePC)
            {   pc.setNombre(((DTOsCampoVision.NombrePC) dto).nombre);}

            else if (dto instanceof DTOsCampoVision.ModificarHPsPC)
            {   pc.modificarHPs(((DTOsCampoVision.ModificarHPsPC) dto).HPs);}

            else if (dto instanceof DTOsCampoVision.HPsPC)
            {
                pc.setMaxHPs(((DTOsCampoVision.HPsPC) dto).maxHPs);
                pc.setActualHPs(((DTOsCampoVision.HPsPC) dto).actualHPs);
            }

            else if (dto instanceof DTOsCampoVision.AñadirSpellPersonalizadoPC)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((DTOsCampoVision.AñadirSpellPersonalizadoPC) dto).spellID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((DTOsCampoVision.AñadirSpellPersonalizadoPC) dto).spellID); return; }
                else
                {
                    pc.añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof DTOsCampoVision.NumTalentosSkillPersonalizadoPC)
            {
                String skillID = ((DTOsCampoVision.NumTalentosSkillPersonalizadoPC) dto).skillID;
                int statID = ((DTOsCampoVision.NumTalentosSkillPersonalizadoPC) dto).statID;
                int valor = ((DTOsCampoVision.NumTalentosSkillPersonalizadoPC) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                if (pc instanceof Player) ((Player) pc).setNumTalentosSkillPersonalizadoFromServer(skillID, statID, valor);
                else pc.setNumTalentosSkillPersonalizado(skillID, statID, valor);
            }

            else if (dto instanceof DTOsCampoVision.DatosCompletosPC)
            {
                pc.setNumAnimacion(((DTOsCampoVision.DatosCompletosPC) dto).numAnimacion);
                pc.setNombre(((DTOsCampoVision.DatosCompletosPC) dto).nombre);
                pc.setMaxHPs(((DTOsCampoVision.DatosCompletosPC) dto).maxHPs);
                pc.setActualHPs(((DTOsCampoVision.DatosCompletosPC) dto).actualHPs);
                pc.setNivel(((DTOsCampoVision.DatosCompletosPC) dto).nivel);
            }
        }
    }


    public void procesarActualizacionesMisc(DTOsCampoVision.MiscDTOs miscDTOs)
    {
        Object dto;

        for (int i=0; i<miscDTOs.listaDTOs.length; i++)
        {
            dto = miscDTOs.listaDTOs[i];

            if (dto instanceof DTOsCampoVision.CambioTerrenoMisc)
            {
                int tileX = ((DTOsCampoVision.CambioTerrenoMisc) dto).tileX;
                int tileY = ((DTOsCampoVision.CambioTerrenoMisc) dto).tileY;
                int numCapa = ((DTOsCampoVision.CambioTerrenoMisc) dto).numCapa;
                short iDTerreno = ((DTOsCampoVision.CambioTerrenoMisc) dto).iDTerreno;
                mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
            }
        }
    }
}
