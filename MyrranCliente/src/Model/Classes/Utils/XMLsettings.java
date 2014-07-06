package Model.Classes.Utils;// Created by Ladrim on 05/07/2014.

import com.badlogic.gdx.math.Vector3;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class XMLsettings
{

    private Logger logger = LoggerFactory.getLogger(XMLsettings.class);
    private String settingsFile;

    private HashMap<String, String> strings = new HashMap<>();
    private HashMap<String, Float> floats = new HashMap<>();
    private HashMap<String, Integer> ints = new HashMap<>();
    private HashMap<String, Boolean> booleans = new HashMap<>();
    private HashMap<String, Vector3> vectors = new HashMap<>();


    public XMLsettings(String settings)
    {
        this.settingsFile = settings;
        cargarDatos();
    }

    //SET:
    public void setFile(String settingsFile)            { this.settingsFile = settingsFile; }
    public void setString(String key, String value)     { strings.put(key, value); }
    public void setFloat(String key, float value)       { floats.put(key, value); }
    public void setInt(String key, int value)           { ints.put(key, value); }
    public void setBoolean(String key, boolean value)   { booleans.put(key, value); }
    public void setVector(String key, Vector3 value)    { vectors.put(key, new Vector3(value)); }

    //GET::
    public String getFile()                             { return settingsFile; }
    public String getString(String key)                 { return getString(key, ""); }
    public float getFloat(String key)                   { return getFloat(key, 0.0f); }
    public int getInt(String key)                       { return getInt(key, 0); }
    public boolean getBoolean(String key)               { return getBoolean(key, false); }
    public Vector3 getVector(String key)                { return getVector(key, Vector3.Zero.cpy()); }

    public String getString(String key, String defaultValue)
    {
        String string = strings.get(key);

        if (string != null) { return string; }
        else { logger.error("{} not found, returning default {}", key, defaultValue); return defaultValue; }
    }

    public float getFloat(String key, float defaultValue)
    {
        Float f = floats.get(key);

        if (f != null) { return f; }
        else { logger.error("{} not found, returning default {}", key, defaultValue); return defaultValue; }
    }
    public int getInt(String key, int defaultValue)
    {
        Integer i = ints.get(key);

        if (i != null) { return i; }
        else { logger.error("{} not found, returning default {}", key, defaultValue); return defaultValue; }
    }

    public boolean getBoolean(String key, boolean defaultValue)
    {
        Boolean b = booleans.get(key);

        if (b != null) { return b; }
        else { logger.error("{} not found, returning default {}", key, defaultValue); return defaultValue; }
    }
    public Vector3 getVector(String key, Vector3 defaultValue)
    {
        Vector3 v = vectors.get(key);

        if (v != null) { return new Vector3(v); }
        else { logger.error("{} not found, returning default {}", key, defaultValue); return defaultValue; }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return XMLsettings.class.getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }

    //Metodos:
    private void cargarDatos()
    {
        logger.info("Settings: loading file " + settingsFile);

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
                logger.info("Settings: loaded string {} = {}", key, value);
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
                logger.info("Settings: loaded float {} = {}", key, value);
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
                logger.info("Settings: loaded int {} = {}", key, value);
            }

            //Load booleans
            booleans.clear();
            List<Element> boolNodes = root.getChildren("bool");

            for (int i = 0; i < boolNodes.size(); ++i)
            {
                Element boolNode = boolNodes.get(i);
                String key = boolNode.getAttributeValue("key");
                Boolean value = Boolean.parseBoolean(boolNode.getAttributeValue("value"));
                booleans.put(key, value);
                logger.info("Settings: loaded boolean {} = {}", key, value);
            }

            //Load vectors
            vectors.clear();
            List<Element> vectorNodes = root.getChildren("vector");

            for (int i = 0; i < vectorNodes.size(); ++i)
            {
                Element vectorNode = vectorNodes.get(i);
                String key = vectorNode.getAttributeValue("key");
                Float x = Float.parseFloat(vectorNode.getAttributeValue("x"));
                Float y = Float.parseFloat(vectorNode.getAttributeValue("y"));
                Float z = Float.parseFloat(vectorNode.getAttributeValue("z"));
                vectors.put(key, new Vector3(x, y, z));
                logger.info("Settings: loaded vector " + key + " = (" + x + ", " + y + ", " + z + ")");
            }

            logger.info("Settings: successfully finished loading settings");
        }
        catch (Exception e)
        {   logger.error("Settings: error loading file: {} {}", settingsFile, e.getMessage()); }
    }

    public void salvarDatos()
    {
        logger.info("Settings: saving file " + settingsFile);
        Document doc = new Document();
        Element element;


            //Create root
            doc.setRootElement(new Element("Settings"));

            //Create string nodes
            for (Entry<String, String> entry : strings.entrySet())
            {
                element = new Element("string");
                element.setAttribute("key", entry.getKey());
                element.setAttribute("value", entry.getValue());
                doc.getRootElement().addContent(element);
            }

            //Create float nodes
            for (Entry<String, Float> entry : floats.entrySet())
            {
                element = new Element("float");
                element.setAttribute("key", entry.getKey());
                element.setAttribute("value", Float.toString(entry.getValue()));
                doc.getRootElement().addContent(element);
            }

            //Create int nodes
            for (Entry<String, Integer> entry : ints.entrySet())
            {
                element = new Element("int");
                element.setAttribute("key", entry.getKey());
                element.setAttribute("value", Integer.toString(entry.getValue()));
                doc.getRootElement().addContent(element);
            }

            //Create boolean nodes
            for (Entry<String, Boolean> entry : booleans.entrySet())
            {
                element = new Element("bool");
                element.setAttribute("key", entry.getKey());
                element.setAttribute("value", Boolean.toString(entry.getValue()));
                doc.getRootElement().addContent(element);
            }

            //Create vector nodes
            for (Entry<String, Vector3> entry : vectors.entrySet())
            {
                element = new Element("vector");
                Vector3 vector = entry.getValue();
                element.setAttribute("key", entry.getKey());
                element.setAttribute("x", Float.toString(vector.x));
                element.setAttribute("y", Float.toString(vector.y));
                element.setAttribute("x", Float.toString(vector.z));
                doc.getRootElement().addContent(element);
            }

        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(settingsFile));
            logger.info("Settings: successfully saved");
        }
        catch (Exception e) { logger.error("Settings: error saving file {}", settingsFile); }
    }
}
