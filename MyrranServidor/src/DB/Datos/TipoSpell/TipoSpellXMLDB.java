package DB.Datos.TipoSpell;// Created by Hanto on 17/04/2014.

import Core.Skills.SkillStat;
import DAO.TipoSpell.TipoSpellXMLDBI;
import Data.Settings;
import Interfaces.Spell.TipoSpellI;
import Model.Classes.Skill.Spell.TipoSpell;
import Model.Classes.Skill.Spell.TipoSpellFactory;
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

public class TipoSpellXMLDB implements TipoSpellXMLDBI
{
    private static class Singleton          { private static final TipoSpellXMLDB get = new TipoSpellXMLDB(); }
    public static TipoSpellXMLDB get()    { return Singleton.get; }

    private Map<String, TipoSpellI>  listaDeTipoSpells = new HashMap<>();
    private String ficheroTipoSpells = Settings.RECURSOS_XML + Settings.XML_DataTipoSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<String, TipoSpellI> getListaDeTipoSpells() { return listaDeTipoSpells; }

    private TipoSpellXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        for (TipoSpellFactory tipoSpellFactory: TipoSpellFactory.values())
        {
            TipoSpell tipoSpell = tipoSpellFactory.nuevo();
            tipoSpell.setID(tipoSpellFactory.name());
            listaDeTipoSpells.put(tipoSpell.getID(), tipoSpell);
        }

        logger.debug("Cargando datos desde {}", ficheroTipoSpells);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTipoSpells);

        try
        {
            Document documento = builder.build(input);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChildren("TipoSpell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                String descripcion  = nodo.getChildText("Descripcion");

                TipoSpellI tipoSpell =  listaDeTipoSpells.get(iD);
                tipoSpell.setID(iD);
                tipoSpell.setNombre(nombre);
                tipoSpell.setDescripcion(descripcion);

                logger.info ("TIPO SPELL:     {}", iD);
                logger.debug("nombre:         {}", nombre);
                logger.debug("Descripcion:    {}", descripcion);

                if (iD == null || nombre == null || descripcion == null)
                {   logger.error("Error parseando los datos del TipoBDebuff, campos erroneos", iD);}

                List listaStats = nodo.getChildren("SkillStat");

                for (int j = 0; j < listaStats.size(); j++)
                {
                    if (listaStats.size() < tipoSpell.getNumSkillStats()) logger.error("Faltan SkillStats por definir");

                    Element stat = (Element) listaStats.get(j);

                    byte id             = Byte.parseByte(stat.getAttributeValue("ID"));
                    String nombreStat   = stat.getAttributeValue("nombre");
                    float valorBase     = Float.parseFloat(stat.getAttributeValue("valorBase"));
                    boolean isMejorable = Boolean.parseBoolean(stat.getAttributeValue("isMejorable"));
                    int talentoMaximo   = Integer.parseInt(stat.getAttributeValue("talentoMaximo"));
                    int costeTalento    = Integer.parseInt(stat.getAttributeValue("costeTalento"));
                    float bonoTalento   = Float.parseFloat(stat.getAttributeValue("bonoTalento"));

                    tipoSpell.setSkillStat(new SkillStat(id, nombreStat, valorBase), id);
                    if (isMejorable) tipoSpell.getSkillStat(id).setTalentos(talentoMaximo, costeTalento, bonoTalento);
                    else tipoSpell.getSkillStat(id).setIsMejorable(isMejorable);

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

                listaDeTipoSpells.put(tipoSpell.getID(), tipoSpell);
            }
            if (listaDeTipoSpells.size()==0)
                logger.error("No se ha encontrado ningun Dato valido en el fichero {}", ficheroTipoSpells);
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}: ", ficheroTipoSpells, e); }
    }

    @Override public void salvarDatos()
    {
        logger.debug("Salvando datos en {}", ficheroTipoSpells);
        Document doc = new Document();
        Element tspell;
        Element element;
        SkillStat skillStat;

        //Crear root:
        doc.setRootElement(new Element("TipoSpells"));

        for (Map.Entry<String, TipoSpellI> entry: listaDeTipoSpells.entrySet())
        {
            tspell = new Element("TipoSpell");
            tspell.setAttribute("ID", entry.getValue().getID());
            tspell.setAttribute("nombre", entry.getValue().getNombre());

            element = new Element("Descripcion");
            element.setText(entry.getValue().getDescripcion());
            tspell.addContent(element);

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
                tspell.addContent(element);
            }
            doc.getRootElement().addContent(tspell);
            logger.debug("TIPOSPELL: {} salvado", tspell.getAttributeValue("ID"));
        }
        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTipoSpells));
            logger.info("Datos salvados en fichero XML: {}", ficheroTipoSpells);
        }
        catch (Exception e) { logger.error("ERROR: salvando datos Spells en fichero: {}", ficheroTipoSpells, e);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
