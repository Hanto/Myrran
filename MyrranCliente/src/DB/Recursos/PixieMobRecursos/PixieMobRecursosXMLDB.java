package DB.Recursos.PixieMobRecursos;// Created by Hanto on 13/08/2015.

import DB.RSC;
import Model.Settings;
import View.Classes.Actores.Pixie;
import ch.qos.logback.classic.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixieMobRecursosXMLDB
{
    private static class Singleton              { private static final PixieMobRecursosXMLDB get = new PixieMobRecursosXMLDB(); }
    public static PixieMobRecursosXMLDB get()   { return Singleton.get; }

    private Map<String, Pixie> listaDeMobs = new HashMap<>();
    private String ficheroMobs = Settings.RECURSOS_XML + Settings.XML_PixieMob;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, Pixie>getListaDeMobs()   { return listaDeMobs; }

    private PixieMobRecursosXMLDB()
    {
        cargarPixiesMobs();
    }

    public void cargarPixiesMobs()
    {
        logger.info("Cargando [PIXIES DE MOBS] desde {}", ficheroMobs);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroMobs);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Mob");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);
                String nombre = nodo.getAttributeValue("pixie");

                Pixie pixie = new Pixie(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_PixieMObs_LOC + nombre),
                                        4, 3, 3, 0.15f);
                listaDeMobs.put(nombre, pixie);

                logger.trace("PixieMob: " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroMobs, e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
