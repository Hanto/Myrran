package DB.Recursos.SkillBaseRecursos.DAO;// Created by Hanto on 05/08/2015.

import View.Classes.Actores.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface SkillBaseRecursosDAO
{
    public TextureRegion getIcono (String nombreIcono);
    public Pixie getAnimacion (String nombreAnimacion);

    public void salvarIcono(String nombreIcono, String nombreTextura, TextureAtlas atlas);
    public void salvarAnimacionCasteo(String nombreAnimacion, String nombrePixie, TextureAtlas atlas);
    public void salvarAnimacionProyectil(String nombreAnimacion, String nombrePixie, TextureAtlas atlas);
}
