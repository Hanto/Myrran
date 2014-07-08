package DB.Recursos.PixiePCRecursos.DAO;// Created by Hanto on 01/05/2014.

import DB.RSC;
import DB.Recursos.PixiePCRecursos.DTO.PixieRecursos;
import DB.Recursos.PixiePCRecursos.PixiePCRecursosXMLDB;
import Data.Settings;
import View.Classes.Graficos.Pixie;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PixiePCRecursosXML implements PixiePCRecursosDAO
{
    private Map<String, PixiePCRecursosXMLDB.EquipoPC> listaDePCRazas;
    private PixiePCRecursosXMLDB pixiePCRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public PixiePCRecursosXML(PixiePCRecursosXMLDB pixiePCRecursosXMLDB)
    {
        this.pixiePCRecursosXMLDB = pixiePCRecursosXMLDB;
        this.listaDePCRazas = pixiePCRecursosXMLDB.getListaDePCRazas();
    }


    @Override public void salvarRazaPC(String iDRaza)
    {
        PixiePCRecursosXMLDB.EquipoPC equipoPC = new PixiePCRecursosXMLDB.EquipoPC();
        listaDePCRazas.put(iDRaza, equipoPC);
        pixiePCRecursosXMLDB.salvarRazas();
    }

    @Override public void salvarCuerpoPC(String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Cuerpo no existe", iDCuerpo); return; }

        PixieRecursos.PixieCuerpo pixieCuerpo = new PixieRecursos.PixieCuerpo(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeCuerpos.put(iDCuerpo, pixieCuerpo);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarCabezaPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Cabeza no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieCuerpo = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeCabezas.put(iDCuerpo, pixieCuerpo);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarYelmoPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Yelmo no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeYelmos.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarHombrerasPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Hombreras no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeHombreras.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarPetoPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Peto no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDePetos.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarPantalonesPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Pantalones no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDePantalones.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarGuantesPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Guantes no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeGuantes.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarBotasPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot Botas no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeBotas.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarCapasTraserasPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot CapaTrasera no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeCapasTraseras.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public void salvarCapasFrontalesPC (String iDRaza, String iDCuerpo)
    {
        if (RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(
            Settings.ATLAS_PixiePcCuerpos_LOC + iDCuerpo) == null)
        {   logger.error("Textura {} para slot CapaFrontal no existe", iDCuerpo); return; }

        PixieRecursos.PixieSlot pixieSlot = new PixieRecursos.PixieSlot(iDCuerpo);
        if (listaDePCRazas.containsKey(iDRaza))
        {
            listaDePCRazas.get(iDRaza).listaDeCapasFrontales.put(iDCuerpo, pixieSlot);
            pixiePCRecursosXMLDB.salvarSlot(iDCuerpo);
        }
        else logger.error("Raza {} no existe", iDRaza);
    }

    @Override public Pixie getCuerpoPC (String iDRaza, String iDCuerpo)
    {   return listaDePCRazas.get(iDRaza).listaDeCuerpos.get(iDCuerpo); }

    @Override public Pixie getCabezaPC(String iDRaza, String iDCabeza)
    {   return listaDePCRazas.get(iDRaza).listaDeCabezas.get(iDCabeza); }

    @Override public Pixie getYelmoPC(String iDRaza, String iDYelmo)
    {   return listaDePCRazas.get(iDRaza).listaDeYelmos.get(iDYelmo); }

    @Override public Pixie getHombrerasPC(String iDRaza, String iDHombreras)
    {   return listaDePCRazas.get(iDRaza).listaDeHombreras.get(iDHombreras); }

    @Override public Pixie getPetoPC(String iDRaza, String iDPeto)
    {   return listaDePCRazas.get(iDRaza).listaDePetos.get(iDPeto); }

    @Override public Pixie getPantalonesPC(String iDRaza, String iDPantalones)
    {   return listaDePCRazas.get(iDRaza).listaDePantalones.get(iDPantalones); }

    @Override public Pixie getGuantesPC(String iDRaza, String iDGuantes)
    {   return listaDePCRazas.get(iDRaza).listaDeGuantes.get(iDGuantes); }

    @Override public Pixie getBotasPC(String iDRaza, String iDGuantes)
    {   return listaDePCRazas.get(iDRaza).listaDeBotas.get(iDGuantes); }

    @Override public Pixie getCapaTraseraPC(String iDRaza, String iDCapaTrasera)
    {   return listaDePCRazas.get(iDRaza).listaDeCapasTraseras.get(iDCapaTrasera); }

    @Override public Pixie getCapaFrontalPC(String iDRaza, String iDCapaFrontal)
    {   return listaDePCRazas.get(iDRaza).listaDeCapasFrontales.get(iDCapaFrontal); }
}
