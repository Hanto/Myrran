package DB.Recursos.FuentesRecursos;// Created by Hanto on 02/05/2014.

import Model.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class FuentesRecursosXMLDB
{
    private static class Singleton              { private static final FuentesRecursosXMLDB get = new FuentesRecursosXMLDB(); }
    public static FuentesRecursosXMLDB get()    { return Singleton.get; }

    private Map<String, BitmapFont>listaDeFuentes = new HashMap<>();
    private String ficheroTexturas = Settings.RECURSOS_XML+ Settings.XML_TexturasFuentes;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public Map<String, BitmapFont>getListaDeFuentes()   { return listaDeFuentes; }

    private FuentesRecursosXMLDB()
    {   cargarDatos(); }



    public void cargarDatos()
    {
        logger.info("Cargando [FUENTES] desde {}", ficheroTexturas);

        SAXBuilder builder = new SAXBuilder();
        InputStream fichero = abrirFichero(ficheroTexturas);

        try
        {
            Document documento = builder.build(fichero);
            Element rootNode = documento.getRootElement();

            List listaNodos = rootNode.getChild("Fuentes").getChildren("Textura");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo = (Element) listaNodos.get(i);
                String nombre = nodo.getText();

                BitmapFont fuente = new BitmapFont(Gdx.files.internal(Settings.ATLAS_Fuentes_LOC + nombre +".fnt"), false);
                listaDeFuentes.put(nombre, fuente);

                logger.trace("TexturaTexto: " + nombre);
            }
            logger.trace("");
        }
        catch (Exception e) { logger.error("ERROR: leyendo fuente de fichero {}:"+e, ficheroTexturas); }
    }

    public void salvarDatos()
    {
        logger.info("Salvando [FUENTES] en {}", ficheroTexturas);

        Element fuenteRoot;
        Element fuente;

        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroTexturas);

        try
        {
            Document doc = builder.build(input);

            doc.getRootElement().removeChildren("Fuentes");
            fuenteRoot = new Element("Fuentes");

            for (Map.Entry<String, BitmapFont> entry: listaDeFuentes.entrySet())
            {
                fuente = new Element("Textura");
                fuente.setText(entry.getKey());
                fuenteRoot.addContent(fuente);
                logger.trace("Textura {} salvada", fuente.getText());
            }
            doc.getRootElement().addContent(fuenteRoot);

            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(ficheroTexturas));
            logger.info("Datos salvados en fichero XML: {}", ficheroTexturas);
        }
        catch (Exception e) { logger.error("ERROR: escribiendo Fuentes en el fichero {}"+e, ficheroTexturas);}
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
