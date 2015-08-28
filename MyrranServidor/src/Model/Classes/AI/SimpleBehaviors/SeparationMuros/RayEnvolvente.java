package Model.Classes.AI.SimpleBehaviors.SeparationMuros;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.utils.rays.RayConfigurationBase;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;

public class RayEnvolvente<T extends Vector<T>> extends RayConfigurationBase<T>
{
    private float longitud;
    private final float angulo = 60*MathUtils.degreesToRadians;
    private T vector;

    public RayEnvolvente(Steerable<T> owner)
    {
        super(owner, 6);
        this.longitud = owner.getBoundingRadius() + owner.getBoundingRadius();
        this.vector = owner.newVector();
    }


    @Override public Ray<T>[] updateRays()
    {
        T ownerPosition = owner.getPosition();
        T ownerVelocity = owner.getLinearVelocity();

        float anguloVeloticy = owner.vectorToAngle(ownerVelocity);

        //Rayo Central Delantero:
        rays[0].start.set(ownerPosition);
        rays[3].start.set(ownerPosition);
        vector.set(ownerVelocity).nor();

        rays[0].end.set(vector).scl(longitud).add(ownerPosition);
        rays[3].end.set(vector).scl(-longitud).add(ownerPosition);

        //Rayo Izquierdo Delantero:
        rays[1].start.set(ownerPosition);
        rays[4].start.set(ownerPosition);
        owner.angleToVector(vector, anguloVeloticy - angulo);

        rays[1].end.set(vector).scl(longitud).add(ownerPosition);
        rays[4].end.set(vector).scl(-longitud).add(ownerPosition);

        //Rayo Derecho Delantero:
        rays[2].start.set(ownerPosition);
        rays[5].start.set(ownerPosition);
        owner.angleToVector(vector, anguloVeloticy + angulo);

        rays[2].end.set(vector).scl(longitud).add(ownerPosition);
        rays[5].end.set(vector).scl(-longitud).add(ownerPosition);/*

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
        owner.angleToVector(rays[5].end, anguloVeloticy + angulo).scl(-longitud).add(ownerPosition);*/

        return rays;
    }
}
