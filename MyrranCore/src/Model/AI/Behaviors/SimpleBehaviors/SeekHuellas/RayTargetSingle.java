package Model.AI.Behaviors.SimpleBehaviors.SeekHuellas;

import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class RayTargetSingle extends RayTargetConfiguration
{
    public RayTargetSingle(SteerableAgentI owner, int ancho)
    {
        this.owner = owner;
        this.setNumRays(1);
    }

    public Iterator<Ray<Vector2>> updateTarget (float x, float y)
    {
        rays[0].start.set(owner.getPosition());
        rays[0].end.set(x, y);

        return getRays();
    }

}
