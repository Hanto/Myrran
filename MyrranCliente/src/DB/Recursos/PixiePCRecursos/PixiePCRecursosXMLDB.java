package DB.Recursos.PixiePCRecursos;// Created by Hanto on 01/05/2014.

import DB.Recursos.PixiePCRecursos.DTO.PixieRecursos;
import Model.Settings;
import View.Classes.Actores.Pixie;
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

public class PixiePCRecursosXMLDB
{
    private static class Singleton              { private static final PixiePCRecursosXMLDB get = new PixiePCRecursosXMLDB(); }
    public static PixiePCRecursosXMLDB get()    { return Singleton.get;}

    private String ficheroPixies = Settings.RECURSOS_XML+ Settings.XML_PixieSlot;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    private Map<String, EquipoPC> listaDePCRazas = new HashMap<>();
    public static class EquipoPC
    {
        public Map<String,Pixie>listaDeCuerpos = new HashMap<>();
        public Map<String,Pixie>listaDeCabezas = new HashMap<>();
        public Map<String,Pixie>listaDeYelmos = new HashMap<>();
        public Map<String,Pixie>listaDePetos = new HashMap<>();
        public Map<String,Pixie>listaDePantalones = new HashMap<>();
        public Map<String,Pixie>listaDeGuantes = new HashMap<>();
        public Map<String,Pixie>listaDeBotas = new HashMap<>();
        public Map<String,Pixie>listaDeHombreras = new HashMap<>();
        public Map<String,Pixie>listaDeCapasTraseras = new HashMap<>();
        public Map<String,Pixie>listaDeCapasFrontales = new HashMap<>();

        public Map<String,Pixie>getSlot(String slot)
        {
            switch (slot)
            {
                case "Cuerpo":      return listaDeCuerpos;
                case "Cabeza":      return listaDeCabezas;
                case "Yelmo":       return listaDeYelmos;
                case "Peto":        return listaDePetos;
                case "Pantalon":    return listaDePantalones;
                case "Guante":      return listaDeGuantes;
                case "Bota":        return listaDeBotas;
                case "Hombrera":    return listaDeHombreras;
                case "CapaTrasera": return listaDeCapasTraseras;
                case "CapaFrontal": return listaDeCapasFrontales;
                default:            return null;
            }
        }
    }
    public Map<String, EquipoPC>getListaDePCRazas()     { return listaDePCRazas; }


    private PixiePCRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarRazas();
        cargarSlot("Cuerpo");
        cargarSlot("Cabeza");
        cargarSlot("Yelmo");
        cargarSlot("Bota");
        cargarSlot("Guante");
        cargarSlot("Hombrera");
        cargarSlot("Pantalon");
        cargarSlot("Peto");
        cargarSlot("CapaFrontal");
        cargarSlot("CapaTrasera");
    }

    public void cargarRazas()
    {
        logger.info("Cargando [RAZAS] desde {}", ficheroPixies);
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroPixies);

        try
        {
            Document doc = builder.build(fichero);
            List listaNodos = doc.getRootElement().getChild("Razas").getChildren("Raza");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                EquipoPC equipoPC = new EquipoPC();
                listaDePCRazas.put(nombre, equipoPC);

                logger.trace("Raza : {}", nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo [RAZAS] en fichero {}: {}", ficheroPixies, e); }
    }

    public void cargarSlot(String slot)
    {
        logger.info("Cargando [{}] desde {}", slot.toUpperCase(), ficheroPixies);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroPixies);

        try
        {
            Document doc = builder.build(input);
            List listaNodos = doc.getRootElement().getChild(slot).getChildren("Pixie");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String raza     = nodo.getAttributeValue("raza");
                String nombre   = nodo.getAttributeValue("textura");

                if (slot.equals("Cuerpo")) listaDePCRazas.get(raza).getSlot(slot).put(nombre, new PixieRecursos.PixieCuerpo(nombre));
                else listaDePCRazas.get(raza).getSlot(slot).put(nombre, new PixieRecursos.PixieSlot(nombre));

                logger.trace("Raza :           {}", raza);
                logger.trace("Textura Cuerpo:  {}", nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo Slot [{}] en fichero {}: " +e, slot.toUpperCase(), ficheroPixies); }
    }

    public void salvarSlot(String slotor)
    {
        logger.info("Salvando [{}] en {}", slotor.toUpperCase(), ficheroPixies);
        Element slotRoot;
        Element slot;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroPixies);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren(slotor);
            slotRoot = new Element(slotor);

            for (Map.Entry<String, EquipoPC> entry: listaDePCRazas.entrySet())
            {
                for (Map.Entry<String,Pixie> cabeza: entry.getValue().getSlot(slotor).entrySet())
                {
                    slot = new Element("Pixie");
                    slot.setAttribute("textura", cabeza.getKey());
                    slot.setAttribute("raza", entry.getKey());
                    slotRoot.addContent(slot);
                }
            }
            doc.getRootElement().addContent(slotRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroPixies));
            logger.info("Datos de [{}] salvados en: {}", slotor.toUpperCase(), ficheroPixies);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}", ficheroPixies);}
    }

    public void salvarRazas()
    {
        logger.info("Salvando [RAZAS] en {}", ficheroPixies);
        Element razaRoot;
        Element raza;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroPixies);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren("Razas");
            razaRoot = new Element("Razas");

            for (Map.Entry<String, EquipoPC> entry: listaDePCRazas.entrySet())
            {
                raza = new Element("Raza");
                raza.setText(entry.getKey());
                razaRoot.addContent(raza);
            }
            doc.getRootElement().addContent(razaRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroPixies));
            logger.info("Razas salvadas en: {}", ficheroPixies);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando razas en el fichero {}", ficheroPixies);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
