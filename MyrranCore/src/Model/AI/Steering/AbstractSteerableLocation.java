package Model.AI.Steering;

import Interfaces.EntidadesPropiedades.SteerableLocation;
import Model.AbstractModel;
import Model.Settings;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractSteerableLocation extends AbstractModel implements SteerableLocation
{
    protected Vector2 posicion = new Vector2();             // Espacial:
    protected int ultimoCuadranteX;
    protected int ultimoCuadranteY;
    protected Vector2 velocidadLineal = new Vector2(0,0);   // Steerable:

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getPosition()                                  { return posicion; }
    @Override public float getX()                                           { return posicion.x; }
    @Override public float getY()                                           { return posicion.y; }
    @Override public int getCuadranteX()                                    { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    @Override public int getCuadranteY()                                    { return (int)(getY()/ (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    @Override public int getUltimoCuadranteX()                              { return ultimoCuadranteX; }
    @Override public int getUltimoCuadranteY()                              { return ultimoCuadranteY; }
    @Override public void setPosition(float x, float y)                     { posicion.set(x, y); }
    @Override public void setUltimoMapTile (int x, int y)                   { ultimoCuadranteX = x; ultimoCuadranteY = y; }

    // STEERABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 getLinearVelocity()        { return velocidadLineal; }
    @Override public float getMaxLinearSpeed()          { return 0; }
    @Override public float getMaxLinearAcceleration()   { return 0; }
    @Override public float getAngularVelocity()         { return 0; }
    @Override public float getMaxAngularSpeed()         { return 0; }
    @Override public float getMaxAngularAcceleration()  { return 0; }
    @Override public float getOrientation()             { return 0; }
    @Override public boolean isTagged()                 { return false; }
    @Override public float getBoundingRadius()          { return 1; }

    @Override public void setMaxLinearSpeed(float maxLinearSpeed) { }
    @Override public void setMaxLinearAcceleration(float maxLinearAcceleration) { }
    @Override public void setMaxAngularSpeed(float maxAngularSpeed) { }
    @Override public void setMaxAngularAcceleration(float maxAngularAcceleration){ }
    @Override public void setTagged(boolean tagged) { }

    // OPERACIONES MATEMATICAS(Steerable):
    //------------------------------------------------------------------------------------------------------------------

    @Override public Vector2 newVector()
    {   return new Vector2(); }

    @Override public float vectorToAngle(Vector2 vector) //Radianes:
    {
        float angulo =(float)Math.atan2(vector.y, vector.x);
        return (angulo + MathUtils.PI2) % MathUtils.PI2;
    }

    @Override public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = (float)Math.cos(angle);
        outVector.y = (float)Math.sin(angle);
        return outVector;
    }
}
