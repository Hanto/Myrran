package Model.Classes.AI.Steering;

import Interfaces.EntidadesPropiedades.SteerableAgent;
import Interfaces.GameState.MundoI;
import Model.Classes.AI.Steering.Fixed.LookWhereFixed;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.ai.steer.behaviors.RaycastObstacleAvoidance;
import com.badlogic.gdx.ai.steer.utils.rays.CentralRayWithWhiskersConfiguration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public enum SteeringCompuestoFactory
{
    WALL_PURSUE_LOOK
    {
        @Override public BlendedSteering nuevo(SteerableAgent owner, SteerableAgent target, MundoI mundo)
        {
            //Pursue:
            Pursue<Vector2> pursue = new Pursue<>(owner, target);

            //LookWhereImGoing:
            LookWhereFixed<Vector2> lookWhereYouAreGoing = new LookWhereFixed<>(owner);
            lookWhereYouAreGoing.setDecelerationRadius(1f);
            lookWhereYouAreGoing.setAlignTolerance(0.1f);

            //RayCastWallAvoidance:
            CentralRayWithWhiskersConfiguration<Vector2> rayConfig = new CentralRayWithWhiskersConfiguration<>(owner, 48f, 24f, 20 * MathUtils.degreesToRadians);
            RayCastMuros rayCastMuros = new RayCastMuros(mundo);
            RaycastObstacleAvoidance wallAvoidance = new RaycastObstacleAvoidance(owner, rayConfig, rayCastMuros, 36);

            PrioritySteering<Vector2> pursueAndEvadeWalls = new PrioritySteering<>(owner);
            pursueAndEvadeWalls.add(wallAvoidance);
            pursueAndEvadeWalls.add(pursue);

            BlendedSteering<Vector2> advancedSteering = new BlendedSteering<>(owner);
            advancedSteering.add(pursueAndEvadeWalls, 1f);
            advancedSteering.add(lookWhereYouAreGoing, 1f);

            owner.setEncaramientoIndependiente(true);
            owner.setVelocidadAngularMax(3f);
            owner.setAceleracionAngularMax(3f);

            return advancedSteering;
        }
    };

    public abstract BlendedSteering nuevo(SteerableAgent owner, SteerableAgent target, MundoI mundo);
}