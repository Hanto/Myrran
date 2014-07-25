package View.Classes.Actores;// Created by Hanto on 09/07/2014.


import Data.Settings;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Particula extends Actor implements Poolable
{
    public static class PoolParticulas extends Pool<Particula>
    {
        //guardamos una referencia del efecto para crear particulas de ese tipo
        private final ParticleEffect effect;

        //Constructor:
        public PoolParticulas (ParticleEffect effect, int capacidadInicial, int capacidadMax)
        {   super(capacidadInicial, capacidadMax); this.effect = effect; }

        @Override protected Particula newObject()
        {   return new Particula(effect, this); }
    }

    //si la particula viene de un pool guardamos una referencia de este para poderla liberar (free)
    private ParticleEffect effect;
    private PoolParticulas poolParticulas;

    //Provisional(BORRAR)
    public PointLight luz;
    public Vector3 posicion = new Vector3();
    public Camera camara;


    public Particula(ParticleEffect effect)
    {
        this.effect = new ParticleEffect(effect);
        effect.start();
    }

    public Particula(ParticleEffect effect, PoolParticulas poolParticulas)
    {
        this.effect = new ParticleEffect(effect);
        this.poolParticulas = poolParticulas;
        effect.start();
    }

    public void setScale(float scale)
    {
        super.setScale(scale);
        float scaling;
        ParticleEmitter emisor;

        Array<ParticleEmitter> listaEmisores= effect.getEmitters();
        for (int i=0; i<listaEmisores.size; i++)
        {
            emisor = listaEmisores.get(i);

            //Size:
            scaling = emisor.getScale().getHighMax();
            emisor.getScale().setHigh(scaling * scale);

            scaling = emisor.getScale().getLowMax();
            emisor.getScale().setLow(scaling * scale);
            //Velocity:
            scaling = emisor.getVelocity().getHighMax();
            emisor.getVelocity().setHighMax(scaling * scale);

            scaling = emisor.getVelocity().getHighMin();
            emisor.getVelocity().setHighMin(scaling * scale);

            scaling = emisor.getVelocity().getLowMax();
            emisor.getVelocity().setLow(scaling * scale);
            //Gravity:
            scaling = emisor.getGravity().getHighMax();
            emisor.getGravity().setHighMax(scaling * scale);

            scaling = emisor.getGravity().getHighMin();
            emisor.getGravity().setHighMin(scaling * scale);

            scaling = emisor.getGravity().getLowMax();
            emisor.getGravity().setLow(scaling * scale);
            //Wind:
            scaling = emisor.getWind().getHighMax();
            emisor.getWind().setHighMax(scaling * scale);

            scaling = emisor.getWind().getHighMin();
            emisor.getWind().setHighMin(scaling * scale);

            scaling = emisor.getWind().getLowMax();
            emisor.getWind().setLow(scaling * scale);
        }
    }

    @Override public void draw (Batch batch, float alpha)
    {   effect.draw(batch, Gdx.graphics.getDeltaTime()); }

    public void act(float delta)
    {
        super.act(delta);

        posicion.x = Gdx.input.getX();
        posicion.y = Gdx.input.getY();

        camara.unproject(posicion);

        luz.setPosition(posicion.x *Settings.PIXEL_METROS, posicion.y*Settings.PIXEL_METROS);
        effect.setPosition(posicion.x, posicion.y);
    }

    public void free()
    {
        if (poolParticulas != null)
            poolParticulas.free(this);
    }

    @Override public void reset()
    {   effect.reset(); }
}
