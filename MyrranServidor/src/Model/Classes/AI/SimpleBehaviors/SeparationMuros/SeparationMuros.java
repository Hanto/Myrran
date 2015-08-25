package Model.Classes.AI.SimpleBehaviors.SeparationMuros;


import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.utils.RayConfiguration;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class SeparationMuros<T extends Vector<T>>  extends SteeringBehavior<T>
{
    private RaycastCollisionDetector detectorMuros;
    private RayConfiguration<T> rayosConfig;
    private Collision<T> colision;

    private T toMuro;
    private float distanciaFrenado;

    public SeparationMuros(Steerable<T> owner, RaycastCollisionDetector<Vector2> rayDetectorMuros,
                           RayConfiguration<T> rayosConfig)
    {
        super(owner);
        this.detectorMuros = rayDetectorMuros;
        this.rayosConfig = rayosConfig;
        this.toMuro = owner.newVector();
        this.colision = new Collision<>(owner.newVector(), owner.newVector());

        this.distanciaFrenado = owner.getBoundingRadius() +
                                owner.getMaxLinearSpeed() * owner.getMaxLinearSpeed() / (2 * owner.getMaxLinearAcceleration())
                                + 2;
    }

    @Override protected SteeringAcceleration<T> calculateRealSteering(SteeringAcceleration<T> steering)
    {
        float distancia;

        Ray<T>[] rayos = rayosConfig.updateRays();

        for (Ray rayo : rayos)
        {
            if (detectorMuros.findCollision(colision, rayo))
            {
                toMuro.set(owner.getPosition()).sub(colision.point);

                distancia = toMuro.len();

                if (distancia <= distanciaFrenado)
                    steering.linear.mulAdd(toMuro, owner.getMaxLinearAcceleration()/distancia);
                    //Se calcula el modulo y se aplica la fuerza a la vez, es mas optimo que normalizar el vector
                    //puesto que la logintud del vector ya la tenemos calculada
            }
        }

        steering.angular = 0;

        return steering;
    }

    // STEERINGBEHAVIOR:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SeparationMuros<T> setOwner (Steerable<T> owner)
    {   this.owner = owner; return this; }

    @Override public SeparationMuros setEnabled (boolean enabled)
    {   this.enabled = enabled; return this; }

    @Override public SeparationMuros setLimiter (Limiter limiter)
    {   this.limiter = limiter; return this; }
}
