package DB.Datos.Spell;// Created by Hanto on 17/04/2014.

import Model.Skills.SkillStat;
import DAO.Spell.SpellXMLDBI;
import DB.DAO;
import Model.Settings;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.Spell.TipoSpellI;
import Model.Skills.Spell.Spell;
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
    private Map<String, Element> listaRecursos = new HashMap<>();
    private String ficheroSpells = Settings.RECURSOS_XML+ Settings.XML_DataSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<String, SpellI> getListaDeSpells() { return listaDeSpells; }

    public SpellXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        logger.info("Cargando [SPELLS] desde {}", ficheroSpells);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSpells);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                String tipoSpell    = nodo.getAttributeValue("tipoSpell");
                String descripcion  = nodo.getChildText("Descripcion");
                String icono        = nodo.getChild("Recursos").getAttributeValue("icono");

                TipoSpellI tipoSpellI =  DAO.tipoSpellDAOFactory.getTipoSpellDAO().getTipoSpell(tipoSpell);
                SpellI spell = new Spell(tipoSpellI);
                spell.setID(iD);
                spell.setNombre(nombre);
                spell.setDescripcion(descripcion);

                logger.debug("SPELL:          {}", iD);
                logger.trace("nombre:         {}", nombre);
                logger.trace("TipoSpell:      {}", tipoSpell);
                logger.trace("Descripcion:    {}", descripcion);
                logger.trace("Icono:          {}", icono);

                if (iD == null || nombre == null || tipoSpell == null || icono == null || descripcion == null )
                {   logger.error("Error parseando los datos de [SPELL] {}, campos erroneos", iD);}

                List listaDebuffs = nodo.getChildren("Debuff");

                for (int j = 0; j < listaDebuffs.size(); j++)
                {
                    String debuffID = ((Element)listaDebuffs.get(j)).getText();
                    BDebuffI debuff = DAO.debuffDAOFactory.getBDebuffDAO().getBDebuff(debuffID);
                    spell.añadirDebuff(debuff);

                    logger.trace("Aplica debuff:  {}", debuffID);
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

                    spell.getSkillStat(id).setSkillStats(id, nombreStat, valorBase);
                    if (isMejorable) spell.getSkillStat(id).setTalentosStats(talentoMaximo, costeTalento, bonoTalento);
                    else spell.getSkillStat(id).setIsMejorable(isMejorable);

                    logger.trace("  id:           {}", id);
                    logger.trace("  nombreStat:   {}", nombreStat);
                    logger.trace("  valorBase:    {}", valorBase);
                    logger.trace("  isMejorable:  {}", isMejorable);

                    if (isMejorable)
                    {
                        logger.trace("  talentoMaximo:{}", talentoMaximo);
                        logger.trace("  costeTalento: {}", costeTalento);
                        logger.trace("  bonoTalento:  {}", bonoTalento);
                    }
                }
                logger.trace("");

                listaDeSpells.put(spell.getID(), spell);
            }
            if (listaDeSpells.size()==0)
                logger.error("No se ha encontrado ningun Dato valido en el fichero [SPELLS] {}", ficheroSpells);
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero [SPELLS] {}: ", ficheroSpells, e); }
    }

    @Override public void salvarDatos()
    {
        logger.info("Salvando [SPELLS] en {}", ficheroSpells);
        Document doc = new Document();
        Element spell;
        Element element;
        SkillStat skillStat;

        actualizarListaRecursos();

        //Crear root
        doc.setRootElement(new Element("Spells"));

        //Creamos nodos:
        for (Map.Entry<String, SpellI> entry: listaDeSpells.entrySet())
        {
            spell = new Element("Spell");
            spell.setAttribute("ID", entry.getValue().getID());
            spell.setAttribute("nombre", entry.getValue().getNombre());
            spell.setAttribute("tipoSpell", entry.getValue().getTipoSpell().getID());

            //añadimos los Recursos:
            element = listaRecursos.get(entry.getValue().getID());
            if (element != null) spell.addContent(element);

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
                element.setAttribute("talentoMaximo", Integer.toString(skillStat.getTalentosMaximos()));
                spell.addContent(element);
            }
            doc.getRootElement().addContent(spell);
            logger.debug("SPELLS: {} salvado", spell.getAttributeValue("ID"));
        }

        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroSpells));
            logger.info("[SPELLS] salvados en fichero XML: {}", ficheroSpells);
        }
        catch (Exception e) { logger.error("ERROR: salvando [SPELLS] en fichero: {}: "+e, ficheroSpells);}
    }

    private void actualizarListaRecursos()
    {
        Document doc;
        Element nodo;
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroSpells);
        listaRecursos.clear();

        try
        {
            doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Spell");

            for (int i = 0; i< listaNodos.size() ; i++)
            {
                nodo = listaNodos.get(i).getChild("Recursos");
                listaRecursos.put(listaNodos.get(i).getAttributeValue("ID"), nodo.detach());
            }
        }
        catch (Exception e) { logger.error("ERROR: leyendo Recursos de fichero [SPELLS]: {}: "+e, ficheroSpells);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
