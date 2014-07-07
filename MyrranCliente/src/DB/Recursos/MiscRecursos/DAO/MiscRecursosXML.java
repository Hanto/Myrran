package DB.Recursos.MiscRecursos.DAO;// Created by Hanto on 02/05/2014.

import DB.Recursos.MiscRecursos.MiscRecursosXMLDB;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

public class MiscRecursosXML implements MiscRecursosDAO
{
    private Map<String, TextureRegion> listaDeTexturasMisc;
    private MiscRecursosXMLDB miscRecursosXMLDB;

    //CONSTRUCTOR:
    public MiscRecursosXML(MiscRecursosXMLDB miscRecursosXMLDB)
    {
        this.miscRecursosXMLDB = miscRecursosXMLDB;
        this.listaDeTexturasMisc = miscRecursosXMLDB.getListaDeTexturasMisc();
    }




    @Override public void salvarTextura(String nombreRecurso, String rutaYnombreTextura, TextureAtlas atlas)
    {
        TextureRegion textura = new TextureRegion(atlas.findRegion(rutaYnombreTextura));
        listaDeTexturasMisc.put(nombreRecurso, textura);
        miscRecursosXMLDB.salvarDatos();

    }

    @Override public TextureRegion cargarTextura(String nombreRecurso)
    {
        if (!listaDeTexturasMisc.containsKey(nombreRecurso))
        {   System.out.println("ERROR: Recurso Misc " + nombreRecurso + " no existe."); }
        return listaDeTexturasMisc.get(nombreRecurso);
    }
}
