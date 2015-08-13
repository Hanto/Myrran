package Model.AbstractClases;// Created by Hanto on 12/08/2015.

import Interfaces.EntidadesPropiedades.Dinamico;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesPropiedades.Orientable;
import Interfaces.EntidadesPropiedades.Steerable2D;
import Model.Settings;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractSteerable extends AbstractModel implements Steerable2D, Espacial, Dinamico, Orientable
{
    protected Vector2 posicion = new Vector2();             // Espacial:
    protected int ultimoMapTileX;
    protected int ultimoMapTileY;
    protected Vector2 velocidad = new Vector2();            // Dinamico:
    protected float velocidadMax = 40f;
    protected float aceleracionMax = 50f;
    protected float velocidadMod = 1.0f;
    protected float velocidadAngular;
    protected float velocidadAngularMax = 5f;
    protected float aceleracionAngularMax = 10f;
    protected float orientacion = 0;                        // Orientable:
    protected boolean encaramientoIndependiente = false;    // Steerable:
    protected boolean tagged = false;
    protected SteeringBehavior<Vector2> steeringBehavior;   // AbstractSteerable:
    protected SteeringAcceleration<Vector2> steeringOutput;

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getPosition()                                  { return posicion; }
    @Override public float getX()                                           { return posicion.x; }
    @Override public float getY()                                           { return posicion.y; }
    @Override public int getMapTileX()                                      { return (int)(posicion.x / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getMapTileY()                                      { return (int)(posicion.y / (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoMapTileX()                                { return ultimoMapTileX; }
    @Override public int getUltimoMapTileY()                                { return ultimoMapTileY; }
    @Override public void setUltimoMapTile (int x, int y)                   { ultimoMapTileX = x; ultimoMapTileY = y; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getVelocidad()                                 { return velocidad; }
    @Override public float getVelocidadMax()                                { return velocidadMax; }
    @Override public float getAceleracionMax()                              { return aceleracionMax; }
    @Override public float getVelocidadMod()                                { return velocidadMod; }
    @Override public float getVelocidadAngular()                            { return velocidadAngular; }
    @Override public float getVelocidadAngularMax()                         { return velocidadAngularMax; }
    @Override public float getAceleracionAngularMax()                       { return aceleracionAngularMax; }
    @Override public void setVelocidadMax(float velocidadMax)               { this.velocidadMax = velocidadMax; }
    @Override public void setAceleracionMax(float aceleracionMax)           { this.aceleracionMax = aceleracionMax; }
    @Override public void setVelocidaMod(float velocidadMod)                { this.velocidadMod = velocidadMod; }
    @Override public void setVelocidadAngular(float velocidadAngular)       { this.velocidadAngular = velocidadAngular; }
    @Override public void setVelocidadAngularMax(float velocidadAngularMax) { this.velocidadAngularMax = velocidadAngularMax; }
    @Override public void setAceleracionAngularMax(float aceleracionAngularMax){this.aceleracionAngularMax = aceleracionAngularMax; }

    // ORIENTABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getOrientacion()                                 { return orientacion; }
    @Override public void setOrientacion(float angulo)                      { this.orientacion = angulo; }


    public AbstractSteerable()
    {   steeringOutput = new SteeringAcceleration<Vector2>(new Vector2()); }

    // STEERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getLinearVelocity()        { return getVelocidad(); }
    @Override public float getMaxLinearSpeed()          { return getVelocidadMax(); }
    @Override public float getMaxLinearAcceleration()   { return getAceleracionMax(); }
    @Override public float getAngularVelocity()         { return getVelocidadAngular(); }
    @Override public float getMaxAngularSpeed()         { return getVelocidadAngularMax(); }
    @Override public float getMaxAngularAcceleration()  { return getAceleracionAngularMax(); }

    @Override public void setMaxLinearSpeed(float maxLinearSpeed)
    {   setVelocidadMax(maxLinearSpeed); }

    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {   setAceleracionMax(maxLinearAcceleration); }

    @Override public void setMaxAngularSpeed(float maxAngularSpeed)
    {   setVelocidadAngularMax(maxAngularSpeed); }

    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {   setAceleracionAngularMax(maxAngularAcceleration); }

    @Override public float getOrientation()
    {   return getOrientacion(); }

    @Override public boolean isTagged()
    {   return tagged; }

    @Override public void setTagged(boolean tagged)
    {   this.tagged = tagged; }

    @Override public float getBoundingRadius()
    {   return 24; }

    // TIPO DE STEERING:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior)
    {   this.steeringBehavior = steeringBehavior; }

    // OPERACIONES MATEMATICAS::
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 newVector()
    {   return new Vector2(); }

    @Override public float vectorToAngle(Vector2 vector)
    {   return (float)Math.atan2(-vector.x, vector.y); }

    @Override public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    // CALCULO STEERING:
    //------------------------------------------------------------------------------------------------------------------

    public void calcularSteering(float delta)
    {
        steeringBehavior.calculateSteering(steeringOutput);
        aplicarSteering(steeringOutput, delta);
    }

    private void aplicarSteering(SteeringAcceleration<Vector2> steering, float delta)
    {
        this.getPosition().mulAdd(getVelocidad(), delta);
        this.getVelocidad().mulAdd(steering.linear, delta).limit(this.getMaxLinearSpeed());

        if (encaramientoIndependiente)
        {
            setOrientacion(getOrientacion() + getVelocidadAngular() * delta);
            setVelocidadAngular(getVelocidadAngular() + steering.angular * delta);
        }
        else
        {
            float nuevaOrientacion;
            if ( this.getVelocidad().isZero(0.001f)) nuevaOrientacion = this.getOrientation();
            else nuevaOrientacion = this.vectorToAngle(this.getVelocidad());

            if (nuevaOrientacion != getOrientacion())
            {
                setVelocidadAngular((nuevaOrientacion - getOrientacion()) * delta);
                setOrientacion(nuevaOrientacion);
            }
        }
    }
}
