package Model.Classes.AI.SteeringFactory;

import Interfaces.EntidadesPropiedades.SteerableAgent;
import Interfaces.GameState.MundoI;
import Model.Classes.AI.FixedBehaviors.LookWhereFixed;
import Model.Classes.AI.SimpleBehaviors.SeekHuellas.RayTargetSingle;
import Model.Classes.AI.SimpleBehaviors.SeekHuellas.SeekHuellas;
import Model.Classes.AI.SimpleBehaviors.SeparationMuros.Ray6Direcciones;
import Model.Classes.AI.SimpleBehaviors.SeparationMuros.RayDetectorMuros;
import Model.Classes.AI.SimpleBehaviors.SeparationMuros.SeparationMuros;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
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

            //SeparationMuros:
            Ray6Direcciones<Vector2> rayo6 = new Ray6Direcciones<>(owner);
            SeparationMuros<Vector2> wallAvoidance = new SeparationMuros<>(owner, rayDetectorMuros, rayo6);

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

    public abstract BlendedSteering nuevo(SteerableAgent owner, SteerableAgent target, MundoI mundo);
}
