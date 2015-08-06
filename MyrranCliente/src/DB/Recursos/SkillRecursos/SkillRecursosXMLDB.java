package DB.Recursos.SkillRecursos;// Created by Hanto on 30/04/2014.

import DB.Recursos.SkillRecursos.DTO.SpellRecursos;
import Model.Settings;
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

public class SkillRecursosXMLDB
{
    private static class Singleton          { private static final SkillRecursosXMLDB get = new SkillRecursosXMLDB(); }
    public static SkillRecursosXMLDB get()  { return Singleton.get; }

    private Map<String, SpellRecursos> listaSpell = new HashMap<>();
    private String ficheroSpellRecursos = Settings.RECURSOS_XML+ Settings.XML_DataSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, SpellRecursos> getListaSpell()                   { return listaSpell; }

    private SkillRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {   cargarSpellRecursos(); }

    public void cargarSpellRecursos()
    {
        logger.info("Cargando [SPELL RECURSOS] desde {}", ficheroSpellRecursos);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSpellRecursos);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);

                String tipoSpell    = nodo.getAttributeValue("tipoSpell");
                String iDSpell      = nodo.getAttributeValue("ID");
                String nombreIcono  = nodo.getChild("Recursos").getAttributeValue("icono");

                SpellRecursos spellRecursos =  new SpellRecursos(iDSpell, tipoSpell, nombreIcono);

                logger.trace("Spell: {}", iDSpell);
                logger.trace("Icono: {}", nombreIcono);

                List<Element> listaNodosAnimacion = listaNodos.get(i).getChildren("Animacion");

                for (Element nodoAnimacion : listaNodosAnimacion)
                {
                    int tipoAnimacion = nodoAnimacion.getAttribute("TipoAnimacion").getIntValue();
                    int numAnimacion  = nodoAnimacion.getAttribute("numAnimacion").getIntValue();

                    logger.trace("tipoAnimacion: {}", tipoAnimacion);
                    logger.trace("numAnimacion:  {}", numAnimacion);

                    spellRecursos.setNumeroAnimacion(tipoAnimacion, numAnimacion);
                }
                listaSpell.put(iDSpell, spellRecursos);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroSpellRecursos, e); }
    }

    public void salvarSpellRecursos()
    {
        logger.info("Salvando [SPELL RECURSOS] en {}", ficheroSpellRecursos);
        Document doc;
        Element recurso;
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSpellRecursos);

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
            xmlOutputter.output(doc, new FileOutputStream(ficheroSpellRecursos));
            logger.info("[SPELL RECURSOS] actualizados en fichero XML: {}", ficheroSpellRecursos);
        }
        catch (Exception e) { logger.error("ERROR: leyendo campo Iconos de fichero: {}: "+e, ficheroSpellRecursos);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
