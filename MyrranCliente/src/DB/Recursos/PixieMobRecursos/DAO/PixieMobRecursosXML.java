package DB.Recursos.PixieMobRecursos.DAO;// Created by Hanto on 13/08/2015.

import DB.Recursos.PixieMobRecursos.PixieMobRecursosXMLDB;
import View.Classes.Actores.Pixie;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PixieMobRecursosXML implements PixieMobRecursosDAO
{
    private Map<String, Pixie> listaPixeMobs;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public PixieMobRecursosXML(PixieMobRecursosXMLDB pixieMobRecursosXMLDB)
    {   listaPixeMobs = pixieMobRecursosXMLDB.getListaDeMobs(); }

    @Override public Pixie getPixieMob(String nombreMob)
    {
        if (!listaPixeMobs.containsKey(nombreMob))
        {   logger.error("ERROR: PixieMob con nombre: {} no existe", nombreMob); return null; }
        else return listaPixeMobs.get(nombreMob);
    }

    @Override public void salvarPixieMob(String nombreMob) {}
}
