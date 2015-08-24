package Model.Classes.AI.SimpleBehaviors.SeekHuellas;

import Interfaces.EntidadesPropiedades.SteerableAgent;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.Iterator;

public abstract class RayTargetConfiguration implements RayTargetConfigurationI
{
    protected SteerableAgent owner;
    protected Ray<Vector2>[] rays;

    public SteerableAgent getOwner()            { return owner; }
    public void setOwner(SteerableAgent owner)  { this.owner = owner; }
    public Iterator<Ray<Vector2>> getRays()     { return Arrays.asList(rays).iterator(); }

    public void setNumRays(int numRays)
    {
        this.rays = new Ray[numRays];
        for (int i = 0; i < numRays; i++)
            this.rays[i] = new Ray(owner.newVector(), owner.newVector());
    }
}
