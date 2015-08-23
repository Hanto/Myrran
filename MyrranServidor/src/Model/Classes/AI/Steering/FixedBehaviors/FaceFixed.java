package Model.Classes.AI.Steering.FixedBehaviors;// Created by Hanto on 14/08/2015.

import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Face;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;

public class FaceFixed<T extends Vector<T>> extends Face<T>
{
    public FaceFixed(Steerable<T> owner)
    {   super(owner); }

    public FaceFixed (Steerable<T> owner, Steerable<T> target)
    {   super(owner, target); }

    protected SteeringAcceleration<T> face (SteeringAcceleration<T> steering, T targetPosition) {
        // Get the direction to target
        T toTarget = steering.linear.set(targetPosition).sub(owner.getPosition());

        // Check for a zero direction, and return no steering if so
        if (toTarget.isZero(MathUtils.FLOAT_ROUNDING_ERROR)) return steering.setZero();

        // Calculate the orientation to face the target
        float orientation = owner.vectorToAngle(toTarget);

        // Delegate to ReachOrientation
        return reachOrientation(steering, orientation);
    }

    protected SteeringAcceleration<T> reachOrientation (SteeringAcceleration<T> steering, float targetOrientation) {
        // Get the rotation direction to the target wrapped to the range [-PI, PI]

        //ESTA FUCKING LINEA CORRIGE EL BUG DE TELEPORTACION AL PASAR POR EL EJE 0:
        //SIEMPRE Y CUANDO LAS ORIENTACIONES ESTEN NORMALIZADAS. RANGO 0-2PI.
        if (targetOrientation < owner.getOrientation()) targetOrientation = targetOrientation + MathUtils.PI2;

        float rotation = (targetOrientation - owner.getOrientation()) % MathUtils.PI2;
        if (rotation > MathUtils.PI) rotation -= MathUtils.PI2;

        // Absolute rotation
        float rotationSize = rotation < 0f ? -rotation : rotation;

        //AQUI TAMBIEN HABIA UN BUG, SI LA ALIGNTOLERANCE ERA 0, SE PRODUCIA UNA DIVISION 0/0 AL CALCULAR EL
        //STEERING ANGULAR CUANDO ESTABAN ALINEADOS
        // Check if we are there, return no steering
        if (rotationSize <= alignTolerance) return steering.setZero();

        Limiter actualLimiter = getActualLimiter();

        // Use maximum rotation
        float targetRotation = actualLimiter.getMaxAngularSpeed();

        // If we are inside the slow down radius, then calculate a scaled rotation
        if (rotationSize <= decelerationRadius) targetRotation *= rotationSize / decelerationRadius;

        // The final target rotation combines
        // speed (already in the variable) and direction
        targetRotation *= rotation / rotationSize;

        // Acceleration tries to get to the target rotation
        steering.angular = (targetRotation - owner.getAngularVelocity()) / timeToTarget;

        // Check if the absolute acceleration is too great
        float angularAcceleration = steering.angular < 0f ? -steering.angular : steering.angular;
        if (angularAcceleration > actualLimiter.getMaxAngularAcceleration())
            steering.angular *= actualLimiter.getMaxAngularAcceleration() / angularAcceleration;

        // No linear acceleration
        steering.linear.setZero();

        // Output the steering
        return steering;
    }
}
