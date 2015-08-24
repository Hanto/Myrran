package Model.AI.Steering;// Created by Hanto on 12/08/2015.

import Interfaces.EntidadesPropiedades.SteerableAgent;
import Model.AbstractModel;
import Model.Settings;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public abstract class AbstractSteerableAgent extends AbstractModel implements SteerableAgent
{
    protected Vector2 posicion = new Vector2();             // Espacial:
    protected int ultimoCuadranteX;
    protected int ultimoCuadranteY;
    protected Vector2 velocidad = new Vector2();            // Dinamico:
    protected float velocidadMax = 40f;
    protected float aceleracionMax = 50f;
    protected float velocidadMod = 1.0f;
    protected float velocidadAngular;
    protected float velocidadAngularMax = 5f;
    protected float aceleracionAngularMax = 5f;
    protected int ancho;                                    // Solido:
    protected int alto;
    protected float orientacion = 0;                        // Orientable:
    protected SteeringBehavior<Vector2> steeringBehavior;   // SteerableAgent:
    protected SteeringAcceleration<Vector2> steeringOutput;
    protected Huellas huellas = new Huellas();              // Seguible:
    protected boolean encaramientoIndependiente = false;    // Steerable:
    protected boolean tagged = false;

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getPosition()                                  { return posicion; }
    @Override public float getX()                                           { return posicion.x; }
    @Override public float getY()                                           { return posicion.y; }
    @Override public int getCuadranteX()                                    { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getCuadranteY()                                    { return (int)(getY()/ (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoCuadranteX()                              { return ultimoCuadranteX; }
    @Override public int getUltimoCuadranteY()                              { return ultimoCuadranteY; }
    @Override public void setUltimoMapTile (int x, int y)                   { ultimoCuadranteX = x; ultimoCuadranteY = y; }

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

    // SOLIDO:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getAncho()                                         { return ancho; }
    @Override public int getAlto()                                          { return alto; }
    @Override public void setAncho(int ancho)                               { this.ancho = ancho; }
    @Override public void setAlto(int alto)                                 { this.alto = alto; }

    // ORIENTABLE: (los angulos mejor que estem siempre normalizados)
    //------------------------------------------------------------------------------------------------------------------

    @Override public float getOrientacion()                                 { return orientacion; }
    @Override public void setOrientacion(float radianes)                    { this.orientacion = radianes % MathUtils.PI2; }

    // STEERABLEAGENT:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior)
    {   this.steeringBehavior = steeringBehavior; }

    @Override public void setEncaramientoIndependiente (boolean b)
    {   this.encaramientoIndependiente = b; }

    // SEGUIBLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Iterator<Huella> getListaHuellasIterator()
    {   return this.huellas.iterator(); }

    @Override public void setTiempoDecayHuellas (float tiempoDecayHuellas)
    {   this.huellas.setTiempoDecayHuellas(tiempoDecayHuellas); }

    // STEERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getLinearVelocity()        { return getVelocidad(); }
    @Override public float getMaxLinearSpeed()          { return getVelocidadMax(); }
    @Override public float getMaxLinearAcceleration()   { return getAceleracionMax(); }
    @Override public float getAngularVelocity()         { return getVelocidadAngular(); }
    @Override public float getMaxAngularSpeed()         { return getVelocidadAngularMax(); }
    @Override public float getMaxAngularAcceleration()  { return getAceleracionAngularMax(); }
    @Override public float getOrientation()             { return getOrientacion(); }
    @Override public boolean isTagged()                 { return tagged; }
    @Override public float getBoundingRadius()          { return (ancho+alto)/4; }

    @Override public void setMaxLinearSpeed(float maxLinearSpeed)
    {   setVelocidadMax(maxLinearSpeed); }

    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {   setAceleracionMax(maxLinearAcceleration); }

    @Override public void setMaxAngularSpeed(float maxAngularSpeed)
    {   setVelocidadAngularMax(maxAngularSpeed); }

    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {   setAceleracionAngularMax(maxAngularAcceleration); }

    @Override public void setTagged(boolean tagged)
    {   this.tagged = tagged; }

    // OPERACIONES MATEMATICAS(Steerable):
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 newVector()
    {   return new Vector2(); }

    @Override public float vectorToAngle(Vector2 vector) //Radianes:
    {
        float angulo =(float)Math.atan2(vector.y, vector.x);
        return (angulo + MathUtils.PI2) % MathUtils.PI2;
    }

    @Override public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = (float)Math.cos(angle);
        outVector.y = (float)Math.sin(angle);
        return outVector;
    }

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public AbstractSteerableAgent()
    {   steeringOutput = new SteeringAcceleration(new Vector2()); }

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
            if ( this.getVelocidad().isZero(0.1f)) nuevaOrientacion = this.getOrientation();
            else nuevaOrientacion = this.vectorToAngle(this.getVelocidad());

            if (nuevaOrientacion != getOrientacion())
            {
                setVelocidadAngular((nuevaOrientacion - getOrientacion()) * delta);
                setOrientacion(nuevaOrientacion);
            }
        }
    }
}
