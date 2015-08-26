package Model.Classes.AI.SimpleBehaviors.SeparationMuros;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;

public class Ray6Direcciones<T extends Vector<T>> extends RayConfigurationBase<T>
{
    private float longitud;
    private final float angulo = 60*MathUtils.degreesToRadians;

    public Ray6Direcciones(Steerable<T> owner)
    {
        super(owner, 6);
        this.longitud = owner.getBoundingRadius() + owner.getBoundingRadius();
    }


    @Override public Ray<T>[] updateRays()
    {
        T ownerPosition = owner.getPosition();
        T ownerVelocity = owner.getLinearVelocity();

        float anguloVeloticy = owner.vectorToAngle(ownerVelocity);

        //Rayo Central Delantero:
        rays[0].start.set(ownerPosition);
        rays[0].end.set(ownerVelocity).nor().scl(longitud).add(ownerPosition);

        //Rayo Izquierdo Delantero:
        rays[1].start.set(ownerPosition);
        owner.angleToVector(rays[1].end, anguloVeloticy - angulo).scl(longitud).add(ownerPosition);

        //Rayo Derecho Delantero:
        rays[2].start.set(ownerPosition);
        owner.angleToVector(rays[2].end, anguloVeloticy + angulo).scl(longitud).add(ownerPosition);

        //Rayo Central Trasero:
        rays[3].start.set(ownerPosition);
        rays[3].end.set(ownerVelocity).nor().scl(-longitud).add(ownerPosition);

        //Rayo Izquiero Trasero:
        rays[4].start.set(ownerPosition);
        owner.angleToVector(rays[4].end, anguloVeloticy - angulo).scl(-longitud).add(ownerPosition);

        //Rayo Derecho Trasero:
        rays[5].start.set(ownerPosition);
        owner.angleToVector(rays[5].end, anguloVeloticy + angulo).scl(-longitud).add(ownerPosition);

        return rays;
    }
}
