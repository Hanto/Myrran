package DB.Recursos.AccionRecursos.DTO;// Created by Hanto on 07/05/2014.

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AccionRecursos
{
    private String id;
    private String nombreTextura;
    private TextureRegion textura;

    public String getID()                           { return id; }
    public String getNombreTextura()                { return nombreTextura; }
    public TextureRegion getTextura()               { return textura; }

    public void setID(String id)                    { this.id = id; }
    public void setNombreTextura(String s)          { this.nombreTextura = s; }
    public void setTextura (TextureRegion textura)  { this.textura = textura; }

    public AccionRecursos(String ID, String nombreTextura, TextureRegion textura)
    {   this.id = ID; this.nombreTextura = nombreTextura; this.textura = textura; }
}
