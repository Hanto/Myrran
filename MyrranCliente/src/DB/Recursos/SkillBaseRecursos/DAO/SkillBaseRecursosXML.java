package DB.Recursos.SkillBaseRecursos.DAO;// Created by Hanto on 05/08/2015.

import DB.Recursos.SkillBaseRecursos.SkillBaseRecursosXMLDB;
import Model.Settings;
import View.Classes.Actores.Pixie;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SkillBaseRecursosXML implements SkillBaseRecursosDAO
{
    private Map<String, TextureRegion> listaDeTexturasIconosSpells;
    private Map<String, Pixie> listaDeAnimaciones;

    private SkillBaseRecursosXMLDB skillBaseRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public SkillBaseRecursosXML(SkillBaseRecursosXMLDB skillBaseRecursosXMLDB)
    {
        this.skillBaseRecursosXMLDB = skillBaseRecursosXMLDB;
        this.listaDeTexturasIconosSpells = skillBaseRecursosXMLDB.getListaDeTexturasIconosSpells();
        this.listaDeAnimaciones = skillBaseRecursosXMLDB.getListaDeAnimaciones();
    }

    @Override public TextureRegion getIcono (String nombreIcono)
    {
        if (!listaDeTexturasIconosSpells.containsKey(nombreIcono))
            { logger.error("ERROR: Textura {} no existe", nombreIcono); return null; }
        else return listaDeTexturasIconosSpells.get(nombreIcono);
    }

    @Override public Pixie getAnimacion (String nombreAnimacion)
    {
        if (!listaDeAnimaciones.containsKey(nombreAnimacion))
            { logger.error("ERROR: Textura {} no existe", nombreAnimacion); return null;  }
        else return listaDeAnimaciones.get(nombreAnimacion);
    }

    @Override public void salvarIcono(String nombreIcono, String nombreTextura, TextureAtlas atlas)
    {
        TextureRegion textura = new TextureRegion(atlas.findRegion(Settings.ATLAS_TexturasIconos_LOC +nombreTextura));
        if (textura == null) { logger.error("Textura Icono {} no existe en Atlas", nombreTextura); return; }
        listaDeTexturasIconosSpells.put(nombreIcono, textura);
        skillBaseRecursosXMLDB.salvarTexturasIconos();
    }

    @Override public void salvarAnimacionCasteo(String nombreAnimacion, String nombrePixie, TextureAtlas atlas)
    {
        TextureRegion textura = atlas.findRegion(Settings.ATLAS_AnimacionesSpells_LOC +nombrePixie);
        if (textura == null ) { logger.error("Textura Animacion {} no existe en Atlas", nombreAnimacion); return; }
        Pixie pixie = new Pixie(textura,1,3);
        pixie.añadirAnimacion("Casteo", new int[]{0, 1, 2}, 0.15f, false);
        pixie.animaciones().get(0).animarYEliminar = true;
        listaDeAnimaciones.put(nombreAnimacion, pixie);
    }

    @Override public void salvarAnimacionProyectil(String nombreAnimacion, String nombrePixie, TextureAtlas atlas)
    {
        TextureRegion textura = atlas.findRegion(Settings.ATLAS_AnimacionesSpells_LOC +nombrePixie);
        if (textura == null ) { logger.error("Textura Animacion {} no existe en Atlas", nombreAnimacion); return; }
        Pixie pixie = new Pixie(textura,1,3);
        pixie.añadirAnimacion("Proyectil", new int[]{0, 1, 2}, 0.15f, false);
        listaDeAnimaciones.put(nombreAnimacion, pixie);
    }
}
