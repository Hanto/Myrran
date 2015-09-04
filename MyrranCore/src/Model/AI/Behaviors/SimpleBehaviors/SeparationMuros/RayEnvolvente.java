package Model.AI.Behaviors.SimpleBehaviors.SeparationMuros;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.utils.RayConfiguration;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RayEnvolvente implements RayConfiguration<Vector2>
{
    protected Steerable<Vector2> owner;
    protected Ray<Vector2>[] rays;

    private float longitud;
    private final float angulo = 60* MathUtils.degreesToRadians;
    private Vector2 vector;

    public RayEnvolvente(Steerable owner)
    {
        this.longitud = owner.getBoundingRadius() + owner.getBoundingRadius();
        this.vector = new Vector2();
        this.owner = owner;
        this.rays = new Ray[6];

        rays[0] = new Ray(new Vector2(), new Vector2());
        for (int i = 1; i < 6; i++)
            this.rays[i] = new Ray(rays[0].start, new Vector2());
    }

    @Override public Ray<Vector2>[] updateRays()
    {
        float anguloVeloticy = owner.vectorToAngle(owner.getLinearVelocity());

        //Rayo Central Delantero:
        rays[0].start.set(owner.getPosition());
        vector.set(owner.getLinearVelocity()).nor();

        rays[0].end.set(vector).scl(longitud).add(owner.getPosition());
        rays[3].end.set(vector).scl(-longitud).add(owner.getPosition());

        //Rayo Izquierdo Delantero:
        owner.angleToVector(vector, anguloVeloticy - angulo);

        rays[1].end.set(vector).scl(longitud).add(owner.getPosition());
        rays[4].end.set(vector).scl(-longitud).add(owner.getPosition());

        //Rayo Derecho Delantero:
        owner.angleToVector(vector, anguloVeloticy + angulo);

        rays[2].end.set(vector).scl(longitud).add(owner.getPosition());
        rays[5].end.set(vector).scl(-longitud).add(owner.getPosition());

        return rays;
    }

    public Steerable<Vector2> getOwner ()
    {   return owner; }

    public void setOwner (Steerable<Vector2> owner)
    {   this.owner = owner; }

    public Ray<Vector2>[] getRays ()
    {   return rays; }
}
