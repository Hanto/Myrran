package DB.Recursos.FuentesRecursos.DAO;// Created by Hanto on 02/05/2014.

import DB.Recursos.FuentesRecursos.FuentesRecursosXMLDB;
import Data.Settings;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FuentesRecursosXML implements FuentesRecursosDAO
{
    private Map<String, BitmapFont>listaDeFuentes;
    private FuentesRecursosXMLDB fuentesRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public FuentesRecursosXML(FuentesRecursosXMLDB fuentesRecursosXMLDB)
    {
        this.fuentesRecursosXMLDB = fuentesRecursosXMLDB;
        this.listaDeFuentes = fuentesRecursosXMLDB.getListaDeFuentes();
    }

    @Override public void salvarFuente(String nombreFuente, String nombreTextura, TextureAtlas atlas)
    {
        BitmapFont fuente = new BitmapFont(Gdx.files.internal(Settings.ATLAS_Fuentes_LOC +nombreTextura), false);
        listaDeFuentes.put(nombreFuente, fuente);
        fuentesRecursosXMLDB.salvarDatos();
    }

    @Override public BitmapFont getFuente(String nombreFuente)
    {   return listaDeFuentes.get(nombreFuente); }

    @Override public void dispose()
    {
        for (Map.Entry<String, BitmapFont> entry: listaDeFuentes.entrySet())
        {
            logger.trace("DISPOSE: Liberando fuente {}", entry.getKey());
            entry.getValue().dispose();
        }
    }
}
