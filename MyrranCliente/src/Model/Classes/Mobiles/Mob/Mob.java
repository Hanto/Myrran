package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import Interfaces.EntidadesTipos.MobI;
import Interfaces.GameState.MundoI;
import Model.AbstractClases.AbstractModel;
import Model.Cuerpos.Cuerpo;
import com.badlogic.gdx.math.Vector2;

public class Mob extends AbstractModel implements MobI
{
    protected int iD;
    protected Cuerpo cuerpo;

    public Mob(int iD, Cuerpo cuerpo)
    {
        this.iD = iD;
        this.cuerpo = cuerpo;
    }

    @Override public void dispose()
    {
        cuerpo.dispose();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizar(float delta, MundoI mundo)
    {

    }


    @Override
    public float getX()
    {
        return 0;
    }

    @Override
    public float getY()
    {
        return 0;
    }

    @Override
    public int getMapTileX()
    {
        return 0;
    }

    @Override
    public int getMapTileY()
    {
        return 0;
    }

    @Override
    public int getUltimoMapTileX()
    {
        return 0;
    }

    @Override
    public int getUltimoMapTileY()
    {
        return 0;
    }

    @Override public void setPosition(float x, float y)
    {   cuerpo.setPosition(x, y); }

    @Override
    public void setUltimoMapTile(int x, int y)
    {

    }

    @Override public int getID()
    {   return iD; }

    @Override
    public void setID(int iD)
    {

    }

    @Override
    public Vector2 getPosition()
    {
        return null;
    }

    @Override
    public float getOrientation()
    {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity()
    {
        return null;
    }

    @Override
    public float getAngularVelocity()
    {
        return 0;
    }

    @Override
    public float getBoundingRadius()
    {
        return 0;
    }

    @Override
    public boolean isTagged()
    {
        return false;
    }

    @Override
    public void setTagged(boolean tagged)
    {

    }

    @Override
    public Vector2 newVector()
    {
        return null;
    }

    @Override public float vectorToAngle(Vector2 vector)
    {   return (float)Math.atan2(-vector.x, vector.y); }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        return null;
    }

    @Override
    public float getMaxLinearSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed)
    {

    }

    @Override
    public float getMaxLinearAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration)
    {

    }

    @Override
    public float getMaxAngularSpeed()
    {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed)
    {

    }

    @Override
    public float getMaxAngularAcceleration()
    {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration)
    {
        cuerpo.getBody().setTransform(cuerpo.getBody().getPosition().x, cuerpo.getBody().getPosition().y, maxAngularAcceleration);
    }
}
