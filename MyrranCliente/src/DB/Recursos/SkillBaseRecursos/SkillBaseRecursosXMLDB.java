package DB.Recursos.SkillBaseRecursos;// Created by Hanto on 05/08/2015.

import DB.RSC;
import Model.Settings;
import View.Classes.Actores.Pixie;
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

public class SkillBaseRecursosXMLDB
{
    private static class Singleton              { private static final SkillBaseRecursosXMLDB get = new SkillBaseRecursosXMLDB(); }
    public static SkillBaseRecursosXMLDB get()  { return Singleton.get; }

    private Map<String, TextureRegion> listaDeTexturasIconosSpells = new HashMap<>();
    private Map<String, Pixie> listaDeAnimaciones = new HashMap<>();

    private String ficheroAnimacionesCasteo = Settings.RECURSOS_XML + Settings.XML_AnimacionesCasteo;
    private String ficheroAnimacionesProyectil = Settings.RECURSOS_XML + Settings.XML_AnimacionesProyectil;
    private String ficheroTexturas = Settings.RECURSOS_XML + Settings.XML_TexturasIconoSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TextureRegion> getListaDeTexturasIconosSpells()  { return listaDeTexturasIconosSpells; }
    public Map<String, Pixie> getListaDeAnimaciones()                   { return listaDeAnimaciones; }

    private SkillBaseRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasIconos();
        cargarAnimacionesCasteo();
        cargarAnimacionesProyectil();
    }

    public void cargarTexturasIconos()
    {
        logger.info("Cargando [TEXTURAS ICONOS SPELL] desde {}", ficheroTexturas);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChild("TexturasSpells").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);
                String nombre = nodo.getText();

                TextureRegion textura = new TextureRegion(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_TexturasIconos_LOC + nombre));
                listaDeTexturasIconosSpells.put(nombre, textura);

                logger.trace("TexturaIconoSpell: " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTexturas, e); }
    }

    public void cargarAnimacionesCasteo()
    {
        logger.info("Cargando [ANIMACIONES CASTEO] desde {}", ficheroAnimacionesCasteo);
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroAnimacionesCasteo);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();
            List listaNodos = rootNode.getChildren("Animacion");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                Pixie pixie = new Pixie(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_AnimacionesSpells_LOC + nombre),1,3);
                pixie.añadirAnimacion("Casteo", new int[]{0, 1, 2}, 0.15f, false);
                pixie.animaciones().get(0).animarYEliminar = true;
                listaDeAnimaciones.put(nombre, pixie);

                logger.trace(" AnimacionCasteo : " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroAnimacionesCasteo, e); }
    }

    public void cargarAnimacionesProyectil()
    {
        logger.info("Cargando [ANIMACIONES PROYECTIL] desde {}", ficheroAnimacionesProyectil);
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroAnimacionesProyectil);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();
            List listaNodos = rootNode.getChildren("Animacion");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                Pixie pixie = new Pixie(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_AnimacionesSpells_LOC + nombre),1,3);
                pixie.añadirAnimacion("Proyectil", new int[]{0, 1, 2}, 0.15f, false);
                listaDeAnimaciones.put(nombre, pixie);

                logger.trace(" AnimacionProyectil : " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroAnimacionesProyectil, e); }
    }

    public void salvarTexturasIconos()
    {
        logger.info("Salvando [TEXTURAS ICONOS]s en {}", ficheroTexturas);
        Element iconoRoot;
        Element icono;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);
            doc.getRootElement().removeChildren("TexturasSpells");
            iconoRoot = new Element("TexturasSpells");

            for (Map.Entry<String, TextureRegion> entry: listaDeTexturasIconosSpells.entrySet())
            {
                icono = new Element("Textura");
                icono.setText(entry.getKey());
                iconoRoot.addContent(icono);
                logger.trace("Textura {} salvada", icono.getText());
            }
            doc.getRootElement().addContent(iconoRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}"+e, ficheroTexturas);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
