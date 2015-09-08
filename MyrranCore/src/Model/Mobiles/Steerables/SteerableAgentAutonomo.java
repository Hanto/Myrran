package Model.Mobiles.Steerables;// Created by Hanto on 03/09/2015.

import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

public abstract class SteerableAgentAutonomo extends SteerableAgent implements SteerableAgentAutonomoI
{
    protected SteeringBehavior<Vector2> steeringBehavior;   // SteerableAgent:
    protected SteeringAcceleration<Vector2> steeringOutput;
    protected boolean encaramientoIndependiente = false;

    public float nuevaOrientacion;
    public float nuevaPosX;
    public float nuevaPosY;

    public SteerableAgentAutonomo()
    {   steeringOutput = new SteeringAcceleration<>(new Vector2()); }

    public SteeringBehavior<Vector2> getSteeringBehavior()
    {   return steeringBehavior; }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior)
    {   this.steeringBehavior = steeringBehavior; }

    public void setEncaramientoIndependiente (boolean b)
    {   this.encaramientoIndependiente = b; }

    public void calcularSteering(float delta)
    {
        steeringBehavior.calculateSteering(steeringOutput);
        aplicarSteering(steeringOutput, delta);
    }

    private void aplicarSteering(SteeringAcceleration<Vector2> steering, float delta)
    {
        //Posicion:
        nuevaPosX = posicion.x + getVelocidad().x * delta;
        nuevaPosY = posicion.y + getVelocidad().y * delta;
        setPosition(nuevaPosX, nuevaPosY);

        //Velocidad:
        this.getVelocidad().mulAdd(steering.linear, delta).limit(this.getMaxLinearSpeed());

        //Orientacion:
        if (encaramientoIndependiente)
        {
            nuevaOrientacion = getOrientacion() + getVelocidadAngular() * delta;
            setVelocidadAngular(getVelocidadAngular() + steering.angular * delta);
        }
        else
        {
            if ( this.getVelocidad().isZero(0.1f))  nuevaOrientacion = this.getOrientation();
            else                                    nuevaOrientacion = this.vectorToAngle(this.getVelocidad());

            if (nuevaOrientacion != getOrientacion())
            {
                setVelocidadAngular((nuevaOrientacion - getOrientacion()) * delta);
                setOrientacion(nuevaOrientacion);
            }
        }
    }
}
