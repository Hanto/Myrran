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

    private String settingsFile = "Settings.xml";
    private Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

    public SettingsXMLDB()
    {   cargarDatos(); }

    //Metodos:
    private void cargarDatos()
    {
        logger.debug("Cargando Settings de: {}", settingsFile);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(settingsFile);

        try
        {
            Document documento = builder.build(fichero);
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
                logger.debug("s {} = {}", key, value);
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
                logger.debug("f {} = {}", key, value);
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
                logger.debug("i {} = {}", key, value);
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
                logger.debug("b {} = {}", key, value);
            }

            logger.info("Settings cargados con exito desde: {}", settingsFile);
        }
        catch (Exception e)
        {   logger.error("ERROR cargando Settings desde fichero: {}", settingsFile, e.getMessage()); }
    }

    public void salvarDatos()
    {
        logger.debug("Salvando Datos en: " + settingsFile);
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
            xmlOutputter.output(doc, new FileOutputStream(settingsFile));
            logger.info("Datos salvados con exito");
        }
        catch (Exception e) { logger.error("Error salvando los datos en {}", settingsFile, e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
