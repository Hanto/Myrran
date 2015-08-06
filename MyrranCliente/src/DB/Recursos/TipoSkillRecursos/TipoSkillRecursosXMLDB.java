package DB.Recursos.TipoSkillRecursos;// Created by Hanto on 05/08/2015.

import DB.Recursos.TipoSkillRecursos.DTO.TipoSpellRecursos;
import Model.Settings;
import ch.qos.logback.classic.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoSkillRecursosXMLDB
{
    private static class Singleton              { private static final TipoSkillRecursosXMLDB get = new TipoSkillRecursosXMLDB(); }
    public static TipoSkillRecursosXMLDB get()  { return Singleton.get; }

    private Map<String, TipoSpellRecursos> listaTipoSpell = new HashMap<>();
    private String ficheroTipoSpellRecursos = Settings.RECURSOS_XML + Settings.XML_DataTipoSpells;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TipoSpellRecursos> getListaTipoSpell()           { return listaTipoSpell; }

    private TipoSkillRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {   cargarTipoSpellRecursos(); }

    public void cargarTipoSpellRecursos()
    {
        logger.info("Cargando [TIPO SPELL RECURSOS] desde {}", ficheroTipoSpellRecursos);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTipoSpellRecursos);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("TipoSpell");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i).getChild("Recursos");

                String iD           = listaNodos.get(i).getAttributeValue("ID");
                String nombreIcono  = nodo.getAttributeValue("icono");
                int numAnimaciones  = nodo.getAttribute("numAnimaciones").getIntValue();

                TipoSpellRecursos tipoSpellRecursos =  new TipoSpellRecursos(iD, nombreIcono, numAnimaciones);

                logger.debug("Spell:          {}", iD);
                logger.trace("Icono:          {}", nombreIcono);
                logger.trace("numAnimaciones: {}", numAnimaciones);

                List<Element> listaNodosAnimacion = listaNodos.get(i).getChildren("Animacion");

                for (Element nodoAnimacion : listaNodosAnimacion)
                {
                    int tipoAnimacion = nodoAnimacion.getAttribute("ID").getIntValue();
                    String nombrePixie= nodoAnimacion.getAttributeValue("Pixie");

                    logger.trace("TipoAnimacion:  {}", tipoAnimacion);
                    logger.trace("NombreAnimacion:{}", nombrePixie);

                    tipoSpellRecursos.addNombreAnimacion(tipoAnimacion, nombrePixie);
                }
                listaTipoSpell.put(tipoSpellRecursos.getIDTipoSpell(), tipoSpellRecursos);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTipoSpellRecursos, e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
