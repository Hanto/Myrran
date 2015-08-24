package Model.Classes.AI.SimpleBehaviors.SeekHuellas;

import Interfaces.EntidadesPropiedades.SteerableAgent;
import Model.AI.Steering.Huella;
import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class SeekHuellas extends SteeringBehavior<Vector2>
{
    protected SteerableAgent target;
    protected Vector2 coordenadasTarget = new Vector2();
    protected RaycastCollisionDetector<Vector2> rayDetectorMuros;
    protected RayTargetConfigurationI rayTarget;
    //protected Ray<Vector2> ray;

    public SeekHuellas(Steerable<Vector2> owner, SteerableAgent target,
                       RaycastCollisionDetector<Vector2> rayDetectorMuros,
                       RayTargetConfigurationI rayTargetConfiguration)
    {
        super(owner);
        this.target = target;
        this.rayDetectorMuros = rayDetectorMuros;
        this.rayTarget = rayTargetConfiguration;
        //this.ray = new Ray<>(new Vector2(0,0), new Vector2(0,0));
        this.coordenadasTarget.set(target.getPosition().x, target.getPosition().y);
    }

    @Override protected SteeringAcceleration<Vector2> calculateRealSteering (SteeringAcceleration<Vector2> steering)
    {
        //Intentar ir directamente hacia el player:
        if (!collides(target.getX(), target.getY()))
        {   coordenadasTarget.set(target.getX(), target.getY()); return calcularSteering(steering); }

        //En caso contrario seguir la huella mas reciente con visibilidad:
        Iterator<Huella> iterator = target.getListaHuellasIterator();
        while (iterator.hasNext())
        {
            Huella huella = iterator.next();

            if (!collides(huella.x, huella.y))
            {   coordenadasTarget.set(huella.x, huella.y); return calcularSteering(steering); }
        }

        //Si no hay linea de vision con huellas ni player, pararse:
        return steering.setZero();
    }

    private boolean collides(float x, float y)
    {
        Iterator<Ray<Vector2>> ray = rayTarget.updateTarget(x, y);

        while (ray.hasNext())
        {   if (rayDetectorMuros.collides(ray.next())) return true; }
        return false;
    }

    private SteeringAcceleration<Vector2> calcularSteering (SteeringAcceleration<Vector2> steering)
    {
        // Try to match the position of the character with the position of the target by calculating
        // the direction to the target and by moving toward it as fast as possible.
        steering.linear.set(coordenadasTarget).sub(owner.getPosition()).nor().scl(getActualLimiter().getMaxLinearAcceleration());

        // No angular acceleration
        steering.angular = 0;

        return steering;
    }


    public Steerable<Vector2> getTarget ()
    {   return target; }

    public SeekHuellas setTarget (SteerableAgent target)
    {   this.target = target; return this; }

    // STEERINGBEHAVIOR:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SeekHuellas setOwner (Steerable<Vector2> owner)
    {   this.owner = owner; return this; }

    @Override public SeekHuellas setEnabled (boolean enabled)
    {   this.enabled = enabled; return this; }

    @Override public SeekHuellas setLimiter (Limiter limiter)
    {   this.limiter = limiter; return this; }
}
