package DB.Recursos.AccionRecursos.DAO;// Created by Hanto on 07/05/2014.

import DB.Recursos.AccionRecursos.AccionRecursosXMLDB;
import DB.Recursos.AccionRecursos.DTO.AccionRecursos;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AccionRecursosXML implements AccionRecursosDAO
{
    private Map<String, TextureRegion> listaDeTexturasAccion;
    private Map<String, AccionRecursos> listaDeAccionRecursos;
    private AccionRecursosXMLDB accionRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public AccionRecursosXML(AccionRecursosXMLDB accionRecursosXMLDB)
    {
        this.accionRecursosXMLDB = accionRecursosXMLDB;
        this.listaDeAccionRecursos = accionRecursosXMLDB.getListaDeAccionRecursos();
        this.listaDeTexturasAccion = accionRecursosXMLDB.getListaDeTexturasAcciones();
    }



    @Override public TextureRegion getTextura(String nombreTextura)
    {   return listaDeTexturasAccion.get(nombreTextura); }

    @Override public AccionRecursos getAccionRecurso(String iDAccionRecurso)
    {   return listaDeAccionRecursos.get(iDAccionRecurso); }


    @Override public void salvarTextura(String nombreTextura, TextureRegion textura)
    {   listaDeTexturasAccion.put(nombreTextura, textura);
        accionRecursosXMLDB.salvarTexturasIconos();
    }

    @Override public void salvarTextura(String nombreTextura, String nombreTexturaEnAtlas, TextureAtlas atlas)
    {
        TextureRegion textura = new TextureRegion(atlas.findRegion(Settings.ATLAS_TexturasIconos_LOC +nombreTexturaEnAtlas));
        if (textura == null) { logger.error("No se encuentra textura {} en Atlas", nombreTexturaEnAtlas); return; }
        listaDeTexturasAccion.put(nombreTextura, textura);
        accionRecursosXMLDB.salvarTexturasIconos();
    }

    @Override public void salvarAccionRecurso(AccionRecursos accionRecurso)
    {   listaDeAccionRecursos.put(accionRecurso.getID(), accionRecurso);
        accionRecursosXMLDB.salvarAccionRecursos();
    }


}
