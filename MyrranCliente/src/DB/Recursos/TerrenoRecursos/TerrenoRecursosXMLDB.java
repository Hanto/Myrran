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

public class TerrenoRecursosXMLDB
{
    private static class Singleton              { private static final TerrenoRecursosXMLDB get = new TerrenoRecursosXMLDB(); }
    public static TerrenoRecursosXMLDB get()    { return Singleton.get; }

    private Map<String, TextureRegion> listaDeTexturasTerreno = new HashMap<>();
    private Map<Integer, TerrenoRecursos> listaDeTerrenosRecursos = new HashMap<>();
    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasTerrenos;
    private String ficheroTRecursos = Settings.RECURSOS_XML + Settings.XML_DataTerrenos;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TextureRegion> getListaDeTexturasTerreno()       { return listaDeTexturasTerreno; }
    public Map<Integer, TerrenoRecursos> getListaDeTerrenosRecursos()   { return listaDeTerrenosRecursos; }

    private TerrenoRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasTerrenos();
        cargarTerrenoRecursos();
    }


    public void cargarTexturasTerrenos()
    {
        logger.debug("Cargando [TEXTURAS TERRENOS] desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroTexturas);

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
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:"+e, ficheroTexturas); }
    }

    public void cargarTerrenoRecursos()
    {
        logger.debug("Cargando [TERRENOS RECURSOS] desde {}", ficheroTRecursos);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroTRecursos);

        try
        {
            Document doc = builder.build(fichero);
            List<Element> listaNodos = doc.getRootElement().getChildren("Terreno");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i).getChild("Recursos");

                short iD        = Short.parseShort(listaNodos.get(i).getAttributeValue("ID"));
                String nombreT  = nodo.getAttributeValue("textura");

                if (listaDeTexturasTerreno.get(nombreT) == null) logger.error("Nombre de textura {} no existe para el terreno {}", nombreT, iD);
                TerrenoRecursos terrenoRSC = new TerrenoRecursos(iD, nombreT, listaDeTexturasTerreno.get(nombreT));
                listaDeTerrenosRecursos.put(terrenoRSC.getID(), terrenoRSC);

                logger.trace("ID:      {}", iD);
                logger.trace("Textura: {}", nombreT);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:"+e, ficheroTRecursos); }
    }

    public void salvarTexturasTerrenos()
    {
        logger.debug("Salvando [TEXTURAS TERRENOS] en {}", ficheroTexturas);

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
                logger.trace("Textura {} salvada", terreno.getText());
            }
            doc.getRootElement().addContent(terrenoRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}"+e, ficheroTexturas);}
    }

    public void salvarTerrenoRecursos()
    {
        logger.debug("Salvando [TERRENO RECURSOS] en {}", ficheroTRecursos);
        Document doc;
        Element recurso;
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTRecursos);

        try
        {
            doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Terreno");

            for (int i = 0; i< listaNodos.size() ; i++)
            {
                int iD = Integer.parseInt(listaNodos.get(i).getAttributeValue("ID"));
                if (listaDeTerrenosRecursos.get(iD) != null)
                {
                    recurso = listaNodos.get(i).getChild("Recursos");
                    String nombreTextura = listaDeTerrenosRecursos.get(iD).getNombreTextura();
                    recurso.setAttribute("textura", nombreTextura);
                    logger.trace("Nombre Textura terreno {} actualizado a {}", iD, nombreTextura);
                }
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTRecursos));
            logger.info("Terreno Recursos actualizados en fichero XML: {}", ficheroTRecursos);
        }
        catch (Exception e) { logger.error("ERROR: leyendo campo Iconos de fichero: {}: "+e, ficheroTRecursos);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}

