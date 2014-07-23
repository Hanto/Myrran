package Controller.Inputs;// Created by Hanto on 22/07/2014.

import Controller.Controlador;
import DB.DAO;
import DTO.NetPCServidor;
import DTO.NetPCServidor.*;
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

    public void procesarInput(NetPCServidor.PCDTOs netPlayer)
    {
        MobPC player;
        if (controlador.getCliente().getID() == netPlayer.connectionID) { player = mundo.getPlayer(); }
        else { player = mundo.getPC(netPlayer.connectionID); }

        Object dto;

        for (int i=0; i<netPlayer.listaDTOs.length; i++)
        {
            dto = netPlayer.listaDTOs[i];

            if (dto instanceof Posicion)
            {   player.setPosition(((Posicion) dto).posX, ((Posicion) dto).posY);  }

            else if (dto instanceof Animacion)
            {   player.setNumAnimacion(((Animacion) dto).animacion); }

            else if (dto instanceof ModificarHPs)
            {   player.modificarHPs(( (ModificarHPs)dto).HPs ); }

            else if (dto instanceof Nombre)
            {   player.setNombre(((Nombre) dto).nombre); }

            else if (dto instanceof EliminarPC)
            {
                MobPC otro = mundo.getPC(((EliminarPC) dto).connectionID);
                if (otro == null) { logger.error("No se puede eliminar un PC con ID que no existe: {}", ((EliminarPC) dto).connectionID); return; }
                else mundo.eliminarPC(((EliminarPC) dto).connectionID);
            }

            else if (dto instanceof CrearPC)
            {
                int id = ((CrearPC) dto).connectionID;

                if (mundo.getPC(id) == null && mundo.getPlayer().getConnectionID() != id)
                {
                    mundo.añadirPC(id, ((CrearPC) dto).posX, ((CrearPC) dto).posY);
                    MobPC otro = mundo.getPC(id);
                    otro.setNombre(((CrearPC) dto).nombre);
                    otro.setNivel(((CrearPC) dto).nivel);
                    otro.setMaxHPs(((CrearPC) dto).maxHPs);
                    otro.setActualHPs(((CrearPC) dto).actualHPs);
                    otro.setNumAnimacion(((CrearPC) dto).numAnimacion);
                }
                else logger.warn("No se puede Crear PC, ConnectionID: {} pertecene al jugador principal o ya existe", id);
            }

            else if (dto instanceof SkillPersonalizado)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((SkillPersonalizado) dto).skillID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((SkillPersonalizado) dto).skillID); return; }
                else
                {
                    logger.debug("Añadido Spell: {}", spell.getID());
                    mundo.getPlayer().añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof NumTalentosSkillPersonalizado)
            {
                String skillID = ((NumTalentosSkillPersonalizado) dto).skillID;
                int statID = ((NumTalentosSkillPersonalizado) dto).statID;
                int valor = ((NumTalentosSkillPersonalizado) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                mundo.getPlayer().setNumTalentosSkillPersonalizadoFromServer(((NumTalentosSkillPersonalizado) dto).skillID,
                                ((NumTalentosSkillPersonalizado) dto).statID, ((NumTalentosSkillPersonalizado) dto).valor);
            }
        }
    }
}
