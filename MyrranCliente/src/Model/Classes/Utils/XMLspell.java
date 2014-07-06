package Model.Classes.Utils;// Created by Ladrim on 06/07/2014.

import Core.Skills.SkillStat;
import DB.DAO;
import Data.MiscData;
import Interfaces.BDebuff.BDebuffI;
import Interfaces.Spell.SpellI;
import Interfaces.Spell.TipoSpellI;
import Model.Classes.Skill.Spell.Spell;
import ch.qos.logback.classic.Level;
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
import java.util.Map.Entry;

public class XMLspell
{


    public Map<String, SpellI> listaDeSpells = new HashMap<>();

    private Logger logger = (Logger)LoggerFactory.getLogger("XMLspell");
    private String fichero;

    public XMLspell(String otroFichero)
    {
        logger.setLevel(Level.DEBUG);

        fichero = otroFichero;
        cargarDatos(fichero);
    }


    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return XMLsettings.class.getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }

    public void cargarDatos(String file)
    {
        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(file);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                String tipoSpell    = nodo.getAttributeValue("tipoSpell");
                String descripcion  = nodo.getChildText("Descripcion");

                TipoSpellI tipoSpellI =  DAO.tipoSpellDAOFactory.getTipoSpellDAO().getTipoSpell(tipoSpell);
                SpellI spell = new Spell(tipoSpellI);
                spell.setID(iD);
                spell.setNombre(nombre);
                spell.setDescripcion(descripcion);

                logger.info("[SPELL]:");
                logger.info(" iD :           {}", iD);
                logger.info(" nombre:        {}", nombre);
                logger.info(" Descripcion:   {}", descripcion);
                logger.info(" TipoSpell:     {}", tipoSpell);

                List listaDebuffs = nodo.getChildren("Debuff");

                for (int j = 0; j < listaDebuffs.size(); j++)
                {
                    String debuffID = ((Element)listaDebuffs.get(j)).getText();
                    spell.añadirDebuff(debuffID);

                    logger.info(" Aplica debuff: {}", debuffID);
                }

                List listaStats= nodo.getChildren("SkillStat");

                for (int j = 0; j < listaStats.size(); j++)
                {
                    Element stat = (Element) listaStats.get(j);

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

                    logger.info("  ID:           {}", id);
                    logger.info("  nombreStat:   {}", nombreStat);
                    logger.info("  valorBase:    {}", valorBase);
                    logger.info("  isMejorable:  {}", isMejorable);

                    if (isMejorable)
                    {
                        logger.info("  talentoMaximo:{}", talentoMaximo);
                        logger.info("  costeTalento: {}", costeTalento);
                        logger.info("  bonoTalento:  {}", bonoTalento);
                    }
                }
                logger.info("");

                listaDeSpells.put(spell.getID(), spell);
            }
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}: ", MiscData.XML_DataSpells, e); }
    }

    public void salvarDatos()
    {
        logger.info("SPELLS: Salvando datos de los spells en {}", fichero);
        Document doc = new Document();
        Element spell;
        Element element;
        SkillStat skillStat;

        //Crear root
        doc.setRootElement(new Element("Spells"));

        //Creamos nodos:
        for (Entry<String, SpellI> entry: listaDeSpells.entrySet())
        {
            spell = new Element("Spell");
            spell.setAttribute("ID", entry.getValue().getID());
            spell.setAttribute("nombre", entry.getValue().getNombre());
            spell.setAttribute("tipoSpell", entry.getValue().getTipoSpell().getID());

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
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat());
            xmlOutputter.output(doc, new FileOutputStream("prueba.xml"));
            logger.info("SPELLS: Spells salvados en fichero XML: {}", fichero);
        }
        catch (Exception e) { logger.error("ERROR: salvando fichero {} : {}", fichero, e);}
    }
}
