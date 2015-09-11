package Controller;// Created by Hanto on 22/07/2014.

import DB.DAO;
import DTOs.DTOsNet;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.PlayerI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.UI.AccionI;
import Model.Classes.Acciones.AccionFactory;
import Model.Classes.Mobiles.Mob.MobFactory;
import Model.Classes.Mobiles.PC.PCFactory;
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

    public void procesarActualizacionesPC(DTOsNet.PCDTOs pcDTOs)
    {
        PCI pc;
        Object dto;

        if (pcDTOs.connectionID == mundo.getPlayer().getID()) pc = mundo.getPlayer();
        else if (mundo.getPC(pcDTOs.connectionID) == null)
        {
            pc = PCFactory.PCMODULAR.nuevo(pcDTOs.connectionID, mundo.getWorld());
            mundo.añadirPC(pc);
        }
        else pc = mundo.getPC(pcDTOs.connectionID);

        for (int i=0; i<pcDTOs.listaDTOs.length; i++)
        {
            dto = pcDTOs.listaDTOs[i];

            if (dto instanceof DTOsNet.Eliminar)
            {   mundo.eliminarPC(pcDTOs.connectionID); }

            else if (dto instanceof DTOsNet.Posicion)
            {   pc.setDireccion(((DTOsNet.Posicion) dto).posX, ((DTOsNet.Posicion) dto).posY); }

            else if (dto instanceof DTOsNet.NumAnimacion)
            {   pc.setNumAnimacion(((DTOsNet.NumAnimacion) dto).numAnimacion); }

            else if (dto instanceof DTOsNet.ModificarHPs)
            {   pc.modificarHPs(((DTOsNet.ModificarHPs) dto).HPs);}

            else if (dto instanceof DTOsNet.AñadirAura)
            {
                Caster caster;
                if (((DTOsNet.AñadirAura) dto).tipoCaster == 0)
                {
                    if (((DTOsNet.AñadirAura) dto).casteriD == mundo.getPlayer().getID())
                        caster = mundo.getPlayer();
                    else
                        caster = mundo.getPC(((DTOsNet.AñadirAura) dto).casteriD);
                }
                else
                {   caster = null;}

                int auraID = ((DTOsNet.AñadirAura) dto).auraID;
                BDebuffI debuff = DAO.debuffDAOFactory.getBDebuffDAO().getBDebuff(((DTOsNet.AñadirAura) dto).debuffID);

                pc.añadirAura(auraID, debuff, caster, pc);
            }

            else if (dto instanceof DTOsNet.ModificarAuraStacks)
            {   AuraI aura = pc.getAura(((DTOsNet.ModificarAuraStacks) dto).auraID);
                aura.setStacks(((DTOsNet.ModificarAuraStacks) dto).numStacks);
                aura.resetDuracion();
            }

            else if (dto instanceof DTOsNet.EliminarAura)
            {   pc.eliminarAura(((DTOsNet.EliminarAura) dto).auraID); }

            else if (dto instanceof DTOsNet.setHPs)
            {
                pc.setMaxHPs(((DTOsNet.setHPs) dto).maxHPs);
                pc.setActualHPs(((DTOsNet.setHPs) dto).actualHPs);
            }

            else if (dto instanceof DTOsNet.AñadirSpellPersonalizado)
            {
                SpellI spell = DAO.spellDAOFactory.getSpellDAO().getSpell(((DTOsNet.AñadirSpellPersonalizado) dto).spellID);
                if (spell == null) { logger.error("ERROR: no existe Spell con este ID: {}", ((DTOsNet.AñadirSpellPersonalizado) dto).spellID); return; }
                else
                {
                    pc.añadirSkillsPersonalizados(spell.getID());
                    AccionI accion = AccionFactory.accionSpell.SELECCIONARSPELL.nuevo(spell);
                    controlador.añadirAccion(accion);
                }
            }

            else if (dto instanceof DTOsNet.SetNumTalentosSkillPersonalizado)
            {
                String skillID = ((DTOsNet.SetNumTalentosSkillPersonalizado) dto).skillID;
                int statID = ((DTOsNet.SetNumTalentosSkillPersonalizado) dto).statID;
                int valor = ((DTOsNet.SetNumTalentosSkillPersonalizado) dto).valor;

                logger.debug("Modificado Spell: {} stat: {} talentos "+valor, skillID, statID);
                if (pc instanceof PlayerI) ((PlayerI) pc).setNumTalentosSkillPersonalizadoFromServer(skillID, statID, valor);
                else pc.setNumTalentosSkillPersonalizado(skillID, statID, valor);
            }

            else if (dto instanceof DTOsNet.DatosCompletosPC)
            {
                pc.setNumAnimacion(((DTOsNet.DatosCompletosPC) dto).numAnimacion);
                pc.setNombre(((DTOsNet.DatosCompletosPC) dto).nombre);
                pc.setMaxHPs(((DTOsNet.DatosCompletosPC) dto).maxHPs);
                pc.setActualHPs(((DTOsNet.DatosCompletosPC) dto).actualHPs);
                pc.setNivel(((DTOsNet.DatosCompletosPC) dto).nivel);
                pc.setVelocidadMax(((DTOsNet.DatosCompletosPC) dto).velocidadMax);
                pc.setPosition(((DTOsNet.DatosCompletosPC) dto).posX, ((DTOsNet.DatosCompletosPC) dto).posY);
            }

            else if (dto instanceof DTOsNet.NombrePC)
            {   pc.setNombre(((DTOsNet.NombrePC) dto).nombre);}
        }
    }

    public void procesarActualizacionesProyectiles(DTOsNet.ProyectilDTOs proyectilDTOs)
    {
        Object dto;

        for (int i=0; i < proyectilDTOs.listaDTOs.length; i++)
        {
            dto = proyectilDTOs.listaDTOs[i];

            if (dto instanceof DTOsNet.DatosCompletosProyectil)
            {
                ProyectilI proyectil = ProyectilFactory.NUEVOPROYECTIL.nuevo(mundo.getWorld(), ((DTOsNet.DatosCompletosProyectil) dto).ancho, ((DTOsNet.DatosCompletosProyectil) dto).alto)
                        .setID(proyectilDTOs.iD)
                        .setSpell(((DTOsNet.DatosCompletosProyectil) dto).spellID)
                        .setPosition(((DTOsNet.DatosCompletosProyectil) dto).origenX, ((DTOsNet.DatosCompletosProyectil) dto).origenY)
                        .setDireccionEnGrados(((DTOsNet.DatosCompletosProyectil) dto).direccionEnGrados)
                        .setVelocidad(((DTOsNet.DatosCompletosProyectil) dto).velocidad)
                        .setDuracion(((DTOsNet.DatosCompletosProyectil) dto).duracionMax)
                        .build();
                proyectil.setDuracionActual(((DTOsNet.DatosCompletosProyectil) dto).duracionActual);
                mundo.añadirProyectil(proyectil);
            }

            else if (dto instanceof DTOsNet.Eliminar)
            {   mundo.eliminarProyectil(proyectilDTOs.iD); }
        }
    }

    public void procesarActualizacionesMobs(DTOsNet.MobDTOs mobDTOs)
    {
        Object dto;

        MobI mob = mundo.getMob(mobDTOs.iD);
        if (mob == null)
        {
            mob = MobFactory.MOBMODULAR.nuevo(mobDTOs.iD, mundo.getWorld());
            mundo.añadirMob(mob);
        }

        for (int i=0; i < mobDTOs.listaDTOs.length; i++)
        {
            dto = mobDTOs.listaDTOs[i];

            if (dto instanceof DTOsNet.Posicion)
            {   mob.setDireccion(((DTOsNet.Posicion) dto).posX, ((DTOsNet.Posicion) dto).posY); }

            else if (dto instanceof DTOsNet.Orientacion)
            {   mob.setOrientacion(((DTOsNet.Orientacion) dto).orientacion);}

            else if (dto instanceof DTOsNet.ModificarHPs)
            {   mob.modificarHPs(((DTOsNet.ModificarHPs) dto).HPs);}

            else if (dto instanceof DTOsNet.AñadirAura)
            {
                Caster caster;
                if (((DTOsNet.AñadirAura) dto).tipoCaster == 0)
                {
                    if (((DTOsNet.AñadirAura) dto).casteriD == mundo.getPlayer().getID())
                        caster = mundo.getPlayer();
                    else
                        caster = mundo.getPC(((DTOsNet.AñadirAura) dto).casteriD);
                }
                else
                {   caster = null;}

                int auraID = ((DTOsNet.AñadirAura) dto).auraID;
                BDebuffI debuff = DAO.debuffDAOFactory.getBDebuffDAO().getBDebuff(((DTOsNet.AñadirAura) dto).debuffID);

                mob.añadirAura(auraID, debuff, caster, mob);
            }

            else if (dto instanceof DTOsNet.ModificarAuraStacks)
            {   AuraI aura = mob.getAura(((DTOsNet.ModificarAuraStacks) dto).auraID);
                aura.setStacks(((DTOsNet.ModificarAuraStacks) dto).numStacks);
                aura.resetDuracion();
            }

            else if (dto instanceof DTOsNet.EliminarAura)
            {   mob.eliminarAura(((DTOsNet.EliminarAura) dto).auraID); }

            else if (dto instanceof DTOsNet.DatosCompletosMob)
            {
                mob.setVelocidadMax(((DTOsNet.DatosCompletosMob) dto).velocidadMax);
                mob.setPosition(((DTOsNet.DatosCompletosMob) dto).posX, ((DTOsNet.DatosCompletosMob) dto).posY);
            }
        }
    }

    public void procesarActualizacionesMisc(DTOsNet.MiscDTOs miscDTOs)
    {
        Object dto;

        for (int i=0; i < miscDTOs.listaDTOs.length; i++)
        {
            dto = miscDTOs.listaDTOs[i];

            if (dto instanceof DTOsNet.CambioTerrenoMisc)
            {
                int tileX = ((DTOsNet.CambioTerrenoMisc) dto).tileX;
                int tileY = ((DTOsNet.CambioTerrenoMisc) dto).tileY;
                int numCapa = ((DTOsNet.CambioTerrenoMisc) dto).numCapa;
                short iDTerreno = ((DTOsNet.CambioTerrenoMisc) dto).iDTerreno;
                mundo.getMapa().setTerreno(tileX, tileY, numCapa, iDTerreno);
            }
            else if (dto instanceof DTOsNet.MapTilesAdyacentes)
            {   controlador.actualizarMapTilesAdyacentes(((DTOsNet.MapTilesAdyacentes) dto));}


        }
    }
}
