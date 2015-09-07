package Interfaces.EntidadesPropiedades.Steerable;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

public interface Autonomo
{
    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior);
    public void setEncaramientoIndependiente(boolean b);
}
