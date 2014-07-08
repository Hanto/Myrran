package DB.Datos.Spell;// Created by Hanto on 17/04/2014.

import Core.Skills.SkillStat;
import DAO.Spell.SpellXMLDBI;
import DB.DAO;
import Data.Settings;
import Interfaces.BDebuff.BDebuffI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.TipoSpellI;
import Model.Classes.Skill.Spell.Spell;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpellXMLDB implements SpellXMLDBI
{
    private static class Singleton      { private static final SpellXMLDB get = new SpellXMLDB(); }
    public static SpellXMLDB get()      { return Singleton.get; }

    private Map<String, SpellI> listaDeSpells = new HashMap<>();
    private Map<String, String> listaIconos = new HashMap<>();
    private String ficheroSpells = Settings.RECURSOS_XML+ Settings.XML_DataSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<String, SpellI> getListaDeSpells() { return listaDeSpells; }

    public SpellXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        logger.debug("Cargando datos desde {}", ficheroSpells);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSpells);

        try
        {
            Document documento = builder.build(input);
            Element rootNode = documento.getRootElement();

            List<Element> listaNodos = rootNode.getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                String tipoSpell    = nodo.getAttributeValue("tipoSpell");
                String descripcion  = nodo.getChildText("Descripcion");
                String icono        = nodo.getAttributeValue("icono");

                TipoSpellI tipoSpellI =  DAO.tipoSpellDAOFactory.getTipoSpellDAO().getTipoSpell(tipoSpell);
                SpellI spell = new Spell(tipoSpellI);
                spell.setID(iD);
                spell.setNombre(nombre);
                spell.setDescripcion(descripcion);

                logger.info ("SPELL:          {}", iD);
                logger.debug("nombre:         {}", nombre);
                logger.debug("TipoSpell:      {}", tipoSpell);
                logger.debug("Descripcion:    {}", descripcion);

                if (iD == null || nombre == null || tipoSpell == null || icono == null || descripcion == null )
                {   logger.error("Error parseando los datos del SPELL, campos erroneos", iD);}

                List listaDebuffs = nodo.getChildren("Debuff");

                for (int j = 0; j < listaDebuffs.size(); j++)
                {
                    String debuffID = ((Element)listaDebuffs.get(j)).getText();
                    spell.añadirDebuff(debuffID);

                    logger.debug("Aplica debuff:  {}", debuffID);
                }

                List<Element> listaStats= nodo.getChildren("SkillStat");

                for (int j = 0; j < listaStats.size(); j++)
                {
                    if (listaStats.size() < spell.getTipoSpell().getNumSkillStats()) logger.error("Faltan SkillStats por definir");

                    Element stat = listaStats.get(j);

                    byte id             = Byte.parseByte(stat.getAttributeValue("ID"));
                    String nombreStat   = stat.getAttributeValue("nombre");
                    float valorBase     = Float.parseFloat(stat.getAttributeValue("valorBase"));
                    boolean isMejorable = Boolean.parseBoolean(stat.getAttributeValue("isMejorable"));
                    int talentoMaximo   = Integer.parseInt(stat.getAttributeValue("talentoMaximo"));
                    int costeTalento    = Integer.parseInt(stat.getAttributeValue("costeTalento"));
                    float bonoTalento   = Float.parseFloat(stat.getAttributeValue("bonoTalento"));

                    spell.getSkillStat(id).setStat(id, nombreStat, valorBase);
                    if (isMejorable) spell.getSkillStat(id).setTalentos(talentoMaximo, costeTalento, bonoTalento);
                    else spell.getSkillStat(id).setIsMejorable(isMejorable);

                    logger.debug("  id:           {}", id);
                    logger.debug("  nombreStat:   {}", nombreStat);
                    logger.debug("  valorBase:    {}", valorBase);
                    logger.debug("  isMejorable:  {}", isMejorable);

                    if (isMejorable)
                    {
                        logger.debug("  talentoMaximo:{}", talentoMaximo);
                        logger.debug("  costeTalento: {}", costeTalento);
                        logger.debug("  bonoTalento:  {}", bonoTalento);
                    }
                }
                logger.debug("");

                listaDeSpells.put(spell.getID(), spell);
            }
            if (listaDeSpells.size()==0)
                logger.error("No se ha encontrado ningun Dato valido en el fichero {}", ficheroSpells);
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}: "+e, ficheroSpells); }
    }

    @Override public void salvarDatos()
    {
        logger.debug("Salvando datos en {}", ficheroSpells);
        Document doc = new Document();
        Element spell;
        Element element;
        SkillStat skillStat;

        actualizarListaIconos();

        //Crear root
        doc.setRootElement(new Element("Spells"));

        //Creamos nodos:
        for (Map.Entry<String, SpellI> entry: listaDeSpells.entrySet())
        {
            spell = new Element("Spell");
            spell.setAttribute("ID", entry.getValue().getID());
            spell.setAttribute("nombre", entry.getValue().getNombre());
            spell.setAttribute("tipoSpell", entry.getValue().getTipoSpell().getID());
            spell.setAttribute("icono", listaIconos.get(entry.getValue().getID()));

            element = new Element("Descripcion");
            element.setText(entry.getValue().getDescripcion());
            spell.addContent(element);

            Iterator<BDebuffI> debuff = entry.getValue().getDebuffsQueAplica();
            while (debuff.hasNext())
            {
                element = new Element("Debuff");
                element.setText(debuff.next().getID());
                spell.addContent(element);
            }

            Iterator<SkillStat> stat = entry.getValue().getSkillStats();
            while (stat.hasNext())
            {
                skillStat = stat.next();
                element = new Element("SkillStat");
                element.setAttribute("ID", Integer.toString(skillStat.getID()));
                element.setAttribute("nombre", skillStat.getNombre());
                element.setAttribute("valorBase", Float.toString(skillStat.getValorBase()));
                element.setAttribute("isMejorable", Boolean.toString(skillStat.getisMejorable()));
                element.setAttribute("costeTalento", Integer.toString(skillStat.getCosteTalento()));
                element.setAttribute("bonoTalento", Float.toString(skillStat.getBonoTalento()));
                element.setAttribute("talentoMaximo", Integer.toString(skillStat.getTalentoMaximo()));
                spell.addContent(element);
            }
            doc.getRootElement().addContent(spell);
            logger.debug("SPELLS: {} salvado", spell.getAttributeValue("ID"));
        }

        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroSpells));
            logger.info("Datos salvados en fichero XML: {}", ficheroSpells);
        }
        catch (Exception e) { logger.error("ERROR: salvando datos Spells en fichero: {}: "+e, ficheroSpells);}
    }

    private void actualizarListaIconos()
    {
        Document doc;
        Element nodo;
        SAXBuilder builder = new SAXBuilder();

        InputStream input = abrirFichero(ficheroSpells);
        listaIconos.clear();

        try
        {
            doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i< listaNodos.size() ; i++)
            {
                nodo = listaNodos.get(i);
                listaIconos.put(nodo.getAttributeValue("ID"), nodo.getAttributeValue("icono"));
            }
        }
        catch (Exception e) { logger.error("ERROR: leyendo campo Iconos de fichero: {}: "+e, ficheroSpells);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
