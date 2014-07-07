package DB.Datos.TipoBdDebuff;// Created by Hanto on 10/06/2014.

import Core.Skills.SkillStat;
import DAO.TipoBDebuff.TipoBDebuffXMLDBI;
import Data.Settings;
import Interfaces.BDebuff.TipoBDebuffI;
import Model.Classes.Skill.BDebuff.TipoBDebuff;
import Model.Classes.Skill.BDebuff.TipoBDebuffFactory;
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

public class TipoBDebuffXMLDB implements TipoBDebuffXMLDBI
{
    private static class Singleton          { private static final TipoBDebuffXMLDB get = new TipoBDebuffXMLDB(); }
    public static TipoBDebuffXMLDB get()    { return Singleton.get; }

    private Map<String, TipoBDebuffI> listaDeTipoBDebuffs = new HashMap<>();
    private String ficheroTipoDebuffs = Settings.RECURSOS_XML + Settings.XML_DataTipoBDebuffs;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public Map<String, TipoBDebuffI> getListaDeTipoBDebuffs() { return listaDeTipoBDebuffs; }

    private TipoBDebuffXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        for (TipoBDebuffFactory tipoBDebuffFactory: TipoBDebuffFactory.values())
        {
            TipoBDebuff tipoBDebuff = tipoBDebuffFactory.nuevo();
            tipoBDebuff.setID(tipoBDebuffFactory.name());
            listaDeTipoBDebuffs.put(tipoBDebuff.getID(), tipoBDebuff);
        }

        logger.debug("Cargando datos desde {}", ficheroTipoDebuffs);

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTipoDebuffs);

        try
        {
            Document documento = builder.build(input);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChildren("TDebuff");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);

                String iD           = nodo.getAttributeValue("ID");
                String nombre       = nodo.getAttributeValue("nombre");
                boolean isDebuff    = Boolean.parseBoolean(nodo.getAttributeValue("isDebuff"));
                byte stacksMaximos  = Byte.parseByte(nodo.getAttributeValue("stacksMaximos"));
                String descripcion  = nodo.getChildText("Descripcion");

                TipoBDebuffI tipoDebuff =  listaDeTipoBDebuffs.get(iD);
                tipoDebuff.setID(iD);
                tipoDebuff.setNombre(nombre);
                tipoDebuff.setDescripcion(descripcion);
                tipoDebuff.setIsDebuff(isDebuff);
                tipoDebuff.setStacksMaximos(stacksMaximos);

                logger.info ("TIPO DEBUFF:    {}", iD);
                logger.debug("nombre:         {}", nombre);
                logger.debug("isDebuff:       {}", isDebuff);
                logger.debug("stacksMaximos:  {}", stacksMaximos);
                logger.debug("Descripcion:    {}", descripcion);

                if (iD == null || nombre == null || descripcion == null || stacksMaximos == 0)
                {   logger.error("Error parseando los datos del TipoBDebuff, campos erroneos", iD);}

                List listaStats = nodo.getChildren("SkillStat");

                for (int j = 0; j < listaStats.size(); j++)
                {
                    if (listaStats.size() < tipoDebuff.getNumSkillStats()) logger.error("Faltan SkillStats por definir");

                    Element stat = (Element) listaStats.get(j);

                    byte id             = Byte.parseByte(stat.getAttributeValue("ID"));
                    String nombreStat   = stat.getAttributeValue("nombre");
                    float valorBase     = Float.parseFloat(stat.getAttributeValue("valorBase"));
                    boolean isMejorable = Boolean.parseBoolean(stat.getAttributeValue("isMejorable"));
                    int talentoMaximo   = Integer.parseInt(stat.getAttributeValue("talentoMaximo"));
                    int costeTalento    = Integer.parseInt(stat.getAttributeValue("costeTalento"));
                    float bonoTalento   = Float.parseFloat(stat.getAttributeValue("bonoTalento"));

                    tipoDebuff.setSkillStat(new SkillStat(id, nombreStat, valorBase), id);
                    if (isMejorable) tipoDebuff.getSkillStat(id).setTalentos(talentoMaximo, costeTalento, bonoTalento);
                    else tipoDebuff.getSkillStat(id).setIsMejorable(isMejorable);

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

                listaDeTipoBDebuffs.put(tipoDebuff.getID(), tipoDebuff);
            }
            if (listaDeTipoBDebuffs.size()==0)
                logger.error("No se ha encontrado ningun Dato valido en el fichero {}", ficheroTipoDebuffs);
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}: ", ficheroTipoDebuffs, e); }
    }

    @Override public void salvarDatos()
    {
        logger.debug("Salvando datos en {}", ficheroTipoDebuffs);
        Document doc = new Document();
        Element debuff;
        Element element;
        SkillStat skillStat;

        //Crear root:
        doc.setRootElement(new Element("TipoBDebuffs"));

        for (Entry<String, TipoBDebuffI> entry: listaDeTipoBDebuffs.entrySet())
        {
            debuff = new Element("TDebuff");
            debuff.setAttribute("ID", entry.getValue().getID());
            debuff.setAttribute("nombre", entry.getValue().getNombre());
            debuff.setAttribute("isDebuff", Boolean.toString(entry.getValue().getIsDebuff()));
            debuff.setAttribute("stacksMaximos", Byte.toString(entry.getValue().getStacksMaximos()));

            element = new Element("Descripcion");
            element.setText(entry.getValue().getDescripcion());
            debuff.addContent(element);

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
                debuff.addContent(element);
            }
            doc.getRootElement().addContent(debuff);
            logger.debug("DEBUFF: {} salvado", debuff.getAttributeValue("ID"));
        }

        try
        {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTipoDebuffs));
            logger.info("Datos salvados en fichero XML: {}", ficheroTipoDebuffs);
        }
        catch (Exception e) { logger.error("ERROR: salvando datos Spells en fichero: {}", ficheroTipoDebuffs, e);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
