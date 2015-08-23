package Model.Classes.AI.Steering.SimpleBehaviors;

import Model.Settings;
import com.badlogic.gdx.ai.steer.Limiter;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;


public class SeekSombra extends SteeringBehavior<Vector2>
{
    protected Steerable<Vector2> target;
    protected Vector2 coordenadasTarget = new Vector2();
    protected RaycastCollisionDetector<Vector2> rayCastCollisionDetector;
    protected Ray<Vector2> ray;
    protected boolean ajustarCoordenadas = false;

    public SeekSombra (Steerable<Vector2> owner, Steerable<Vector2> target, RaycastCollisionDetector<Vector2> rayCastCollisionDetector)
    {
        super(owner);
        this.target = target;
        this.rayCastCollisionDetector = rayCastCollisionDetector;
        this.ray = new Ray<>(owner.getPosition(), target.getPosition());
        this.coordenadasTarget.set(target.getPosition());
    }

    @Override protected SteeringAcceleration<Vector2> calculateRealSteering (SteeringAcceleration<Vector2> steering)
    {
        float distancia = Vector2.len(coordenadasTarget.x - owner.getPosition().x, coordenadasTarget.y - owner.getPosition().y);

        //actualizar Rayo:
        ray.start = owner.getPosition();
        ray.end = target.getPosition();

        //Comprobar si colisiona, si no es asi, actualizar Waypoint:
        if (!rayCastCollisionDetector.collides(ray))
        {
            coordenadasTarget.set(target.getPosition());
            ajustarCoordenadas = true;
        }
        else if (ajustarCoordenadas)
        {
            coordenadasTarget.set(coordenadasTarget.x - coordenadasTarget.x % Settings.TILESIZE + Settings.TILESIZE/2,
                                  coordenadasTarget.y - coordenadasTarget.y % Settings.TILESIZE + Settings.TILESIZE/2);
            ajustarCoordenadas = false;
        }


        if (distancia <= 12)
            coordenadasTarget.set(target.getPosition());

        // Try to match the position of the character with the position of the target by calculating
        // the direction to the target and by moving toward it as fast as possible.
        steering.linear.set(coordenadasTarget).sub(owner.getPosition()).nor().scl(getActualLimiter().getMaxLinearAcceleration());

        // No angular acceleration
        steering.angular = 0;

        return steering;
    }

    public Steerable<Vector2> getTarget ()
    {   return target; }

    public SeekSombra setTarget (Steerable<Vector2> target)
    {   this.target = target; return this; }

    // STEERINGBEHAVIOR:
    //------------------------------------------------------------------------------------------------------------------

    @Override public SeekSombra setOwner (Steerable<Vector2> owner)
    {   this.owner = owner; return this; }

    @Override public SeekSombra setEnabled (boolean enabled)
    {   this.enabled = enabled; return this; }

    @Override public SeekSombra setLimiter (Limiter limiter)
    {   this.limiter = limiter; return this; }
}

