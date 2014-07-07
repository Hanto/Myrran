package DB.Recursos.TerrenoRecursos;// Created by Hanto on 15/04/2014.

import DB.RSC;
import DB.Recursos.TerrenoRecursos.DTO.TerrenoRecursos;
import Data.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerrenoRecursosLocalDB
{
    private static class Singleton              { private static final TerrenoRecursosLocalDB get = new TerrenoRecursosLocalDB(); }
    public static TerrenoRecursosLocalDB get()  { return Singleton.get; }

    public Map<String, TextureRegion> listaDeTexturasTerreno = new HashMap<>();
    public Map<Integer, TerrenoRecursos> listaDeTerrenosRecursos = new HashMap<>();
    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasTerrenos;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    private TerrenoRecursosLocalDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasTerrenos();
        cargarTerrenoRecursos();
    }


    public void cargarTexturasTerrenos()
    {
        logger.debug("Cargando datos desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(Settings.RECURSOS_XML + Settings.XML_TexturasTerrenos);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChild("TexturasTerrenos").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                TextureRegion textura = new TextureRegion(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_TexturasTerrenos_LOC + nombre));
                listaDeTexturasTerreno.put(nombre, textura);

                logger.trace("TexturaTerreno: " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTexturas, e); }
    }

    public void cargarTerrenoRecursos()
    {
        System.out.println("[CARGANDO TERRENOS RECURSOS]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(Settings.RECURSOS_XML + Settings.XML_DataTerrenos);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChildren("Terreno");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                short iD        = Short.parseShort(nodo.getAttributeValue("ID"));
                String nombreT  = nodo.getAttributeValue("nombreTextura");

                TerrenoRecursos terrenoRSC = new TerrenoRecursos(iD, listaDeTexturasTerreno.get(nombreT));
                listaDeTerrenosRecursos.put(terrenoRSC.getID(), terrenoRSC);

                System.out.println(" iD:             " + iD);
                System.out.println(" nombreTextura:  " + nombreT);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println("ERROR: con el fichero XML de datos de "+ Settings.XML_DataTerrenos+": "+e); }
    }

    public void salvarTexturasTerrenos()
    {
        logger.debug("Salvando datos en {}", ficheroTexturas);

        Element terrenoRoot;
        Element terreno;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren("TexturasTerrenos");
            terrenoRoot = new Element("TexturasTerrenos");

            for (Map.Entry<String, TextureRegion> entry: listaDeTexturasTerreno.entrySet())
            {
                terreno = new Element("Textura");
                terreno.setText(entry.getKey());
                terrenoRoot.addContent(terreno);
            }
            doc.getRootElement().addContent(terrenoRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}", ficheroTexturas);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}

