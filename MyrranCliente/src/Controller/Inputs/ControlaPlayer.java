package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DB.DAO;
import DTO.Remote.DTOs;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Spell.SpellI;
import Interfaces.UI.Acciones.AccionI;
import Model.Classes.Acciones.AccionFactory;
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

    public void procesarInput(DTOs.PCDTOs netPlayer)
    {
        MobPC player;
        if (controlador.getCliente().getID() == netPlayer.connectionID) { player = mundo.getPlayer(); }
        else { player = mundo.getPC(netPlayer.connectionID); }

        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOs.Posicion)
            {
                if (player == null) continue;
                player.setPosition(((DTOs.Posicion) dto).posX, ((DTOs.Posicion) dto).posY);
            }

            else if (dto instanceof DTOs.Animacion)
            {
                if (player == null) continue;
                player.setNumAnimacion(((DTOs.Animacion) dto).numAnimacion);
            }

            else if (dto instanceof DTOs.ModificarHPs)
            {
                if (player == null) continue;
                player.modificarHPs(( (DTOs.ModificarHPs)dto).HPs ); }

            else if (dto instanceof DTOs.HPs)
            {
                if (player == null) continue;
                player.setMaxHPs(((DTOs.HPs) dto).maxHPs);
                player.setActualHPs(((DTOs.HPs) dto).actualHPs);
            }

            else if (dto instanceof DTOs.Nombre)
            {
                if (player == null) continue;
                player.setNombre(((DTOs.Nombre) dto).nombre); }

            else if (dto instanceof DTOs.EliminarPC)
            {
                MobPC otro = mundo.getPC(((DTOs.EliminarPC) dto).connectionID);
                if (otro == null) { logger.error("No se puede eliminar un PC con ID que no existe: {}", ((DTOs.EliminarPC) dto).connectionID); return; }
                else mundo.eliminarPC(((DTOs.EliminarPC) dto).connectionID);
            }

            else if (dto instanceof DTOs.CrearPC)
            {
                int id = ((DTOs.CrearPC) dto).connectionID;

                if (mundo.getPC(id) == null && mundo.getPlayer().getConnectionID() != id)
                {
                    mundo.añadirPC(id, ((DTOs.CrearPC) dto).posX, ((DTOs.CrearPC) dto).posY);
                    MobPC otro = mundo.getPC(id);
                    otro.setNombre(((DTOs.CrearPC) dto).nombre);
                    otro.setNivel(((DTOs.CrearPC) dto).nivel);
                    otro.setMaxHPs(((DTOs.CrearPC) dto).maxHPs);
                    otro.setActualHPs(((DTOs.CrearPC) dto).actualHPs);
                    otro.setNumAnimacion(((DTOs.CrearPC) dto).numAnimacion);
                }
                else logger.warn("No se puede Crear PC, ConnectionID: {} pertecene al jugador principal o ya existe", id);
            }

            else if (dto instanceof DTOs.SkillPersonalizado)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((DTOs.SkillPersonalizado) dto).skillID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((DTOs.SkillPersonalizado) dto).skillID); return; }
                else
                {
                    logger.debug("Añadido Spell: {}", spell.getID());
                    mundo.getPlayer().añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof DTOs.NumTalentosSkillPersonalizado)
            {
                String skillID = ((DTOs.NumTalentosSkillPersonalizado) dto).skillID;
                int statID = ((DTOs.NumTalentosSkillPersonalizado) dto).statID;
                int valor = ((DTOs.NumTalentosSkillPersonalizado) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                mundo.getPlayer().setNumTalentosSkillPersonalizadoFromServer(skillID, statID, valor);
            }

            else if (dto instanceof DTOs.CambioTerreno)
            {
                int tileX = ((DTOs.CambioTerreno) dto).tileX;
                int tileY = ((DTOs.CambioTerreno) dto).tileY;
                int numCapa = (int) ((DTOs.CambioTerreno) dto).numCapa;
                short iDTerreno = ((DTOs.CambioTerreno) dto).iDTerreno;

                mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
            }
        }
    }
}
