package View.Gamestate.CampoVision;// Created by Hanto on 24/07/2015.

import DTOs.DTOsNet;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.Misc.Geo.MapaI;
import Interfaces.Misc.Network.ServidorI;
import Interfaces.Misc.Spell.AuraI;
import Interfaces.Misc.Spell.SkillPersonalizadoI;
import Interfaces.Misc.Spell.SpellPersonalizadoI;
import Model.Settings;
import Model.Skills.SkillsPersonalizados.SkillMod;

import java.util.*;

public class BufferCampoVision
{
    //Buffer que almacena todos los cambios de las unidades observadas por el cambio de vision durante los updates
    //al final del update se envian todos los datos al player del campo de vision.

    private List<DTOsNet.Mapa> listaDTOsMapa = new ArrayList<>();
    private List<Object> listaDTOsMisc = new ArrayList<>();
    private MapDTOs mapaDTOsPC = new MapDTOs();
    private MapDTOs mapaDTOsProyectiles = new MapDTOs();
    private MapDTOs mapaDTOsMobs = new MapDTOs();

    //DTOs para no tener que crearlos sin parar:
    private DTOsNet.PCDTOs pcDTOs = new DTOsNet.PCDTOs();
    private DTOsNet.ProyectilDTOs proyectilDTOs = new DTOsNet.ProyectilDTOs();
    private DTOsNet.MobDTOs mobDTOs = new DTOsNet.MobDTOs();
    private DTOsNet.MiscDTOs miscDTOs = new DTOsNet.MiscDTOs();


    //MANIPULACION DE LAS ESTRUCTURAS DE DATOS:
    //---------------------------------------------------------------------------------------------------------------

    private class MapDTOs
    {
        public Map<Integer, ArrayList<Object>> mapDTOs = new HashMap<>();

        public void set(Integer key, Class clase, Object dto)
        {
            if (mapDTOs.containsKey(key))
            {
                List<Object> lista = mapDTOs.get(key);
                for (int i=0; i< lista.size(); i++)
                {
                    if (clase.isInstance(lista.get(i)))
                    {   lista.set(i, dto); return; }
                }
                lista.add(dto);
            }
            else
            {
                ArrayList<Object> arrayDTOs = new ArrayList<>();
                arrayDTOs.add(dto);
                mapDTOs.put(key, arrayDTOs);
            }
        }

        public void add(Integer key, Object dto)
        {
            if (mapDTOs.containsKey(key))
            {   mapDTOs.get(key).add(dto); }
            else
            {
                ArrayList<Object> arrayDTOs = new ArrayList<>();
                arrayDTOs.add(dto);
                mapDTOs.put(key, arrayDTOs);
            }
        }

        public void remove(Integer key)
        {   mapDTOs.remove(key); }

        public void clear()
        {   mapDTOs.clear(); }
    }

    public BufferCampoVision() {}

    // PC:
    //------------------------------------------------------------------------------------------------------------------

    public void eliminarPC(PCI pc)
    {
        DTOsNet.Eliminar eliminar = new DTOsNet.Eliminar();
        mapaDTOsPC.remove(pc.getID());
        mapaDTOsPC.set(pc.getID(), DTOsNet.Eliminar.class, eliminar);
    }

    public void setPositionPC (PCI pc)
    {
        DTOsNet.Posicion posicion = new DTOsNet.Posicion(pc);
        mapaDTOsPC.set(pc.getID(), DTOsNet.Posicion.class, posicion);
    }

    public void setNumAnimacionPC (PCI pc)
    {
        DTOsNet.NumAnimacion numAnimacion = new DTOsNet.NumAnimacion(pc);
        mapaDTOsPC.set(pc.getID(), DTOsNet.NumAnimacion.class, numAnimacion);
    }

    // PC & PLAYER:
    //------------------------------------------------------------------------------------------------------------------

    public void setDatosCompletosPC (PCI pc)
    {
        DTOsNet.DatosCompletosPC datosCompletosPC = new DTOsNet.DatosCompletosPC(pc);
        mapaDTOsPC.set(pc.getID(), DTOsNet.DatosCompletosPC.class, datosCompletosPC);

        Iterator<SpellPersonalizadoI> iteratorSpell = pc.getIteratorSpellPersonalizado();
        while (iteratorSpell.hasNext())
        {   addAñadirSpellPersonalizado(pc, iteratorSpell.next().getID()); }

        Iterator<SkillPersonalizadoI> iteratorSkill = pc.getIteratorSkillPersonalizado();
        while (iteratorSkill.hasNext())
        {
            SkillPersonalizadoI skill = iteratorSkill.next();
            Iterator<SkillMod> iteratorMod = skill.getIterator();
            while(iteratorMod.hasNext())
            {
                SkillMod skillmod = iteratorMod.next();
                if (skillmod.getNumTalentos()>0)
                {   addNumTalentosSkillPersonalizadoPC(pc, skill.getID(), skillmod.getID(), skillmod.getNumTalentos()); }
            }
        }
    }

