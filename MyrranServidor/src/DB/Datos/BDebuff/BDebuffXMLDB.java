package DB.Datos.BDebuff;// Created by Hanto on 16/06/2014.

import DAO.BDebuff.BDebuffXMLDBI;
import DB.DAO;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillStatI;
import Interfaces.Misc.Spell.TipoBDebuffI;
import Model.Settings;
import Model.Skills.BDebuff.BDebuff;
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

public class BDebuffXMLDB implements BDebuffXMLDBI
{
    private static class Singleton      { private static final BDebuffXMLDB get = new BDebuffXMLDB(); }
    public static BDebuffXMLDB get()    { return Singleton.get; }

    private Map<String, BDebuffI> listaDeBDebuffs = new HashMap<>();
    private String ficheroDebuffs = Settings.RECURSOS_XML + Settings.XML_DataBDebuffs;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<String, BDebuffI> getListaDeBDebuffs() { return listaDeBDebuffs;}

    private BDebuffXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        logger.info("Cargando [DEBUFFS] desde {}", ficheroDebuffs);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroDebuffs);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Debuff");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                boolean isDebuff    = Boolean.parseBoolean(nodo.getAttributeValue("isDebuff"));
                byte stacksMaximos  = Byte.parseByte(nodo.getAttributeValue("stacksMaximos"));
                String tipoBDebuff  = nodo.getAttributeValue("tipoBDebuff");
                String descripcion  = nodo.getChildText("Descripcion");

                TipoBDebuffI tipoBDebuffI =  DAO.tipoBDebuffDAOFactory.getTipoBDebuffDAO().getTipoBDebuff(tipoBDebuff);
                BDebuffI debuff = new BDebuff(tipoBDebuffI);
                debuff.setID(iD);
                debuff.setNombre(nombre);
                debuff.setDescripcion(descripcion);
                debuff.setIsDebuff(isDebuff);
                debuff.setStacksMaximos(stacksMaximos);

                logger.debug("BDEBUFF:        {}", iD);
                logger.trace("nombre:         {}", nombre);
                logger.trace("Descripcion:    {}", descripcion);
                logger.trace("isDebuff:       {}", isDebuff);
                logger.trace("stacksMaximos:  {}", stacksMaximos);
                logger.trace("TipoDebuff:     {}", tipoBDebuff);

                if (iD == null || nombre == null || tipoBDebuff == null || descripcion == null || stacksMaximos == 0)
                {   logger.error("Error parseando los datos del BDebuff, campos erroneos", iD);}

                List<Element> listaStats = nodo.getChildren("SkillStat");

                for (int j = 0; j < listaStats.size(); j++)
                {
                    if (listaStats.size() < debuff.getTipoBDebuff().getNumSkillStats()) logger.error("Faltan SkillStats por definir");

                    Element stat = listaStats.get(j);

                    byte id             = Byte.parseByte(stat.getAttributeValue("ID"));
                    String nombreStat   = stat.getAttributeValue("nombre");
                    float valorBase     = Float.parseFloat(stat.getAttributeValue("valorBase"));
                    boolean isMejorable = Boolean.parseBoolean(stat.getAttributeValue("isMejorable"));
                    int talentoMaximo   = Integer.parseInt(stat.getAttributeValue("talentoMaximo"));
                    int costeTalento    = Integer.parseInt(stat.getAttributeValue("costeTalento"));
                    float bonoTalento   = Float.parseFloat(stat.getAttributeValue("bonoTalento"));

                    debuff.getSkillStat(id).setBaseStats(id, nombreStat, valorBase);
                    if (isMejorable) debuff.getSkillStat(id).setTalentosStats(talentoMaximo, costeTalento, bonoTalento);
                    else debuff.getSkillStat(id).setIsMejorable(isMejorable);

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

                listaDeBDebuffs.put(debuff.getID(), debuff);
            }
            if (listaDeBDebuffs.size()==0)
                logger.error("No se ha encontrado ningun Dato valido en el fichero [DEBUFFS] {}", ficheroDebuffs);
        }
        catch (Exception e) { logger.error("ERROR: con el fichero XML de datos de {}", ficheroDebuffs, e); }
    }

    @Override public void salvarDatos()
    {
        logger.info("Salvando [DEBUFFS] en {}", ficheroDebuffs);
        Document doc = new Document();
        Element debuff;
        Element element;
        SkillStatI skillStat;

        //Crear root:
        doc.setRootElement(new Element("BDebuffs"));

        for (Map.Entry<String, BDebuffI> entry: listaDeBDebuffs.entrySet())
        {
            debuff = new Element("Debuff");
            debuff.setAttribute("ID", entry.getValue().getID());
            debuff.setAttribute("nombre", entry.getValue().getNombre());
            debuff.setAttribute("isDebuff", Boolean.toString(entry.getValue().isDebuff()));
            debuff.setAttribute("stacksMaximos", Byte.toString(entry.getValue().getStacksMaximos()));
            debuff.setAttribute("tipoBDebuff", entry.getValue().getTipoBDebuff().getID());

            element = new Element("Descripcion");
            element.setText(entry.getValue().getDescripcion());
            debuff.addContent(element);

            Iterator<SkillStatI> stat = entry.getValue().getSkillStats();
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
                debuff.addContent(element);
            }
            doc.getRootElement().addContent(debuff);
            logger.debug("DEBUFF: {} salvado", debuff.getAttributeValue("ID"));
        }
        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroDebuffs));
            logger.info("Datos salvados en fichero XML: {}", ficheroDebuffs);
        }
        catch (Exception e) { logger.error("ERROR: salvando [DEBUFFS] en fichero: {}", ficheroDebuffs, e);}

    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
