package DB.Recursos.SkillRecursos.DAO;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import DB.Recursos.SkillRecursos.DTO.TipoSpellRecursos;
import View.Classes.Graficos.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface SkillRecursosDAO
{
    public TextureRegion getIcono (String nombreIcono);
    public Pixie getAnimacion (String nombreAnimacion);
    public TipoSpellRecursos getTipoSpellRecursos(String TipoSpellID);
    public SpellRecursos getSpellRecursos(String spellID);

    public void salvarIcono(String nombreIcono, String nombreTextura, TextureAtlas atlas);
    public void salvarAnimacionCasteo(String nombreAnimacion, String nombrePixie, TextureAtlas atlas);
    public void salvarAnimacionProyectil(String nombreAnimacion, String nombrePixie, TextureAtlas atlas);
    public void salvarTipoSpellRecursos(TipoSpellRecursos tipoSpellRecursos);
    public void salvarSpellRecursos(SpellRecursos spellRecursos);
}
