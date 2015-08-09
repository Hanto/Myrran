package Controller;// Created by Hanto on 22/07/2014.

import DB.DAO;
import DTO.DTOsCampoVision;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.Spell.SpellI;
import Interfaces.UI.AccionI;
import Model.Classes.Acciones.AccionFactory;
import Model.Classes.Mobiles.PC;
import Model.Classes.Mobiles.Player;
import Model.Classes.Mobiles.Proyectil.ProyectilFactory;
import Model.GameState.Mundo;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class ClienteInputs
{
    private Controlador controlador;
    private Mundo mundo;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ClienteInputs(Controlador controlador)
    {
        this.controlador = controlador;
        this.mundo = controlador.getMundo();
    }

    public void procesarActualizacionesPC(DTOsCampoVision.PCDTOs pcDTOs)
    {
        PCI pc;
        Object dto;

        if (pcDTOs.connectionID == mundo.getPlayer().getID()) pc = mundo.getPlayer();
        else if (mundo.getPC(pcDTOs.connectionID) == null)
        {
            mundo.añadirPC(new PC(pcDTOs.connectionID, mundo));
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

    public void procesarActualizacionesProyectiles(DTOsCampoVision.ProyectilDTOs proyectilDTOs)
    {
        Object dto;

        for (int i=0; i < proyectilDTOs.listaDTOs.length; i++)
        {
            dto = proyectilDTOs.listaDTOs[i];

            if (dto instanceof DTOsCampoVision.DatosCompletosProyectil)
            {
                ProyectilI proyectil = ProyectilFactory.ESFERA.nuevo(mundo, ((DTOsCampoVision.DatosCompletosProyectil) dto).ancho, ((DTOsCampoVision.DatosCompletosProyectil) dto).alto)
                        .setID(((DTOsCampoVision.DatosCompletosProyectil) dto).ID)
                        .setSpell(((DTOsCampoVision.DatosCompletosProyectil) dto).spellID)
                        .setPosition(((DTOsCampoVision.DatosCompletosProyectil) dto).origenX, ((DTOsCampoVision.DatosCompletosProyectil) dto).origenY)
                        .setDireccionEnGrados(((DTOsCampoVision.DatosCompletosProyectil) dto).direccionEnGrados)
                        .setVelocidad(((DTOsCampoVision.DatosCompletosProyectil) dto).velocidad)
                        .setDuracion(((DTOsCampoVision.DatosCompletosProyectil) dto).duracionMax)
                        .build();
                proyectil.setDuracionActual(((DTOsCampoVision.DatosCompletosProyectil) dto).duracionActual);
                mundo.añadirProyectil(proyectil);
            }
        }
    }

    public void procesarActualizacionesMisc(DTOsCampoVision.MiscDTOs miscDTOs)
    {
        Object dto;

        for (int i=0; i < miscDTOs.listaDTOs.length; i++)
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
