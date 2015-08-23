package Model.Classes.AI.Steering.SimpleBehaviors;

import Interfaces.EntidadesTipos.PCI;
import Model.AI.Steering.Huella;
import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class SeekPC extends SteeringBehavior<Vector2>
{
    protected PCI target;
    protected Vector2 coordenadasTarget = new Vector2();
    protected RaycastCollisionDetector<Vector2> rayCastCollisionDetector;
    protected Ray<Vector2> ray;



    public SeekPC (Steerable<Vector2> owner, PCI target, RaycastCollisionDetector<Vector2> rayCastCollisionDetector)
    {
        super(owner);
        this.target = target;
        this.rayCastCollisionDetector = rayCastCollisionDetector;
        this.ray = new Ray<>(new Vector2(0,0), new Vector2(0,0));
        this.coordenadasTarget.set(target.getPosition().x, target.getPosition().y);
    }

    @Override protected SteeringAcceleration<Vector2> calculateRealSteering (SteeringAcceleration<Vector2> steering)
    {
        //actualizar Rayo:
        ray.start.set(owner.getPosition().x, owner.getPosition().y);
        ray.end.set(target.getPosition().x, target.getPosition().y);

        //Intentar ir directamente hacia el player:
        if (!rayCastCollisionDetector.collides(ray))
            coordenadasTarget.set(target.getX(), target.getY());

        //En caso contrario seguir la huella mas reciente con visibilidad:
        else
        {
            Iterator<Huella> iterator = target.getListaHuellas().iterator();
            while (iterator.hasNext())
            {
                Huella huella = iterator.next();
                ray.end.set(huella.punto.x, huella.punto.y);

                if (!rayCastCollisionDetector.collides(ray))
                {   coordenadasTarget.set(huella.punto.x, huella.punto.y); break; }
            }
        }

        // Try to match the position of the character with the position of the target by calculating
        // the direction to the target and by moving toward it as fast as possible.
        steering.linear.set(coordenadasTarget).sub(owner.getPosition()).nor().scl(getActualLimiter().getMaxLinearAcceleration());

        // No angular acceleration
        steering.angular = 0;

        return steering;
    }

    public Steerable<Vector2> getTarget ()
    {   return target; }

    public SeekPC setTarget (PCI target)
    {   this.target = target; return this; }

    // STEERINGBEHAVIOR:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SeekPC setOwner (Steerable<Vector2> owner)
    {   this.owner = owner; return this; }

    @Override public SeekPC setEnabled (boolean enabled)
    {   this.enabled = enabled; return this; }

    @Override public SeekPC setLimiter (Limiter limiter)
    {   this.limiter = limiter; return this; }
}
