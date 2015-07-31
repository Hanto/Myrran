package View.Gamestate.CampoVision;// Created by Hanto on 24/07/2015.

import Controller.Controlador;
import Model.Skills.SkillMod;
import DTO.DTOsCampoVision;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellPersonalizadoI;

import java.util.*;

public class BufferCampoVision
{
    //Buffer que almacena todos los cambios de las unidades observadas por el cambio de vision durante los updates
    //al final del update se envian todos los datos al player del campo de vision.

    private List<Object> listaDTOsMisc = new ArrayList<>();
    private MapDTOs mapaDTOsPC = new MapDTOs();

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
    }

    public BufferCampoVision() {}

    //PC:
    //------------------------------------------------------------------------------------------------------------------

    public void eliminarPC(PCI pc)
    {
        DTOsCampoVision.EliminarPC eliminar = new DTOsCampoVision.EliminarPC();
        mapaDTOsPC.remove(pc.getConnectionID());
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.EliminarPC.class, eliminar);
    }

    public void setPositionPC (PCI pc)
    {
        DTOsCampoVision.PosicionPC posicion = new DTOsCampoVision.PosicionPC(pc);
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.PosicionPC.class, posicion);
    }

    public void setNumAnimacionPC (PCI pc)
    {
        DTOsCampoVision.NumAnimacionPC numAnumacion = new DTOsCampoVision.NumAnimacionPC(pc);
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.NumAnimacionPC.class, numAnumacion);
    }

    //PC & PLAYER:
    //------------------------------------------------------------------------------------------------------------------

    public void setDatosCompletosPC (PCI pc)
    {
        DTOsCampoVision.DatosCompletosPC datosCompletos = new DTOsCampoVision.DatosCompletosPC(pc);
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.DatosCompletosPC.class, datosCompletos);

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
        DTOsCampoVision.NombrePC nombre = new DTOsCampoVision.NombrePC(pc);
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.NombrePC.class, nombre);
    }

    public void setHPsPC (PCI pc)
    {
        DTOsCampoVision.HPsPC hpsPC = new DTOsCampoVision.HPsPC(pc);
        mapaDTOsPC.set(pc.getConnectionID(), DTOsCampoVision.HPsPC.class, hpsPC);
    }

    public void addModificarHPsPC (PCI pc, float hps)
    {
        DTOsCampoVision.ModificarHPsPC modificarHPsPC = new DTOsCampoVision.ModificarHPsPC(hps);
        mapaDTOsPC.add(pc.getConnectionID(), modificarHPsPC);
    }

    public void addAñadirSpellPersonalizado(PCI pc, String spellID)
    {
        DTOsCampoVision.AñadirSpellPersonalizadoPC añadirSpellPersonalizado = new DTOsCampoVision.AñadirSpellPersonalizadoPC(spellID);
        mapaDTOsPC.add(pc.getConnectionID(), añadirSpellPersonalizado);
    }

    public void addNumTalentosSkillPersonalizadoPC(PCI pc, String skillID, int statID, int valor)
    {
        DTOsCampoVision.NumTalentosSkillPersonalizadoPC numTalentosSkill = new DTOsCampoVision.NumTalentosSkillPersonalizadoPC(skillID, statID, valor);
        mapaDTOsPC.add(pc.getConnectionID(), numTalentosSkill);
    }

    //MISC:
    //------------------------------------------------------------------------------------------------------------------

    public void addCambioTerreno (int tileX, int tileY, int numCapa, short iDTerreno)
    {
        DTOsCampoVision.CambioTerrenoMisc cambioTerreno = new DTOsCampoVision.CambioTerrenoMisc(tileX, tileY, numCapa, iDTerreno);
        listaDTOsMisc.add(cambioTerreno);
    }

    //ENVIAR DATOS A CLIENTE:
    //------------------------------------------------------------------------------------------------------------------

    private void enviarDTOsPC (Controlador controlador, int conID)
    {
        if (mapaDTOsPC.mapDTOs.size() > 0)
        {
            DTOsCampoVision.PCDTOs pcDTOs = new DTOsCampoVision.PCDTOs();
            for (Map.Entry<Integer, ArrayList<Object>> array : mapaDTOsPC.mapDTOs.entrySet())
            {
                pcDTOs.connectionID = array.getKey();
                pcDTOs.listaDTOs = new Object[array.getValue().size()];
                pcDTOs.listaDTOs = array.getValue().toArray();
                controlador.enviarACliente(conID, pcDTOs);
            }
            mapaDTOsPC.mapDTOs.clear();
        }
    }

    private void enviarDTOsMisc (Controlador controlador, int conID)
    {
        if (listaDTOsMisc.size() > 0)
        {
            DTOsCampoVision.MiscDTOs miscDTOs = new DTOsCampoVision.MiscDTOs();
            miscDTOs.listaDTOs = new Object[listaDTOsMisc.size()];
            miscDTOs.listaDTOs = listaDTOsMisc.toArray();
            controlador.enviarACliente(conID, miscDTOs);
            listaDTOsMisc.clear();
        }
    }

    public void enviarDTOS (Controlador controlador, int conID)
    {
        enviarDTOsPC(controlador, conID);
        enviarDTOsMisc(controlador, conID);
    }
}