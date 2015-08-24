package Model.Classes.AI.SimpleBehaviors.SeekHuellas;

import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public interface RayTargetConfigurationI
{
    public Iterator<Ray<Vector2>> updateTarget (float x, float y);
}
