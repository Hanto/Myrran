package DB.Recursos.MiscRecursos;// Created by Hanto on 02/05/2014.

import DB.RSC;
import Model.Settings;
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

public class MiscRecursosXMLDB
{
    private static class Singleton          { private static final MiscRecursosXMLDB get = new MiscRecursosXMLDB(); }
    public static MiscRecursosXMLDB get()   { return Singleton.get; }

    private Map<String, TextureRegion> listaDeTexturasMisc = new HashMap<>();
    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasMisc;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, TextureRegion> getListaDeTexturasMisc()  { return listaDeTexturasMisc; }

    private MiscRecursosXMLDB()
    {   cargarDatos(); }



    public void cargarDatos()
    {
        logger.info("Cargando [TEXTURAS MISC] desde {}", ficheroTexturas);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChild("TexturasMisc").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = listaNodos.get(i);
                String nombre = nodo.getText();

                TextureRegion textura = new TextureRegion(RSC.atlasRecursosDAO.getAtlasRecursosDAO().getAtlas().findRegion(Settings.ATLAS_TexturasMisc_LOC + nombre));
                listaDeTexturasMisc.put(nombre, textura);

                logger.trace("TexturaMisc: {}", nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fichero {}:", ficheroTexturas, e); }
    }

    public void salvarDatos()
    {
        logger.info("Salvando [TEXTURAS MISC] en {}", ficheroTexturas);
        Element texturaRoot;
        Element textura;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);
            doc.getRootElement().removeChildren("TexturasMisc");
            texturaRoot = new Element("TexturasMisc");

            for (Map.Entry<String, TextureRegion> entry: listaDeTexturasMisc.entrySet())
            {
                textura = new Element("Textura");
                textura.setText(entry.getKey());
                texturaRoot.addContent(textura);
                logger.trace("Textura {} salvada", textura.getText());
            }
            doc.getRootElement().addContent(texturaRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: leyendo/parseando el fichero {}", ficheroTexturas);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }

}
