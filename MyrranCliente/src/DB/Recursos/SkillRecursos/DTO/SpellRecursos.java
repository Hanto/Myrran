package DB.Recursos.SkillRecursos.DTO;// Created by Hanto on 30/04/2014.

import View.Classes.Actores.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpellRecursos
{
    private String id;
    private String nombreTexturaIcono;
    private TextureRegion icono;
    private Pixie[] tipoAnimacion;

    public String getID()                                       { return id; }
    public String getNombreTexturaIcono()                       { return nombreTexturaIcono; }
    public TextureRegion getIcono()                             { return icono; }
    public Pixie getTipoAnimacion(int tipoAnimacion)            { return this.tipoAnimacion[tipoAnimacion]; }

    public void setID(String id)                                { this.id = id; }
    public void setNombreTexturaIcono(String s)                 { this.nombreTexturaIcono = s; }
    public void setIcono(TextureRegion icono)                   { this.icono = icono; }
    public void setTipoAnimacion(int tipoAnimacion, Pixie pixie){ this.tipoAnimacion[tipoAnimacion] = pixie; }

    public SpellRecursos(String iDSpell, String nombreTexturaIcono, TextureRegion icono)
    {   id = iDSpell; this.nombreTexturaIcono = nombreTexturaIcono; this.icono = icono; }
}
