package Model.Classes.AI.Steering;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.math.Vector2;

public class SteeringFactory
{
    public enum Steering1
    {
        LOOK_WHERE_YOU_ARE_GOING()
        {   // gives the owner angular acceleration to make it face in the direction it is moving. In this way the owner
            // changes facing gradually, which can look more natural, especially for aerial vehicles such as helicopters
            // or for human characters that can move sideways
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin)
            {   return new LookWhereYouAreGoing<>(origin); }
        },

        WANDER()
        {   // this behavior is designed to produce a steering acceleration that will give the impression of a random walk
            // through the agent's environment. .
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin)
            {   return new Wander(origin); }
        },

        FLOW_FIELD_FOLLOWING()
        {   // The FollowFlowField behavior produces a linear acceleration that tries to align the motion of the owner with
            // the local tangent of a flow field. The flow field defines a mapping from a location in space to a flow vector.
            @Override
            public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin)
            {   return new FollowFlowField(origin); }
        },

        BLEEDED_STEERING()
        {   // is a combination behavior that simply sums up all the active behaviors, applies their weights, and truncates
            // the result before returning. There are no constraints on the blending weights; they don't have to sum to one,
            // for example, and rarely do. Don't think of BlendedSteering as a weighted mean, because it's not.
            // With BlendedSteering you can combine multiple behaviors to get a more complex behavior. It can work fine, but
            // the trade-off is that it comes with a few problems:
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin)
            {   return new BlendedSteering<>(origin); }
        },

        PRIORITY_STEERING()
        {   // iterates through the active behaviors and returns the first non zero steering. It makes sense since certain
            // steering behaviors only request an acceleration in particular conditions. Unlike Seek or Evade, which always
            // produce an acceleration, RaycastObstacleAvoidance, CollisionAvoidance, Separation, Hide and Arrive will
            // suggest no acceleration in many cases. But when these behaviors do suggest an acceleration, it is unwise to
            // ignore it. An obstacle avoidance behavior, for example, should be honored immediately to avoid the crash.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin)
            {   return new PrioritySteering<Vector2>(origin); }
        };

        public abstract SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin);
    }

    public enum Steering2
    {
        SEEK()
        {   // moves the owner towards the target position. Given a target, this behavior calculates the linear
            // steering acceleration which will direct the agent towards the target.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Seek<Vector2>(origin, target); }
        },

        FLEE()
        {   // does the opposite of Seek. It produces a linear steering force that moves the agent away from a target position
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Flee<Vector2>(origin, target); }
        },

        ARRIVE()
        {   // behavior moves the agent towards a target position. It is similar to Seek but it attempts to arrive at the
            // target position with a zero velocity. The arrival tolerance lets the owner get near enough to the target
            // without letting small errors keep it in motion. The deceleration radius, usually much larger than the previous
            // one, specifies when the incoming character will begin to slow down
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Arrive<Vector2>(origin, target); }
        },

        REACH_ORIENTATION()
        {   // tries to align the owner to the target. It pays no attention to the position or velocity of the owner or target.
            // This steering behavior does not produce any linear acceleration; it only responds by turning.
            // behaves in a somewhat similar way to Arrive since it tries to reach the target orientation and tries to have
            // zero rotation when it gets there. Like arrive, it uses two radii: deceleration radius for slowing down and
            // align tolerance to make orientations near the target acceptable without letting small errors keep the owner swinging.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new ReachOrientation<Vector2>(origin, target); }
        },

        PURSUE()
        {   // produces a force that steers the agent towards the the targe). Actually it predicts where an agent will
            // be in time T and seeks towards that point to intercept it, assuming the target will continue moving with the
            // same velocity it currently has.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Pursue<Vector2>(origin, target); }
        },

        EVADE()
        {   // is almost the same as Pursue except that the agent flees from the estimated future position of the pursuer
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Evade<Vector2>(origin, target); }
        },

        FACE()
        {   // behavior makes the owner look at its target. It delegates to the ReachOrientation behavior to perform the
            // rotation but calculates the target orientation first based on target and owner position.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new Face<Vector2>(origin, target); }
        },

        MATCH_VELOCITY()
        {   // So far we have encountered some behaviors that try to match position with a target, such as Seek and Flee.
            // This behavior does the same with velocity
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target)
            {   return new MatchVelocity<Vector2>(origin, target); }
        };

        public abstract SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin, Steerable<Vector2> target);
    }

    public enum Steering3
    {
        INTERPOSE()
        {   // produces a steering force that moves the owner to a point along the imaginary line connecting two other
            // agents. A bodyguard taking a bullet for his employer or a soccer player intercepting a pass are examples
            // of this type of behavior.
            @Override public SteeringBehavior<Vector2> nuevo(Steerable<Vector2> Mob,
                                                             Steerable<Vector2>target1, Steerable<Vector2> target2)
            {   return new Interpose<Vector2>(Mob, target1, target2); }
        };

        public abstract SteeringBehavior<Vector2> nuevo(Steerable<Vector2> origin,
                                                        Steerable<Vector2> target1, Steerable<Vector2> target2);
    }

}