package DB.Recursos.SkillRecursos.DAO;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.SkillRecursosXMLDB;
import Model.Settings;
import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import DB.Recursos.SkillRecursos.DTO.TipoSpellRecursos;
import View.Classes.Actores.Pixie;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SkillRecursosXML implements SkillRecursosDAO
{
    private Map<String, TextureRegion> listaDeTexturasIconosSpells;
    private Map<String, Pixie> listaDeAnimaciones;
    private Map<String, TipoSpellRecursos> listaTipoSpell;
    private Map<String, SpellRecursos> listaSpell;

    private SkillRecursosXMLDB skillRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public SkillRecursosXML(SkillRecursosXMLDB skillRecursosXMLDB)
    {
        this.skillRecursosXMLDB = skillRecursosXMLDB;
        this.listaDeTexturasIconosSpells = skillRecursosXMLDB.getListaDeTexturasIconosSpells();
        this.listaDeAnimaciones = skillRecursosXMLDB.getListaDeAnimaciones();
        this.listaTipoSpell = skillRecursosXMLDB.getListaTipoSpell();
        this.listaSpell = skillRecursosXMLDB.getListaSpell();
    }




    @Override public TextureRegion getIcono (String nombreIcono)
    {   return listaDeTexturasIconosSpells.get(nombreIcono); }

    @Override public Pixie getAnimacion (String nombreAnimacion)
    {   return listaDeAnimaciones.get(nombreAnimacion); }

    @Override public TipoSpellRecursos getTipoSpellRecursos(String tipoSpellID)
    {   return listaTipoSpell.get(tipoSpellID);}

    @Override public SpellRecursos getSpellRecursos(String spellID)
    {   return listaSpell.get(spellID); }



    @Override public void salvarIcono(String nombreIcono, String nombreTextura, TextureAtlas atlas)
    {
        TextureRegion textura = new TextureRegion(atlas.findRegion(Settings.ATLAS_TexturasIconos_LOC +nombreTextura));
        if (textura == null) { logger.error("Textura Icono {} no existe en Atlas", nombreTextura); return; }
        listaDeTexturasIconosSpells.put(nombreIcono, textura);
        skillRecursosXMLDB.salvarTexturasIconos();
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

    @Override public void salvarTipoSpellRecursos(TipoSpellRecursos tipoSpellRecursos)
    {   listaTipoSpell.put(tipoSpellRecursos.getID(), tipoSpellRecursos); }

    @Override public void salvarSpellRecursos(SpellRecursos spellRecursos)
    {   listaSpell.put(spellRecursos.getID(), spellRecursos);
        skillRecursosXMLDB.salvarSpellRecursos();
    }


}