    public void setNombrePC (PCI pc)
    {
        DTOsNet.NombrePC nombre = new DTOsNet.NombrePC(pc);
        mapaDTOsPC.set(pc.getID(), DTOsNet.NombrePC.class, nombre);
    }

    public void setHPsPC (PCI pc)
    {
        DTOsNet.setHPs hpsSet = new DTOsNet.setHPs(pc);
        mapaDTOsPC.set(pc.getID(), DTOsNet.setHPs.class, hpsSet);
    }

    public void addModificarHPsPC (PCI pc, float hps)
    {
        DTOsNet.ModificarHPs modificarHPs = new DTOsNet.ModificarHPs(hps);
        mapaDTOsPC.add(pc.getID(), modificarHPs);
    }

    public void addAñadirSpellPersonalizado(PCI pc, String spellID)
    {
        DTOsNet.AñadirSpellPersonalizado añadirSpellPersonalizado = new DTOsNet.AñadirSpellPersonalizado(spellID);
        mapaDTOsPC.add(pc.getID(), añadirSpellPersonalizado);
    }

    public void addNumTalentosSkillPersonalizadoPC(PCI pc, String skillID, int statID, int valor)
    {
        DTOsNet.SetNumTalentosSkillPersonalizado setNumTalentosSkillPersonalizado = new DTOsNet.SetNumTalentosSkillPersonalizado(skillID, statID, valor);
        mapaDTOsPC.add(pc.getID(), setNumTalentosSkillPersonalizado);
    }

    public void addAñadirAura(PCI pc, AuraI aura)
    {
        DTOsNet.AñadirAura añadirAura = new DTOsNet.AñadirAura(aura);
        mapaDTOsPC.add(pc.getID(), añadirAura);
    }

    public void addEliminarAura(PCI pc, AuraI aura)
    {
        DTOsNet.EliminarAura eliminarAura = new DTOsNet.EliminarAura(aura);
        mapaDTOsPC.add(pc.getID(), eliminarAura);
    }

    public void addAuraStacks(PCI pc, AuraI aura)
    {
        DTOsNet.ModificarAuraStacks modificarAuraStacks = new DTOsNet.ModificarAuraStacks(aura);
        mapaDTOsPC.add(pc.getID(), modificarAuraStacks);
    }

    // MOBS:
    //------------------------------------------------------------------------------------------------------------------

    public void setDatosCompletosMob (MobI mob)
    {
        setPosicionMob(mob);

        AuraI aura;
        Iterator<AuraI>iterator = mob.getAuras();
        while (iterator.hasNext())
        {
            aura = iterator.next();
            addAñadirAura(mob, aura);
        }
    }

    public void setPosicionMob(MobI mob)
    {
        DTOsNet.Posicion posicionMob = new DTOsNet.Posicion(mob);
        mapaDTOsMobs.add(mob.getID(), posicionMob);
    }

    public void setOrientacionMob(MobI mob)
    {
        DTOsNet.Orientacion orientacion = new DTOsNet.Orientacion(mob);
        mapaDTOsMobs.add(mob.getID(), orientacion);
    }

    public void addModificarHpsMob (MobI mob, float hps)
    {
        DTOsNet.ModificarHPs modificarHPsMob = new DTOsNet.ModificarHPs(hps);
        mapaDTOsMobs.add(mob.getID(), modificarHPsMob);
    }

    public void addAñadirAura(MobI mob, AuraI aura)
    {
        DTOsNet.AñadirAura añadirAura = new DTOsNet.AñadirAura(aura);
        mapaDTOsMobs.add(mob.getID(), añadirAura);
    }

    public void addEliminarAura(MobI mob, AuraI aura)
    {
        DTOsNet.EliminarAura eliminarAura = new DTOsNet.EliminarAura(aura);
        mapaDTOsMobs.add(mob.getID(), eliminarAura);
    }

    public void addAuraStacks(MobI mob, AuraI aura)
    {
        DTOsNet.ModificarAuraStacks modificarAuraStacks = new DTOsNet.ModificarAuraStacks(aura);
        mapaDTOsMobs.add(mob.getID(), modificarAuraStacks);
    }

    // PROYECTILES:
    //------------------------------------------------------------------------------------------------------------------

    public void setDatosCompletosProyectil (ProyectilI proyectil)
    {
        DTOsNet.DatosCompletosProyectil datosCompletos = new DTOsNet.DatosCompletosProyectil(proyectil);
        mapaDTOsProyectiles.set(proyectil.getID(), DTOsNet.DatosCompletosProyectil.class, datosCompletos);
    }

    public void eliminarProyectil (ProyectilI proyectil)
    {
        DTOsNet.Eliminar eliminarProyectil = new DTOsNet.Eliminar();
        mapaDTOsProyectiles.remove(proyectil.getID());
        mapaDTOsProyectiles.set(proyectil.getID(), DTOsNet.Eliminar.class, eliminarProyectil);
    }

