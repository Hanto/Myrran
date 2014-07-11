package DB.Recursos.ParticulasRecursos;// Created by Hanto on 10/07/2014.

import Data.Settings;
import View.Classes.Actores.Particula.PoolParticulas;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ObjectMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class ParticulaRecursosXMLDB
{
    private static class Singleton              { private static final ParticulaRecursosXMLDB get = new ParticulaRecursosXMLDB(); }
    public static ParticulaRecursosXMLDB get()  { return Singleton.get; }

    private ObjectMap<String, ParticleEffect> listaParticleEffects = new ObjectMap<>();
    private ObjectMap<String, PoolParticulas> listaPoolParticulas = new ObjectMap<>();
    private String ficheroParticulas = Settings.RECURSOS_XML+Settings.XML_Particulas;
    private String carpetaParticulas = Settings.RECURSOS_CarpetaParticulas;
    private String carpetaImagenesParticulas = Settings.RECURSOS_CarpetaImagesParticulas;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public ObjectMap<String, ParticleEffect> getListaParticleEffects()  { return listaParticleEffects; }
    public ObjectMap<String, PoolParticulas> getListaPoolParticulas()   { return listaPoolParticulas; }

    public ParticulaRecursosXMLDB()
    {   cargarDatos(); }

    public void cargarDatos()
    {
        logger.info("Cargando [PARTICULAS] desde {}", ficheroParticulas);
        SAXBuilder builder = new SAXBuilder();
        InputStream input = abrirFichero(ficheroParticulas);

        try
        {
            Document doc = builder.build(input);
            List<Element> listaNodos = doc.getRootElement().getChildren("Particula");

            for (int i = 0; i < listaNodos.size(); i++)
            {
                Element nodo    = listaNodos.get(i);
                String nombre   = nodo.getText();

                ParticleEffect effect = new ParticleEffect();
                effect.load(Gdx.files.internal(carpetaParticulas+nombre+".p"), Gdx.files.internal(carpetaImagenesParticulas));
                listaParticleEffects.put(nombre, effect);
                logger.trace("Particula: {}", nombre);

            }
            logger.trace("");

            if (listaParticleEffects.size == 0)
                logger.error("No se han encontrado datos validos de [PARTICULAS] en el fichero {}", ficheroParticulas);
        }
        catch (Exception e) {logger.error("ERROR: con el fichero [PARTICULAS] de {}", ficheroParticulas, e); }
    }

    public InputStream abrirFichero(String rutaYNombreFichero)
    {   //probamos a Acceder al fichero directamente, en caso de poder acerlo lo transformamos en un InputStream
        try { return new FileInputStream(new File(rutaYNombreFichero)); }
        //En el caso de dar error por que el fichero no exista, probamos a acceder al recurso
        catch (Exception e) { return this.getClass().getClassLoader().getResourceAsStream(rutaYNombreFichero);}
    }
}
