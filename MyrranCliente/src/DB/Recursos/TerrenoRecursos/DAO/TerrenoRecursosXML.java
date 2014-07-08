package DB.Recursos.TerrenoRecursos.DAO;// Created by Ladrim on 24/04/2014.

import DB.Recursos.TerrenoRecursos.DTO.TerrenoRecursos;
import DB.Recursos.TerrenoRecursos.TerrenoRecursosXMLDB;
import Data.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TerrenoRecursosXML implements TerrenoRecursosDAO
{
    private Map<String, TextureRegion> listaDeTexturasTerreno;
    private Map<Integer, TerrenoRecursos> listaDeTerrenosRecursos;
    private TerrenoRecursosXMLDB terrenoRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public TerrenoRecursosXML(TerrenoRecursosXMLDB terrenoRecursosXMLDB)
    {
        this.terrenoRecursosXMLDB = terrenoRecursosXMLDB;
        this.listaDeTexturasTerreno = terrenoRecursosXMLDB.getListaDeTexturasTerreno();
        this.listaDeTerrenosRecursos = terrenoRecursosXMLDB.getListaDeTerrenosRecursos();
    }


    @Override public TextureRegion getTextura(String nombreTextura)
    {   return listaDeTexturasTerreno.get(nombreTextura); }

    @Override public TerrenoRecursos getTerrenoRecurso(int iDTerreno)
    {   return listaDeTerrenosRecursos.get(iDTerreno); }

    @Override public void salvarTextura(String nombreTextura, TextureRegion textura)
    {   listaDeTexturasTerreno.put(nombreTextura, textura);
        terrenoRecursosXMLDB.salvarTexturasTerrenos();
    }

    @Override public void salvarTextura(String nombreTextura, String nombreTexturaEnAtlas, TextureAtlas atlas)
    {
        TextureRegion textura = new TextureRegion(atlas.findRegion(Settings.ATLAS_TexturasTerrenos_LOC +nombreTexturaEnAtlas));
        if (textura == null) { logger.error("Textura terreno {} no existe en Atlas", nombreTexturaEnAtlas); return; }
        listaDeTexturasTerreno.put(nombreTextura, textura);
        terrenoRecursosXMLDB.salvarTexturasTerrenos();
    }

    @Override public void salvarTerrenoRecurso(TerrenoRecursos terreno)
    {   listaDeTerrenosRecursos.put(terreno.getID(), terreno);
        terrenoRecursosXMLDB.salvarTerrenoRecursos();
    }
}
