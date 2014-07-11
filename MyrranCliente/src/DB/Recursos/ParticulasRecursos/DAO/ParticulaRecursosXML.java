package DB.Recursos.ParticulasRecursos.DAO;// Created by Hanto on 10/07/2014.

import DB.Recursos.ParticulasRecursos.ParticulaRecursosXMLDB;
import View.Classes.Actores.Particula;
import View.Classes.Actores.Particula.PoolParticulas;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.ObjectMap.Values;
import org.slf4j.LoggerFactory;

public class ParticulaRecursosXML implements ParticulaRecursosDAO
{
    private ObjectMap<String, ParticleEffect> listaParticleEffects;
    private ObjectMap<String, PoolParticulas> listaPoolParticulas;
    private ParticulaRecursosXMLDB particulaRecursosXMLDB;
    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    //CONSTRUCTOR:
    public ParticulaRecursosXML(ParticulaRecursosXMLDB particulaRecursosXMLDB)
    {
        this.particulaRecursosXMLDB = particulaRecursosXMLDB;
        listaParticleEffects = particulaRecursosXMLDB.getListaParticleEffects();
        listaPoolParticulas = particulaRecursosXMLDB.getListaPoolParticulas();
    }




    @Override public ParticleEffect getParticleEffect(String nombreEfecto)
    {
        ParticleEffect efecto = listaParticleEffects.get(nombreEfecto);
        if (efecto == null) { logger.error("getParticle Effect: No existe Particle Effecto con este nombre {}", nombreEfecto); return null; }
        return listaParticleEffects.get(nombreEfecto);
    }

    @Override public PoolParticulas getPoolParticulas (String nombreEfecto)
    {
        PoolParticulas pool = listaPoolParticulas.get(nombreEfecto);
        if (pool == null) { logger.error("getPoolParticulas: No existe Pool de particulas con este nombre: {}", nombreEfecto); return null; }
        return pool;
    }

    @Override public Particula obtain (String nombreEfecto)
    {
        PoolParticulas pool = listaPoolParticulas.get(nombreEfecto);
        if (pool == null) { logger.error("Obtain: No existe Pool de particulas con este nombre: {}", nombreEfecto); return null; }
        return pool.obtain();
    }

    @Override public void crearPool (String nombreEfecto, int min, int max)
    {
        ParticleEffect efecto = listaParticleEffects.get(nombreEfecto);
        if (efecto == null) { logger.error("Crear Pool: No existe Particle Effecto con este nombre {}", nombreEfecto); return; }
        PoolParticulas pool = new PoolParticulas(efecto, min, max);
        listaPoolParticulas.put(nombreEfecto, pool);
    }

    @Override public void eliminarPool (String nombreEfecto)
    {
        PoolParticulas pool = listaPoolParticulas.get(nombreEfecto);
        if (pool == null) { logger.error("Eliminar Pool: No existe Pool de particulas con este nombre: {}", nombreEfecto); return; }
        pool.clear();
        listaPoolParticulas.remove(nombreEfecto);
    }

    @Override public void free (Particula particula)
    {   particula.free(); }

    @Override public void dispose()
    {
        Values<PoolParticulas> valuesParticulas = listaPoolParticulas.values();
        while(valuesParticulas.hasNext())
        {   valuesParticulas.next().clear(); }

        for (Entry<String, ParticleEffect> entry: listaParticleEffects.entries())
        {
            logger.trace("DISPOSE: Liberando efecto particulas {}", entry.key);
            entry.value.dispose();
        }
    }
}
