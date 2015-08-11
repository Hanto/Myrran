package Model.Classes.Mobiles.Mob;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Actualizable;
import Interfaces.EntidadesPropiedades.IDentificable;
import Interfaces.GameState.MundoI;
import Interfaces.Model.AbstractModel;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Mob extends AbstractModel implements Steerable<Vector2>, Actualizable, IDentificable,
        Disposable
{
    protected int iD;

    protected Vector2 posicion;
    protected Vector2 velocidadLineal;
    protected float velocidadAngular;
    protected float velocidadLinealMax;
    protected float aceleracionLinealMaxima;
    protected float velocidadAngularMax;
    protected float aceleracionAngularMaxima;

    protected boolean encaramientoIndependiente = true;
    protected boolean tagged;
    protected float orientacion = 0;

    protected SteeringBehavior<Vector2> steeringBehavior;
    protected SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());


    public Mob()
    { }

    public void dispose()
    { }


    // STEERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getPosition()              { return posicion; }
    @Override public Vector2 getLinearVelocity()        { return velocidadLineal; }
    @Override public float getAngularVelocity()         { return velocidadAngular; }

    @Override public float getMaxLinearSpeed()          { return velocidadLinealMax; }
    @Override public float getMaxLinearAcceleration()   { return aceleracionLinealMaxima; }
    @Override public float getMaxAngularSpeed()         { return velocidadAngularMax; }
    @Override public float getMaxAngularAcceleration()  { return aceleracionAngularMaxima; }

    @Override public void setMaxLinearSpeed(float maxLinearSpeed)
    {   velocidadLinealMax = maxLinearSpeed; }

    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {   aceleracionLinealMaxima = maxLinearAcceleration; }

    @Override public void setMaxAngularSpeed(float maxAngularSpeed)
    {   velocidadAngularMax = maxAngularSpeed; }

    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {   aceleracionAngularMaxima = maxAngularAcceleration; }

    // Tipo de Steering Behavior:
    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior)
    {   this.steeringBehavior = steeringBehavior; }


    @Override public boolean isTagged()
    {   return tagged; }

    @Override public void setTagged(boolean tagged)
    {   this.tagged = tagged; }

    @Override public float getBoundingRadius()
    {   return 0; }

    @Override public float getOrientation()
    {   return orientacion; }

    //Operaciones matematicas:
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






    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                                        { return iD; }


    // ACTUALIZABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizar(float delta, MundoI mundo)
    {
        if (steeringBehavior != null)
        {
            steeringBehavior.calculateSteering(steeringOutput);
            applySteering(steeringOutput, delta);
        }
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float delta)
    {
        this.posicion.mulAdd(velocidadLineal, delta);
        this.velocidadLineal.mulAdd(steering.linear, delta).limit(this.getMaxLinearSpeed());

        if (encaramientoIndependiente)
        {
            this.orientacion += velocidadAngular * delta;
            this.velocidadAngular += steering.angular * delta;
        }
        else
        {
            float nuevaOrientacion = calcularOrientacion(this);
            if (nuevaOrientacion != orientacion)
            {
                this.velocidadAngular = (nuevaOrientacion - orientacion) * delta;
                this.orientacion = nuevaOrientacion;
            }
        }
    }

    private float calcularOrientacion(Steerable<Vector2> character)
    {
        if (character.getLinearVelocity().isZero(0.001f)) return character.getOrientation();
        else return character.vectorToAngle(character.getLinearVelocity());
    }

}
