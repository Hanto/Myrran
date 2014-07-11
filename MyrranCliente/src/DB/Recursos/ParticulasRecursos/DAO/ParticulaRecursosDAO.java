package DB.Recursos.ParticulasRecursos.DAO;// Created by Hanto on 10/07/2014.

import View.Classes.Actores.Particula;
import View.Classes.Actores.Particula.PoolParticulas;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Disposable;

public interface ParticulaRecursosDAO extends Disposable
{
    public ParticleEffect getParticleEffect(String nombreEfecto);
    public PoolParticulas getPoolParticulas (String nombreEfecto);
    public Particula obtain (String nombreEfecto);

    public void crearPool (String nombreEfecto, int min, int max);
    public void eliminarPool (String nombreEfecto);
    public void free (Particula particula);
}
