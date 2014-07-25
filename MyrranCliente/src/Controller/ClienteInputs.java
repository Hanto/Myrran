package Controller;// Created by Hanto on 22/07/2014.

import DB.DAO;
import DTO.DTOsPC;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Spell.SpellI;
import Interfaces.UI.Acciones.AccionI;
import Model.Classes.Acciones.AccionFactory;
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

    public void procesarInput(DTOsPC.PCDTOs netPlayer)
    {
        MobPC player;
        if (controlador.getCliente().getID() == netPlayer.connectionID) { player = mundo.getPlayer(); }
        else { player = mundo.getPC(netPlayer.connectionID); }

        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof DTOsPC.Posicion)
            {
                if (player == null) continue;
                player.setPosition(((DTOsPC.Posicion) dto).posX, ((DTOsPC.Posicion) dto).posY);
            }

            else if (dto instanceof DTOsPC.Animacion)
            {
                if (player == null) continue;
                player.setNumAnimacion(((DTOsPC.Animacion) dto).numAnimacion);
            }

            else if (dto instanceof DTOsPC.ModificarHPs)
            {
                if (player == null) continue;
                player.modificarHPs(( (DTOsPC.ModificarHPs)dto).HPs ); }


            else if (dto instanceof DTOsPC.HPs)
            {
                if (player == null) continue;
                player.setMaxHPs(((DTOsPC.HPs) dto).maxHPs);
                player.setActualHPs(((DTOsPC.HPs) dto).actualHPs);
            }

            else if (dto instanceof DTOsPC.Nombre)
            {
                if (player == null) continue;
                player.setNombre(((DTOsPC.Nombre) dto).nombre); }

            else if (dto instanceof DTOsPC.EliminarPC)
            {
                MobPC otro = mundo.getPC(((DTOsPC.EliminarPC) dto).connectionID);
                if (otro == null) { logger.error("No se puede eliminar un PC con ID que no existe: {}", ((DTOsPC.EliminarPC) dto).connectionID); return; }
                else mundo.eliminarPC(((DTOsPC.EliminarPC) dto).connectionID);
            }

            else if (dto instanceof DTOsPC.CrearPC)
            {
                int id = ((DTOsPC.CrearPC) dto).connectionID;

                if (mundo.getPC(id) == null && mundo.getPlayer().getConnectionID() != id)
                {
                    mundo.añadirPC(id, ((DTOsPC.CrearPC) dto).posX, ((DTOsPC.CrearPC) dto).posY);
                    MobPC otro = mundo.getPC(id);
                    otro.setNombre(((DTOsPC.CrearPC) dto).nombre);
                    otro.setNivel(((DTOsPC.CrearPC) dto).nivel);
                    otro.setMaxHPs(((DTOsPC.CrearPC) dto).maxHPs);
                    otro.setActualHPs(((DTOsPC.CrearPC) dto).actualHPs);
                    otro.setNumAnimacion(((DTOsPC.CrearPC) dto).numAnimacion);
                }
                else logger.warn("No se puede Crear PC, ConnectionID: {} pertecene al jugador principal o ya existe", id);
            }

            else if (dto instanceof DTOsPC.SkillPersonalizado)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((DTOsPC.SkillPersonalizado) dto).skillID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((DTOsPC.SkillPersonalizado) dto).skillID); return; }
                else
                {
                    logger.debug("Añadido Spell: {}", spell.getID());
                    mundo.getPlayer().añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof DTOsPC.NumTalentosSkillPersonalizado)
            {
                String skillID = ((DTOsPC.NumTalentosSkillPersonalizado) dto).skillID;
                int statID = ((DTOsPC.NumTalentosSkillPersonalizado) dto).statID;
                int valor = ((DTOsPC.NumTalentosSkillPersonalizado) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                mundo.getPlayer().setNumTalentosSkillPersonalizadoFromServer(skillID, statID, valor);
            }

            else if (dto instanceof DTOsPC.CambioTerreno)
            {
                int tileX = ((DTOsPC.CambioTerreno) dto).tileX;
                int tileY = ((DTOsPC.CambioTerreno) dto).tileY;
                int numCapa = (int) ((DTOsPC.CambioTerreno) dto).numCapa;
                short iDTerreno = ((DTOsPC.CambioTerreno) dto).iDTerreno;

                mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
            }
        }
    }
}
