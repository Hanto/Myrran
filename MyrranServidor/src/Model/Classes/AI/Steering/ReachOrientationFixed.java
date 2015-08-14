package Model.Classes.AI.Steering;// Created by Hanto on 14/08/2015.

import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.ReachOrientation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;

public class ReachOrientationFixed<T extends Vector<T>> extends ReachOrientation<T>
{
    public ReachOrientationFixed(Steerable<T> owner)
    {   super(owner); }

    public ReachOrientationFixed(Steerable<T> owner, Steerable<T> target)
    {   super(owner, target); }

    protected SteeringAcceleration<T> reachOrientation (SteeringAcceleration<T> steering, float targetOrientation) {
        // Get the rotation direction to the target wrapped to the range [-PI, PI]
        float rotation = (targetOrientation - owner.getOrientation()) % MathUtils.PI2;

        //rotation = Math.min(targetOrientation - owner.getOrientation(), owner.getOrientation() - targetOrientation);
        //rotation = rotation % MathUtils.PI2;

        System.out.println(rotation);

        if (rotation > MathUtils.PI) rotation -= MathUtils.PI2;

        // Absolute rotation
        float rotationSize = rotation < 0f ? -rotation : rotation;

        // Check if we are there, return no steering
        if (rotationSize < alignTolerance) return steering.setZero();

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
