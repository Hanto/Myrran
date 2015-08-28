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
    private float aceleracionMaxima;
    private boolean colisionadoConMuro = false;
    private float distanciaColision;

    public SeparationMuros(Steerable<T> owner, RaycastCollisionDetector<Vector2> rayDetectorMuros,
                           RayConfiguration<T> rayosConfig, float timeStep)
    {
        super(owner);
        this.detectorMuros = rayDetectorMuros;
        this.rayosConfig = rayosConfig;
        this.toMuro = owner.newVector();
        this.colision = new Collision<>(owner.newVector(), owner.newVector());

        this.aceleracionMaxima =  (2.5f*2.5f)*owner.getMaxLinearAcceleration(); //(float)Math.pow(owner.getMaxLinearSpeed(),2) / 2 + owner.getMaxLinearAcceleration();
        this.distanciaColision = owner.getMaxLinearSpeed() / (1f / timeStep);
    }

    @Override protected SteeringAcceleration<T> calculateRealSteering(SteeringAcceleration<T> steering)
    {
        float distancia;
        float distanciaMenor = 1000;
        T vector = owner.newVector();

        Ray<T>[] rayos = rayosConfig.updateRays();

        for (Ray rayo : rayos)
        {
            if (detectorMuros.findCollision(colision, rayo))
            {
                toMuro.set(owner.getPosition()).sub(colision.point);

                distancia = toMuro.len();

                if (distancia < distanciaMenor ) distanciaMenor = distancia;
                    vector.mulAdd(toMuro, 1/distancia);
            }
        }

        distanciaMenor = distanciaMenor - owner.getBoundingRadius();

        if (distanciaMenor < distanciaColision && !colisionadoConMuro)
        {   owner.getLinearVelocity().setZero(); colisionadoConMuro = true; }

        else if (colisionadoConMuro && distanciaMenor >= distanciaColision)
        {   colisionadoConMuro = false; }

        if (distanciaMenor < 1000 - owner.getBoundingRadius())
            steering.linear.mulAdd(vector, aceleracionMaxima/((distanciaMenor) * (distanciaMenor)));

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
