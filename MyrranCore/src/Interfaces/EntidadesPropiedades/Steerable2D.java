package Interfaces.EntidadesPropiedades;// Created by Hanto on 13/08/2015.

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;

public interface Steerable2D extends Steerable<Vector2>, Espacial, Dinamico, Solido, Orientable
{
    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior);
    public void setEncaramientoIndependiente(boolean b);
}
