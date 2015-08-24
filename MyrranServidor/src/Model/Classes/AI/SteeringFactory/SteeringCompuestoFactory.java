package Model.Classes.AI.SteeringFactory;

import Interfaces.EntidadesPropiedades.SteerableAgent;
import Interfaces.GameState.MundoI;
import Model.Classes.AI.FixedBehaviors.LookWhereFixed;
import Model.Classes.AI.SimpleBehaviors.SeparateMuros.RayDetectorMuros;
import Model.Classes.AI.SimpleBehaviors.SeekHuellas.RayTargetSingle;
import Model.Classes.AI.SimpleBehaviors.SeekHuellas.SeekHuellas;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
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
            //SeekHuellas
            RayDetectorMuros rayDetectorMuros = new RayDetectorMuros(mundo.getMapa());
            RayTargetSingle rayToTarget = new RayTargetSingle(owner, 10);
            SeekHuellas seekHuellas = new SeekHuellas(owner, target, rayDetectorMuros, rayToTarget);

            //LookWhereImGoing:
            LookWhereFixed<Vector2> lookWhereYouAreGoing = new LookWhereFixed<>(owner);
            lookWhereYouAreGoing.setDecelerationRadius(1f);
            lookWhereYouAreGoing.setAlignTolerance(0.1f);

            //RayCastWallAvoidance:p
            CentralRayWithWhiskersConfiguration<Vector2> rayConfig = new CentralRayWithWhiskersConfiguration<>(owner, 24f, 24f, 30 * MathUtils.degreesToRadians);
            RaycastObstacleAvoidance wallAvoidance = new RaycastObstacleAvoidance(owner, rayConfig, rayDetectorMuros, 36);

            //Prioridad:
            PrioritySteering<Vector2> evadeWallsAndSeekHuellas = new PrioritySteering<>(owner);
            evadeWallsAndSeekHuellas.add(wallAvoidance);
            evadeWallsAndSeekHuellas.add(seekHuellas);

            //TOTAL STEERING:
            BlendedSteering<Vector2> advancedSteering = new BlendedSteering<>(owner);
            advancedSteering.add(evadeWallsAndSeekHuellas, 1f);
            advancedSteering.add(lookWhereYouAreGoing, 1f);

            owner.setEncaramientoIndependiente(true);
            owner.setVelocidadAngularMax(3f);
            owner.setAceleracionAngularMax(3f);

            return advancedSteering;
        }
    };

    public abstract BlendedSteering nuevo(SteerableAgent owner, SteerableAgent target, MundoI mundo);
}
