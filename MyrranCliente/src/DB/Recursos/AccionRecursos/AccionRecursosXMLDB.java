package DB.Recursos.AccionRecursos;// Created by Hanto on 07/05/2014.

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

public class AccionRecursosXMLDB
{
    private static class Singleton              { private static final AccionRecursosXMLDB get = new AccionRecursosXMLDB(); }
    public static AccionRecursosXMLDB get()     { return Singleton.get; }

    private Map<String, TextureRegion> listaDeTexturasAcciones = new HashMap<>();
    private Map<String, AccionRecursos> listaDeAccionRecursos = new HashMap<>();
    private String ficheroTexturas = Settings.RECURSOS_XML + Settings.XML_TexturasIconosAcciones;
    private String ficheroRecursos = Settings.RECURSOS_XML+ Settings.XML_DataAcciones;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TextureRegion> getListaDeTexturasAcciones()  { return listaDeTexturasAcciones; }
    public Map<String, AccionRecursos> getListaDeAccionRecursos()   { return listaDeAccionRecursos; }

    private AccionRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasIconos();
        cargarAccionRecursos();
    }


    public void cargarTexturasIconos()
    {
        logger.debug("Cargando [TEXTURAS ACCIONES] desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChild("TexturasAcciones").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);
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
        logger.debug("Cargando [ACCIONES RECURSOS] desde {}", ficheroRecursos);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroRecursos);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Accion");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombreIcono  = nodo.getAttributeValue("icono");

                if (listaDeTexturasAcciones.get(nombreIcono) == null)
                    logger.error("Nombre de textura {} no existe para accion {}", nombreIcono, iD);
                AccionRecursos accionRecursos = new AccionRecursos(iD, nombreIcono, listaDeTexturasAcciones.get(nombreIcono));
                listaDeAccionRecursos.put(iD, accionRecursos);

                logger.trace("iD   : {}", iD);
                logger.trace("Icono: {}", nombreIcono);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}"+e, ficheroRecursos);}
    }

    public void salvarTexturasIconos()
    {
        logger.debug("Salvando [TEXTURAS ACCIONES] en {}", ficheroTexturas);

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
                logger.trace("Textura {} salvada", accion.getText());
            }
            doc.getRootElement().addContent(accionRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}", ficheroTexturas);}
    }

    public void salvarAccionRecursos()
    {
        logger.debug("Salvando [ACCION RECURSOS] en {}", ficheroRecursos);

        Document doc;
        Element nodo;
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroRecursos);

        try
        {
            doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Accion");

            for (int i = 0; i< listaNodos.size() ; i++)
            {
                nodo = listaNodos.get(i);
                String iD = nodo.getAttributeValue("ID");
                if (listaDeAccionRecursos.get(iD) != null)
                {
                    String nombreTextura = listaDeAccionRecursos.get(iD).getNombreTextura();
                    nodo.setAttribute("icono", nombreTextura);
                    logger.trace("Nombre Icono accion {} actualizado a {}", iD, nombreTextura);
                }
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroRecursos));
            logger.info("Accion Recursos actualizados en fichero XML: {}", ficheroRecursos);
        }
        catch (Exception e) { logger.error("ERROR: leyendo campo Iconos de fichero: {}: "+e, ficheroRecursos);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
