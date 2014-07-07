package DB.Recursos.SkillRecursos;// Created by Hanto on 30/04/2014.

import Core.AbrirFichero;
import DB.RSC;
import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import DB.Recursos.SkillRecursos.DTO.TipoSpellRecursos;
import Data.Settings;
import View.Classes.Graficos.Pixie;
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

public class SkillRecursosLocalDB
{
    private static class Singleton              { private static final SkillRecursosLocalDB get = new SkillRecursosLocalDB(); }
    public static SkillRecursosLocalDB get()    { return Singleton.get; }

    public Map<String, TextureRegion> listaDeTexturasIconosSpells = new HashMap<>();
    public Map<String, Pixie> listaDeAnimaciones = new HashMap<>();
    public Map<String, SpellRecursos> listaSpell = new HashMap<>();
    public Map<String, TipoSpellRecursos> listaTipoSpell = new HashMap<>();

    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasIconoSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    private SkillRecursosLocalDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasIconos();
        cargarAnimacionesCasteo();
        cargarAnimacionesProyectil();

        cargarSpellRecursos();
    }


    public void cargarTexturasIconos()
    {
        logger.debug("Cargando datos desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document documento = builder.build(input);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChild("TexturasSpells").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
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
        System.out.println("[CARGANDO ANIMACIONES CASTEO]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = AbrirFichero.abrirFichero(Settings.RECURSOS_XML+ Settings.XML_AnimacionesCasteo);

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

                System.out.println(" AnimacionCasteo : " + nombre);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println("ERROR: con el fichero XML de datos de "+ Settings.XML_AnimacionesCasteo+": "+e); }
    }

    public void cargarAnimacionesProyectil()
    {
        System.out.println("[CARGANDO ANIMACIONES PROYECTIL]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = AbrirFichero.abrirFichero(Settings.RECURSOS_XML+ Settings.XML_AnimacionesProyectil);

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

                System.out.println(" AnimacionProyectil : " + nombre);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println("ERROR: con el fichero XML de datos de "+ Settings.XML_AnimacionesProyectil+": "+e); }
    }

    public void cargarSpellRecursos()
    {
        System.out.println("[CARGANDO SPELL RECURSOS]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = AbrirFichero.abrirFichero(Settings.RECURSOS_XML+ Settings.XML_DataSpells);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();
            List listaNodos = rootNode.getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombreIcono  = nodo.getAttributeValue("icono");

                SpellRecursos spellRecursos =  new SpellRecursos(iD, listaDeTexturasIconosSpells.get(nombreIcono));
                listaSpell.put(iD, spellRecursos);

                System.out.println(" iD :          " + iD);
                System.out.println(" Icono       : " + nombreIcono);
            }
            System.out.println();
        }
        catch (Exception e) { System.out.println("ERROR: con el fichero XML de datos de "+ Settings.XML_DataSpells+": "+e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }

    public void salvarTexturasIconos()
    {
        logger.debug("Salvando datos en {}", ficheroTexturas);

        Element iconosRoot;
        Element icono;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren("TexturasSpells");
            iconosRoot = new Element("TexturasSpells");

            for (Map.Entry<String, TextureRegion> entry: listaDeTexturasIconosSpells.entrySet())
            {
                icono = new Element("Textura");
                icono.setText(entry.getKey());
                iconosRoot.addContent(icono);
            }
            doc.getRootElement().addContent(iconosRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}", ficheroTexturas);}
    }
}
