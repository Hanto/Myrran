package DB.Recursos.AtlasRecursos.DAO;// Created by Hanto on 12/06/2014.

import DB.Recursos.AtlasRecursos.AtlasRecursosLocalDB;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.slf4j.LoggerFactory;

public class AtlasRecursosLocal implements AtlasRecursosDAO
{
    private TextureAtlas atlas = AtlasRecursosLocalDB.get().atlas;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @Override public TextureAtlas getAtlas()
    {   return atlas; }

    @Override public TextureRegion getTextura (String localizacion)
    {   return (new TextureRegion(atlas.findRegion(localizacion))); }

    @Override public void dispose()
    {   atlas.dispose(); logger.trace("DISPOSE: Liberando Atlas");}
}
