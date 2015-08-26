package Model.Classes.AI.SimpleBehaviors.SeekHuellas;


import Interfaces.EntidadesPropiedades.SteerableAgent;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public class RayTargetSingle extends RayTargetConfiguration
{
    private int ancho;
    private final float grados90 = MathUtils.PI * 0.5f;
    private Vector2 vector;

    public RayTargetSingle(SteerableAgent owner, int ancho)
    {
        this.ancho = ancho;
        this.owner = owner;
        this.vector = new Vector2(0,0);
        this.setNumRays(3);
    }

    public Iterator<Ray<Vector2>> updateTarget (float x, float y)
    {
        rays[0].start.set(owner.getPosition());
        rays[0].end.set(x, y);

        vector.set(x, y).sub(owner.getPosition());
        float angulo = owner.vectorToAngle(vector);

        owner.angleToVector(vector, angulo - grados90).scl(ancho);
        rays[1].start.set(owner.getPosition()).add(vector);
        rays[1].end.set(x, y).add(vector);

        owner.angleToVector(vector, angulo + grados90).scl(ancho);
        rays[2].start.set(owner.getPosition()).add(vector);
        rays[2].end.set(x, y).add(vector);

        return getRays();
    }
}