    // MISC:
    //------------------------------------------------------------------------------------------------------------------

    public void addCambioTerreno (int tileX, int tileY, int numCapa, short iDTerreno)
    {
        DTOsNet.CambioTerrenoMisc cambioTerreno = new DTOsNet.CambioTerrenoMisc(tileX, tileY, numCapa, iDTerreno);
        listaDTOsMisc.add(cambioTerreno);
    }

    public void addMapaAdyacencias(boolean[][] mapaAdyacencias)
    {
        DTOsNet.MapTilesAdyacentes mapTilesAdyacentes = new DTOsNet.MapTilesAdyacentes(mapaAdyacencias);
        listaDTOsMisc.add(mapTilesAdyacentes);
    }

    // MAPAS:
    //------------------------------------------------------------------------------------------------------------------

    public void addMapa(MapaI mapa, int esquinaInfIzdaX, int esquinaInfIzdaY, int ancho, int alto)
    {
        DTOsNet.Mapa mapaDTO = new DTOsNet.Mapa(esquinaInfIzdaX, esquinaInfIzdaY, ancho, alto);
        for (int x=0; x< ancho; x++)
        {
            for (int y = 0; y< alto; y++)
            {
                for (int i=0; i< Settings.MAPA_Max_Capas_Terreno; i++)
                {   mapaDTO.mapa[x][y].celda[i] = mapa.getTerrenoID(x + esquinaInfIzdaX, y + esquinaInfIzdaY, i); }
            }
        }
        listaDTOsMapa.add(mapaDTO);
    }

    //ENVIAR DATOS A CLIENTE:
    //------------------------------------------------------------------------------------------------------------------

    private void enviarDTOsPC (ServidorI servidor, int conID)
    {
        if (mapaDTOsPC.mapDTOs.size() > 0)
        {
            //DTOsCampoVision.PCDTOs pcDTOs = new DTOsCampoVision.PCDTOs();
            for (Map.Entry<Integer, ArrayList<Object>> array : mapaDTOsPC.mapDTOs.entrySet())
            {
                pcDTOs.connectionID = array.getKey();
                pcDTOs.listaDTOs = new Object[array.getValue().size()];
                pcDTOs.listaDTOs = array.getValue().toArray();
                servidor.enviarACliente(conID, pcDTOs);
            }
            mapaDTOsPC.clear();
        }
    }

    private void enviarDTOsProyectil (ServidorI servidor, int conID)
    {
        if (mapaDTOsProyectiles.mapDTOs.size() > 0)
        {
            //DTOsCampoVision.ProyectilDTOs proyectilDTOs = new DTOsCampoVision.ProyectilDTOs();
            for (Map.Entry<Integer, ArrayList<Object>> array : mapaDTOsProyectiles.mapDTOs.entrySet())
            {
                proyectilDTOs.iD = array.getKey();
                proyectilDTOs.listaDTOs = new Object[array.getValue().size()];
                proyectilDTOs.listaDTOs = array.getValue().toArray();
                servidor.enviarACliente(conID, proyectilDTOs);
            }
            mapaDTOsProyectiles.clear();
        }
    }

    private void enviarDTOsMobs (ServidorI servidor, int conID)
    {
        if (mapaDTOsMobs.mapDTOs.size() > 0)
        {
            //DTOsCampoVision.MobDTOs mobDTOs = new DTOsCampoVision.MobDTOs();
            for (Map.Entry<Integer, ArrayList<Object>> array : mapaDTOsMobs.mapDTOs.entrySet())
            {
                mobDTOs.iD = array.getKey();
                mobDTOs.listaDTOs = new Object[array.getValue().size()];
                mobDTOs.listaDTOs = array.getValue().toArray();
                servidor.enviarACliente(conID, mobDTOs);
            }
            mapaDTOsMobs.clear();
        }
    }

    private void enviarDTOsMisc (ServidorI servidor, int conID)
    {
        if (listaDTOsMisc.size() > 0)
        {
            //DTOsCampoVision.MiscDTOs miscDTOs = new DTOsCampoVision.MiscDTOs();
            miscDTOs.listaDTOs = new Object[listaDTOsMisc.size()];
            miscDTOs.listaDTOs = listaDTOsMisc.toArray();
            servidor.enviarACliente(conID, miscDTOs);
            listaDTOsMisc.clear();
        }
    }

    private void enviarDTOsMapa(ServidorI servidor, int conID)
    {
        if (listaDTOsMapa.size() > 0)
        {
            for (DTOsNet.Mapa mapa : listaDTOsMapa)
            {   servidor.enviarACliente(conID, mapa); }
            listaDTOsMapa.clear();
        }
    }

    public void enviarDTOS (ServidorI servidor, int conID)
    {
        enviarDTOsMapa(servidor, conID);
        enviarDTOsPC(servidor, conID);
        enviarDTOsProyectil(servidor, conID);
        enviarDTOsMobs(servidor, conID);
        enviarDTOsMisc(servidor, conID);
    }
}
