package DAO.Settings;// Created by Hanto on 06/07/2014.

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

public class SettingsXMLDB
{
    public HashMap<String, String> strings = new HashMap<>();
    public HashMap<String, Float> floats = new HashMap<>();
    public HashMap<String, Integer> ints = new HashMap<>();
    public HashMap<String, Boolean> booleans = new HashMap<>();

    private String ficheroSettings = "Data/Settings.xml";
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    public SettingsXMLDB()
    {   cargarDatos(); }

    //Metodos:
    private void cargarDatos()
    {
        logger.info("Cargando [SETTINGS] de: {}", ficheroSettings);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSettings);

        try
        {
            Document documento = builder.build(input);
            Element root = documento.getRootElement();

            //Load strings
            strings.clear();
            List<Element> stringNodes = root.getChildren("string");

            for (int i = 0; i < stringNodes.size(); ++i)
            {
                Element stringNode = stringNodes.get(i);
                String key = stringNode.getAttributeValue("key");
                String value = stringNode.getAttributeValue("value");
                strings.put(key, value);
                logger.trace("s {} = {}", key, value);
            }

            //Load floats
            floats.clear();
            List<Element> floatNodes = root.getChildren("float");

            for (int i = 0; i < floatNodes.size(); ++i)
            {
                Element floatNode = floatNodes.get(i);
                String key = floatNode.getAttributeValue("key");
                Float value = Float.parseFloat(floatNode.getAttributeValue("value"));
                floats.put(key, value);
                logger.trace("f {} = {}", key, value);
            }

            //Load ints
            ints.clear();
            List<Element> intNodes = root.getChildren("int");

            for (int i = 0; i < intNodes.size(); ++i)
            {
                Element intNode = intNodes.get(i);
                String key = intNode.getAttributeValue("key");
                Integer value = Integer.parseInt(intNode.getAttributeValue("value"));
                ints.put(key, value);
                logger.trace("i {} = {}", key, value);
            }

            //Load booleans
            booleans.clear();
            List<Element> boolNodes = root.getChildren("boolean");

            for (int i = 0; i < boolNodes.size(); ++i)
            {
                Element boolNode = boolNodes.get(i);
                String key = boolNode.getAttributeValue("key");
                Boolean value = Boolean.parseBoolean(boolNode.getAttributeValue("value"));
                booleans.put(key, value);
                logger.trace("b {} = {}", key, value);
            }

            if (strings.size() == 0 || floats.size() == 0 || ints.size() == 0 || booleans.size() == 0)
                logger.warn("Faltan datos validos en el fichero: {}", ficheroSettings);
        }
        catch (Exception e)
        {   logger.error("ERROR cargando Settings desde fichero: {}. {}", ficheroSettings, e.getMessage()); }
    }

    public void salvarDatos()
    {
        logger.info("Salvando [SETTINGS] en: " + ficheroSettings);
        Document doc = new Document();
        Element element;

        //Create root
        doc.setRootElement(new Element("Settings"));

        //Create string nodes
        for (Map.Entry<String, String> entry : strings.entrySet())
        {
            element = new Element("string");
            element.setAttribute("key", entry.getKey());
            element.setAttribute("value", entry.getValue());
            doc.getRootElement().addContent(element);
        }

        //Create float nodes
        for (Map.Entry<String, Float> entry : floats.entrySet())
        {
            element = new Element("float");
            element.setAttribute("key", entry.getKey());
            element.setAttribute("value", Float.toString(entry.getValue()));
            doc.getRootElement().addContent(element);
        }

        //Create int nodes
        for (Map.Entry<String, Integer> entry : ints.entrySet())
        {
            element = new Element("int");
            element.setAttribute("key", entry.getKey());
            element.setAttribute("value", Integer.toString(entry.getValue()));
            doc.getRootElement().addContent(element);
        }

        //Create boolean nodes
        for (Map.Entry<String, Boolean> entry : booleans.entrySet())
        {
            element = new Element("boolean");
            element.setAttribute("key", entry.getKey());
            element.setAttribute("value", Boolean.toString(entry.getValue()));
            doc.getRootElement().addContent(element);
        }

        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroSettings));
            logger.info("Datos salvados con exito");
        }
        catch (Exception e) { logger.error("Error salvando [SETTINGS] en {}", ficheroSettings, e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
