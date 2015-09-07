package Model.AI.Behaviors.SteeringFactory;

import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.Misc.GameState.MundoI;
import Model.AI.Behaviors.FixedBehaviors.LookWhereFixed;
import Model.AI.Behaviors.SimpleBehaviors.SeekHuellas.RayTargetDoble;
import Model.AI.Behaviors.SimpleBehaviors.SeekHuellas.SeekHuellas;
import Model.AI.Behaviors.SimpleBehaviors.SeparationMuros.RayDetectorMuros;
import Model.AI.Behaviors.SimpleBehaviors.SeparationMuros.RayEnvolvente;
import Model.AI.Behaviors.SimpleBehaviors.SeparationMuros.SeparationMuros;
import Model.Settings;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.math.Vector2;

public enum SteeringCompuestoFactory
{
    WALL_PURSUE_LOOK
    {
        @Override public BlendedSteering<Vector2> nuevo(SteerableAgentAutonomoI owner, SteerableAgentI target, MundoI mundo)
        {
            //SeekHuellas
            RayDetectorMuros rayDetectorMuros = new RayDetectorMuros(mundo.getMapa());
            RayTargetDoble rayToTarget = new RayTargetDoble(owner, 10);
            SeekHuellas seekHuellas = new SeekHuellas(owner, target, rayDetectorMuros, rayToTarget);

            //LookWhereImGoing:
            LookWhereFixed<Vector2> lookWhereYouAreGoing = new LookWhereFixed<>(owner);
            lookWhereYouAreGoing.setDecelerationRadius(1f);
            lookWhereYouAreGoing.setAlignTolerance(0.1f);

            //SeparationMuros:
            RayEnvolvente rayo6 = new RayEnvolvente(owner);
            SeparationMuros<Vector2> wallAvoidance = new SeparationMuros<>(owner, rayDetectorMuros, rayo6, Settings.FIXED_TimeStep);

            //TOTAL STEERING:
            BlendedSteering<Vector2> advancedSteering = new BlendedSteering<>(owner);
            advancedSteering.add(wallAvoidance,1f);
            advancedSteering.add(seekHuellas,1f);
            advancedSteering.add(lookWhereYouAreGoing, 1f);

            owner.setEncaramientoIndependiente(true);
            owner.setVelocidadAngularMax(6f);
            owner.setAceleracionAngularMax(12f);

            return advancedSteering;
        }
    };

    public abstract BlendedSteering<Vector2> nuevo(SteerableAgentAutonomoI owner, SteerableAgentI target, MundoI mundo);
}
