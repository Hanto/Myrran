package DB.Datos.Terreno;// Created by Hanto on 15/04/2014.

import DAO.Terreno.TerrenoXMLDBI;
import Data.Settings;
import Interfaces.Geo.TerrenoI;
import Model.Classes.Geo.Terreno;
import ch.qos.logback.classic.Logger;
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

public class TerrenoXMLDB implements TerrenoXMLDBI
{
    private static class Singleton      { private static final TerrenoXMLDB get = new TerrenoXMLDB(); }
    public static TerrenoXMLDB get()  { return Singleton.get; }

    private Map<Short, TerrenoI> listaDeTerrenos = new HashMap<>();
    private Map<Short, String> listaDeTexturas = new HashMap<>();
    private String ficheroTerrenos = Settings.RECURSOS_XML+ Settings.XML_DataTerrenos;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<Short, TerrenoI> getListaTerrenos() { return listaDeTerrenos; }

    private TerrenoXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        logger.debug("Cargando datos desde {}", ficheroTerrenos);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTerrenos);

        try
        {
            Document documento = builder.build(input);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChildren("Terreno");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                short iD        = Short.parseShort(nodo.getAttributeValue("ID"));
                String nombre   = nodo.getAttributeValue("nombre");
                String nombreT  = nodo.getAttributeValue("nombreTextura");
                boolean isSolido= Boolean.parseBoolean(nodo.getAttributeValue("isSolido"));

                Terreno terreno = new Terreno(iD, nombre, isSolido);
                listaDeTerrenos.put(iD, terreno);
                listaDeTexturas.put(iD, nombreT);

                logger.info ("TERRENO:         {}", iD);
                logger.debug("nombre:          {}", nombre);
                logger.debug("nombreTextura:   {}", nombreT);
                logger.debug("isSolido:        {}", isSolido);
            }
            logger.debug("");

            if (listaDeTerrenos.size() == 0)
                logger.error("No se han encontrado datos validos en el fichero {}", ficheroTerrenos);
        }
        catch (Exception e) {logger.error("ERROR: con el fichero XML de datos de {}", ficheroTerrenos, e); }
    }

    @Override public void salvarDatos()
    {
        logger.debug("Salvando datos en {}", ficheroTerrenos);
        Document doc = new Document();
        Element terreno;

        //Crear root:
        doc.setRootElement(new Element("Terrenos"));

        for (Map.Entry<Short, TerrenoI> entry: listaDeTerrenos.entrySet())
        {
            terreno = new Element("Terreno");
            terreno.setAttribute("ID", Short.toString(entry.getValue().getID()));
            terreno.setAttribute("nombre", entry.getValue().getNombre());
            terreno.setAttribute("nombreTextura", listaDeTexturas.get(entry.getValue().getID()));
            terreno.setAttribute("isSolido", Boolean.toString(entry.getValue().getIsSolido()));

            doc.getRootElement().addContent(terreno);
            logger.debug("TERRENO: {} salvado", terreno.getAttributeValue("ID"));
        }
        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream("Prueba.xml"));
            logger.info("Datos salvados en fichero XML: {}", ficheroTerrenos);
        }
        catch (Exception e) { logger.error("ERROR: salvando datos Terrenos en fichero: {}", ficheroTerrenos, e);}

    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
