package DB.Recursos.AccionRecursos;// Created by Hanto on 07/05/2014.

import Core.AbrirFichero;
import DB.RSC;
import DB.Recursos.AccionRecursos.DTO.AccionRecursos;
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

public class AccionRecursosLocalDB
{
    private static class Singleton              { private static final AccionRecursosLocalDB get = new AccionRecursosLocalDB(); }
    public static AccionRecursosLocalDB get()   { return Singleton.get; }

    public Map<String, TextureRegion> listaDeTexturasAcciones = new HashMap<>();
    public Map<String, AccionRecursos> listaDeAccionRecursos = new HashMap<>();

    private String ficheroTexturas = Settings.RECURSOS_XML + Settings.XML_TexturasIconosAcciones;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());


    private AccionRecursosLocalDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasIconos();
        cargarAccionRecursos();
    }


    public void cargarTexturasIconos()
    {
        logger.debug("Cargando datos desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroTexturas);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChild("TexturasAcciones").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                TextureRegion textura = new TextureRegion(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_TexturasIconos_LOC + nombre));
                listaDeTexturasAcciones.put(nombre, textura);

                logger.trace("TexturaAccion: {}", nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTexturas, e); }
    }

    public void cargarAccionRecursos()
    {
        logger.debug("[CARGANDO ACCION RECURSOS]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = AbrirFichero.abrirFichero(Settings.RECURSOS_XML+ Settings.XML_DataAcciones);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();
            List listaNodos = rootNode.getChildren("Accion");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String iD           = nodo.getChildText("iD");
                String nombreIcono  = nodo.getChildText("icono");

                AccionRecursos spellRecursos =  new AccionRecursos(iD, listaDeTexturasAcciones.get(nombreIcono));
                listaDeAccionRecursos.put(iD, spellRecursos);

                System.out.println(" iD :          " + iD);
                System.out.println(" Icono       : " + nombreIcono);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println("ERROR: con el fichero XML de datos de "+ Settings.XML_DataAcciones+": "+e); }
    }

    public void salvarTexturasIconos()
    {
        logger.debug("Salvando datos en {}", ficheroTexturas);

        Element accionRoot;
        Element accion;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren("TexturasAcciones");
            accionRoot = new Element("TexturasAcciones");

            for (Map.Entry<String, TextureRegion> entry: listaDeTexturasAcciones.entrySet())
            {
                accion = new Element("Textura");
                accion.setText(entry.getKey());
                accionRoot.addContent(accion);
            }
            doc.getRootElement().addContent(accionRoot);

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
