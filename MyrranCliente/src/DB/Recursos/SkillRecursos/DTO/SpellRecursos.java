package DB.Recursos.SkillRecursos.DTO;// Created by Hanto on 30/04/2014.

import DB.RSC;
import View.Classes.Actores.Pixie;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpellRecursos
{
    private String iDSpell;
    private String iDTipoSpell;
    private String nombreTexturaIcono;
    private TextureRegion icono;
    private int[] numeroAnimacion;
    private Pixie[] animacion;

    public void setID(String id)                                { this.iDSpell = id; }
    public String getIDSpell()                                  { return iDSpell; }
    public String getNombreTexturaIcono()                       { return nombreTexturaIcono; }
    public TextureRegion getIcono()                             { return icono; }


    public SpellRecursos(String iDSpell, String iDTipoSpell, String nombreTexturaIcono)
    {
        this.iDSpell = iDSpell;
        this.iDTipoSpell = iDTipoSpell;
        this.nombreTexturaIcono = nombreTexturaIcono;

        int numTiposAnimacion = RSC.tipoSkillRecursosDAO.getTipoSkillRecursosDAO().getTipoSpellRecursos(iDTipoSpell).getNumTiposAnimacion();

        this.numeroAnimacion = new int[numTiposAnimacion];
        this.animacion = new Pixie[numTiposAnimacion];

        setNombreTexturaIcono(nombreTexturaIcono);
    }

    public void setNombreTexturaIcono(String nombreTexturaIcono)
    {
        this.nombreTexturaIcono = nombreTexturaIcono;
        icono = RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono(nombreTexturaIcono);
    }

    public void setNumeroAnimacion(int tipoAnimacion, int numAnimacion)
    {
        Pixie pixie = RSC.tipoSkillRecursosDAO.getTipoSkillRecursosDAO().getTipoSpellRecursos(iDTipoSpell).getAnimacion(tipoAnimacion, numAnimacion);
        animacion[tipoAnimacion] = pixie;
        numeroAnimacion[tipoAnimacion] = numAnimacion;
    }

    public int getNumeroAnimacion(int tipoAnimacion)
    {   return numeroAnimacion[tipoAnimacion]; }

    public Pixie getAnimacion (int tipoAnimacion)
    {   return animacion[tipoAnimacion]; }

}
