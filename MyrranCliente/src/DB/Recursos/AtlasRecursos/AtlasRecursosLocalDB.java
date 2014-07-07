package DB.Recursos.AtlasRecursos;// Created by Hanto on 10/04/2014.

import Data.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import org.slf4j.LoggerFactory;

public class AtlasRecursosLocalDB
{
    private static class Singleton  { private static final AtlasRecursosLocalDB get = new AtlasRecursosLocalDB(); }
    public static AtlasRecursosLocalDB get()       { return Singleton.get; }

    public TextureAtlas atlas;

    //Constructor:
    private AtlasRecursosLocalDB()
    {   cargarDatos(); }


    private void cargarDatos()
    {
        Logger logger = (Logger)LoggerFactory.getLogger(this.getClass());

        if (Settings.ATLAS_GenerarAtlas)
        {
            logger.info("Regenerando ATLAS a partir de los recursos almacenados en {}", Settings.RECURSOS_Atlas_Carpeta_Imagenes_Origen);
            TexturePacker.process(Settings.RECURSOS_Atlas_Carpeta_Imagenes_Origen, Settings.RECURSOS_Atlas_Carpeta_Imagenes_Destino, Settings.RECURSOS_Atlas_Atlas_Extension);
        }

        //Cargamos el atlas en memoria
        atlas = new TextureAtlas(Gdx.files.internal(Settings.RECURSOS_Atlas_Carpeta_Imagenes_Destino + Settings.RECURSOS_Atlas_Atlas_Extension +".atlas"));
    }
}
