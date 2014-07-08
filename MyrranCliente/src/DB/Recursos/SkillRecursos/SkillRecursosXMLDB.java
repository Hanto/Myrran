package DB.Recursos.SkillRecursos;// Created by Hanto on 30/04/2014.

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

public class SkillRecursosXMLDB
{
    private static class Singleton              { private static final SkillRecursosXMLDB get = new SkillRecursosXMLDB(); }
    public static SkillRecursosXMLDB get()    { return Singleton.get; }

    private Map<String, TextureRegion> listaDeTexturasIconosSpells = new HashMap<>();
    private Map<String, Pixie> listaDeAnimaciones = new HashMap<>();
    private Map<String, SpellRecursos> listaSpell = new HashMap<>();
    private Map<String, TipoSpellRecursos> listaTipoSpell = new HashMap<>();

    private String ficheroRecursos = Settings.RECURSOS_XML+ Settings.XML_DataSpells;
    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasIconoSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TextureRegion> getListaDeTexturasIconosSpells()  { return listaDeTexturasIconosSpells; }
    public Map<String, Pixie> getListaDeAnimaciones()                   { return listaDeAnimaciones; }
    public Map<String, SpellRecursos> getListaSpell()                   { return listaSpell; }
    public Map<String, TipoSpellRecursos> getListaTipoSpell()           { return listaTipoSpell; }

    private SkillRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        cargarTexturasIconos();
        //cargarAnimacionesCasteo();
        //cargarAnimacionesProyectil();

        cargarSpellRecursos();
    }


    public void cargarTexturasIconos()
    {
        logger.debug("Cargando [TEXTURAS ICONOS SPELL] desde {}", ficheroTexturas);
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
        System.out.println("[CARGANDO ANIMACIONES CASTEO]:");
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(Settings.RECURSOS_XML + Settings.XML_AnimacionesCasteo);

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
        InputStream fichero = abrirFichero(Settings.RECURSOS_XML + Settings.XML_AnimacionesProyectil);

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
        logger.debug("Cargando [SPELL RECURSOS] desde {}", ficheroRecursos);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroRecursos);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i).getChild("Recursos");

                String iD           = listaNodos.get(i).getAttributeValue("ID");
                String nombreIcono  = nodo.getAttributeValue("icono");

                SpellRecursos spellRecursos =  new SpellRecursos(iD, nombreIcono, listaDeTexturasIconosSpells.get(nombreIcono));
                listaSpell.put(iD, spellRecursos);

                logger.trace("Spell: {}", iD);
                logger.trace("Icono: {}", nombreIcono);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTexturas, e); }
    }

    public void salvarTexturasIconos()
    {
        logger.debug("Salvando [TEXTURAS ICONOS]s en {}", ficheroTexturas);

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

    public void salvarSpellRecursos()
    {
        logger.debug("Salvando [SPELL RECURSOS] en {}", ficheroRecursos);
        Document doc;
        Element recurso;
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroRecursos);

        try
        {
            doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i< listaNodos.size() ; i++)
            {
                String iD = listaNodos.get(i).getAttributeValue("ID");
                if (listaSpell.get(iD) != null)
                {
                    recurso = listaNodos.get(i).getChild("Recursos");
                    String nombreTextura = listaSpell.get(iD).getNombreTexturaIcono();
                    recurso.setAttribute("icono", nombreTextura);
                    logger.trace("Spell recurso {} actualizado a {}", iD, nombreTextura);
                }
            }

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroRecursos));
            logger.info("[SPELL RECURSOS] actualizados en fichero XML: {}", ficheroRecursos);
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
