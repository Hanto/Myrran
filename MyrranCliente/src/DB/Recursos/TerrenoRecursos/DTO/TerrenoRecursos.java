package DB.Recursos.TerrenoRecursos.DTO;// Created by Hanto on 30/04/2014.

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TerrenoRecursos
{
    private Integer id;
    private String nombreTextura;
    private TextureRegion textura;

    public int getID()                              { return id; }
    public String getNombreTextura()                { return nombreTextura; }
    public TextureRegion getTextura()               { return textura; }

    public void setID(int id)                       { this.id = id; }
    public void setNombreTextura(String s)          { this.nombreTextura = s; }
    public void setTextura (TextureRegion textura)  { this.textura = textura; }

    public TerrenoRecursos(int ID, String nombreTextura, TextureRegion textura)
    {   this.id = ID; this.nombreTextura = nombreTextura; this.textura = textura; }
}
