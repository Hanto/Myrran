package Model.Mobiles.Steerables;// Created by Hanto on 04/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Espacial;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import Model.AI.Huellas.Huella;
import Model.AI.Huellas.Huellas;
import Interfaces.Observable.AbstractModel;
import Model.Settings;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public abstract class SteerableAgent extends AbstractModel implements SteerableAgentI
{
    protected Vector2 posicion = new Vector2();             // Espacial:
    protected int ultimoCuadranteX;
    protected int ultimoCuadranteY;
    protected Rectangle hitbox = new Rectangle();           // Colisionable(Solido)
    protected Vector2 velocidad = new Vector2();            // Dinamico:
    protected float velocidadMax = 50f; //50
    protected float aceleracionMax = 200f; //200
    protected float velocidadMod = 1.0f;
    protected float velocidadAngular;
    protected float velocidadAngularMax = 5f;
    protected float aceleracionAngularMax = 5f;
    protected float orientacion = 0;                        // Orientable:
    protected Huellas huellas;                              // Seguible:
    protected boolean seguible = false;
    protected boolean tagged = false;                       // Steerable:

    // ESPACIAL:
    //------------------------------------------------------------------------------------------------------------------
    public Vector2 getPosition()                                { return posicion; }
    public float getX()                                         { return posicion.x; }
    public float getY()                                         { return posicion.y; }
    public int getCuadranteX()                                  { return (int)(getX() / (float)(Settings.MAPTILE_NumTilesX * Settings.TILESIZE)); }
    public int getCuadranteY()                                  { return (int)(getY()/ (float)(Settings.MAPTILE_NumTilesY * Settings.TILESIZE)); }
    public int getUltimoCuadranteX()                            { return ultimoCuadranteX; }
    public int getUltimoCuadranteY()                            { return ultimoCuadranteY; }
    public void setUltimoMapTile (int x, int y)                 { this.ultimoCuadranteX = x; this.ultimoCuadranteY = y; }
    public void setPosition(float x, float y)
    {
        this.posicion.set(x, y);
        this.hitbox.setCenter(x, y);
        if (seguible) this.huellas.añadirHuella(x, y);
    }

    // COLISIONABLE:
    //------------------------------------------------------------------------------------------------------------------
    public Rectangle getHitbox()                                { return hitbox; }

    // DINAMICO:
    //------------------------------------------------------------------------------------------------------------------
    public Vector2 getVelocidad()                               { return velocidad; }
    public float getVelocidadMax()                              { return velocidadMax; }
    public float getAceleracionMax()                            { return aceleracionMax; }
    public float getVelocidadMod()                              { return velocidadMod; }
    public float getVelocidadAngular()                          { return velocidadAngular; }
    public float getVelocidadAngularMax()                       { return velocidadAngularMax; }
    public float getAceleracionAngularMax()                     { return aceleracionAngularMax; }
    public void setVelocidadMax(float velocidadMax)             { this.velocidadMax = velocidadMax; }
    public void setAceleracionMax(float aceleracionMax)         { this.aceleracionMax = aceleracionMax; }
    public void setVelocidadMod(float velocidadMod)             { this.velocidadMod = velocidadMod; }
    public void setVelocidadAngular(float velocidadAngular)     { this.velocidadAngular = velocidadAngular; }
    public void setVelocidadAngularMax(float velocidadAngularMax) { this.velocidadAngularMax = velocidadAngularMax; }
    public void setAceleracionAngularMax(float aceleracionAngularMax){this.aceleracionAngularMax = aceleracionAngularMax; }

    // SOLIDO:
    //------------------------------------------------------------------------------------------------------------------
    public int getAncho()                                       { return (int)this.hitbox.getWidth(); }
    public int getAlto()                                        { return (int)this.hitbox.getHeight(); }
    public void setAncho(int ancho)                             { this.hitbox.setWidth(ancho); }
    public void setAlto(int alto)                               { this.hitbox.setHeight(alto); }

    // ORIENTABLE: (los angulos mejor que estem siempre normalizados)
    //------------------------------------------------------------------------------------------------------------------
    public float getOrientacion()                               { return orientacion; }
    public void setOrientacion(float radianes)                  { this.orientacion = radianes % MathUtils.PI2; }

    // SEGUIBLE:
    //------------------------------------------------------------------------------------------------------------------

    public void setHuellas(Huellas huellas)                     { this.huellas = huellas; }
    public void setSeguible(boolean esSeguible)                 { this.seguible = esSeguible; }
    public Iterator<Huella> getListaHuellasIterator()           { return this.huellas.iterator(); }
    public void añadirHuella(Espacial espacial)                 { this.huellas.añadirHuella(espacial); }
    public void actualizarHuellas (float delta)                 { this.huellas.actualizar(delta); }
    public void setTiempoDecayHuellas (float tiempoDecayHuellas){ this.huellas.setTiempoDecayHuellas(tiempoDecayHuellas);}

    // STEERABLE:
    //------------------------------------------------------------------------------------------------------------------

    public Vector2 getLinearVelocity()                          { return getVelocidad(); }
    public float getMaxLinearSpeed()                            { return getVelocidadMax(); }
    public float getMaxLinearAcceleration()                     { return getAceleracionMax(); }
    public float getAngularVelocity()                           { return getVelocidadAngular(); }
    public float getMaxAngularSpeed()                           { return getVelocidadAngularMax(); }
    public float getMaxAngularAcceleration()                    { return getAceleracionAngularMax(); }
    public float getOrientation()                               { return getOrientacion(); }
    public boolean isTagged()                                   { return tagged; }
    public float getBoundingRadius()                            { return (hitbox.getWidth()+hitbox.getHeight())/4; }
    public void setMaxLinearSpeed(float maxLinearSpeed)         { setVelocidadMax(maxLinearSpeed); }
    public void setMaxLinearAcceleration(float maxAcelLineal)   { setAceleracionMax(maxAcelLineal); }
    public void setMaxAngularSpeed(float maxAngularSpeed)       { setVelocidadAngularMax(maxAngularSpeed); }
    public void setMaxAngularAcceleration(float maxAcelAngular) { setAceleracionAngularMax(maxAcelAngular); }
    public void setTagged(boolean tagged)                       { this.tagged = tagged; }

    // OPERACIONES MATEMATICAS(Steerable):
    //------------------------------------------------------------------------------------------------------------------

    public Vector2 newVector()
    {   return new Vector2(); }

    public float vectorToAngle(Vector2 vector) //Radianes:
    {
        float angulo =(float)Math.atan2(vector.y, vector.x);
        return (angulo + MathUtils.PI2) % MathUtils.PI2;
    }

    public Vector2 angleToVector(Vector2 outVector, float angle)
    {
        outVector.x = (float)Math.cos(angle);
        outVector.y = (float)Math.sin(angle);
        return outVector;
    }
}
