package DB.Recursos.TipoSkillRecursos.DTO;// Created by Hanto on 30/04/2014.

import DB.RSC;
import View.Classes.Actores.Pixie;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TipoSpellRecursos
{
    private String iDTipoSpell;
    private String nombreTexturaIcono;
    private TextureRegion icono;
    private List<List<String>> nombreTiposAnimacion;
    private List<List<Pixie>> tiposAnimacion;

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public String getIDTipoSpell()                              { return iDTipoSpell; }
    public String getNombreTexturaIcono()                       { return nombreTexturaIcono; }
    public TextureRegion getIcono()                             { return icono; }
    public int getNumTiposAnimacion()                           { return tiposAnimacion.size(); }
    public void setID(String id)                                { this.iDTipoSpell = id; }


    public TipoSpellRecursos(String iDTipoSpell, String nombreTexturaIcono, int numTiposAnimacion)
    {
        this.iDTipoSpell = iDTipoSpell;

        tiposAnimacion = new ArrayList<List<Pixie>>(numTiposAnimacion);
        for (int i = 0; i < numTiposAnimacion; i++)
        {   tiposAnimacion.add( new ArrayList<Pixie>()); }

        nombreTiposAnimacion = new ArrayList<>(numTiposAnimacion);
        for (int i = 0; i < numTiposAnimacion; i ++)
        {   nombreTiposAnimacion.add( new ArrayList<String>()); }

        setNombreTexturaIcono(nombreTexturaIcono);
    }

    public void setNombreTexturaIcono(String nombreTexturaIcono)
    {
        this.nombreTexturaIcono = nombreTexturaIcono;
        icono = RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono(nombreTexturaIcono);
    }

    public void addNombreAnimacion(int tipoAnimacion, String nombreAnimacion)
    {
        Pixie pixie = RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getAnimacion(nombreAnimacion);
        nombreTiposAnimacion.get(tipoAnimacion).add(nombreAnimacion);
        tiposAnimacion.get(tipoAnimacion).add(pixie);
    }

    public void setAnimacion(int tipoAnimacion, int numAnimacion, String nombreAnimacion)
    {
        Pixie pixie = RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getAnimacion(nombreAnimacion);
        if (nombreTiposAnimacion.get(tipoAnimacion).size()<numAnimacion)
            { logger.error("ERROR: Animacion no existe para ser sobrescrita: {}-{}", tipoAnimacion,numAnimacion ); return; }

        nombreTiposAnimacion.get(tipoAnimacion).set(numAnimacion, nombreAnimacion);
        tiposAnimacion.get(tipoAnimacion).set(numAnimacion, pixie);

    }

    public Pixie getAnimacion(int tipoAnimacion, int numAnimacion)
    {   return tiposAnimacion.get(tipoAnimacion).get(numAnimacion); }

    public String getNombreAnimacion(int tipoAnimacion, int numAnimacion)
    {   return nombreTiposAnimacion.get(tipoAnimacion).get(numAnimacion); }

}
