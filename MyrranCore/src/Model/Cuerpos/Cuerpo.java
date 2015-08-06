package Model.Cuerpos;// Created by Hanto on 16/07/2014.

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import static Model.Settings.METROS_PIXEL;
import static Model.Settings.PIXEL_METROS;

public class Cuerpo implements Disposable
{
    private World world;
    private Body body;

    private float ancho;
    private float alto;
    private Vector2 direccion;

    private Vector2 posicionInterpolada;
    private Vector2 posicionAnterior;
    private float anguloInterpolado;
    private float anguloAnterior;

    public World getWorld()                         { return world; }
    public Body getBody()                           { return body; }
    public Vector2 getDireccion()                   { return direccion; }
    public float getAngulo()                        { return anguloInterpolado; }
    public float getBoxAncho()                      { return ancho; }
    public float getBoxAlto()                       { return alto; }
    public int getAncho()                           { return (int)(ancho * METROS_PIXEL); }
    public int getAlto()                            { return (int)(alto * METROS_PIXEL); }

    public float getBoxXinterpolada()               { return posicionInterpolada.x -ancho/2; }
    public float getBoxYinterpolada()               { return posicionInterpolada.y -alto/2; }
    public void setBody(Body body)                  { this.body = body; }

    
    public Cuerpo(World world, int ancho, int alto)
    {
        direccion = new Vector2();
        posicionInterpolada = new Vector2();
        posicionAnterior = new Vector2();
        this.world = world;
        this.ancho = ancho * PIXEL_METROS;
        this.alto = alto * PIXEL_METROS;
    }

    @Override public void dispose()
    {   world.destroyBody(body); }

    public void setPosition(float x, float y)
    {
        body.setTransform(x *PIXEL_METROS + ancho/2, y *PIXEL_METROS + alto/2, getAngulo());
        posicionAnterior.set(body.getPosition().x, body.getPosition().y);
        posicionInterpolada.set(body.getPosition().x, body.getPosition().y);
    }

    public int getXinterpolada()
    {   return (int)((posicionInterpolada.x - ancho/2) * METROS_PIXEL); }

    public int getYinterpolada()
    {   return (int)((posicionInterpolada.y - alto/2) * METROS_PIXEL); }

    public int getX()
    {   return (int)((body.getPosition().x -ancho/2) * METROS_PIXEL); }

    public int getY()
    {   return (int)((body.getPosition().y -alto/2) * METROS_PIXEL); }

    public int getCentroXinterpolada()
    {   return (int)(posicionInterpolada.x * METROS_PIXEL); }

    public int getCentroYinterpolada()
    {   return (int)(posicionInterpolada.y * METROS_PIXEL); }

    public int getCentroX()
    {   return (int)(body.getPosition().x * METROS_PIXEL); }

    public int getCentroY()
    {   return (int)(body.getPosition().y * METROS_PIXEL); }

    public int getBoxCentroX()
    {   return (int)(body.getPosition().x); }

    public int getBoxCentroY()
    {   return (int)(body.getPosition().y); }

    public void setDireccion(float grados)
    {
        Float anguloRadianes = grados * MathUtils.degreesToRadians;
        direccion.x = (float)Math.cos(anguloRadianes);
        direccion.y = (float)Math.sin(anguloRadianes);
    }

    public void setDireccion(float x, float y)
    {
        direccion.set(x - getCentroX(), y - getCentroY());
        direccion.nor();
    }

    public void setVectorDireccion(float x, float y)
    {   direccion.set(x, y); }

    public void setLinearVelocity(float velocidad)
    {   body.setLinearVelocity(direccion.x * velocidad * PIXEL_METROS, direccion.y * velocidad * PIXEL_METROS);}

    //Los ultimos datos recibidos son los anteriores ahora:
    public void copiarUltimaPosicion()
    {
        if (body != null)
        {
            if (body.isActive()) // && body.getType() == BodyDef.BodyType.DynamicBody
            {
                posicionAnterior.x = body.getPosition().x;
                posicionAnterior.y = body.getPosition().y;
                anguloAnterior = body.getAngle();
            }
        }
    }

    public void interpolarPosicion(float alpha)
    {
        if (body != null)
        {
            if (body.isActive()) // && body.getType() == BodyDef.BodyType.DynamicBody
            {
                posicionInterpolada.x = posicionAnterior.x + (body.getPosition().x - posicionAnterior.x) * alpha;
                posicionInterpolada.y = posicionAnterior.y + (body.getPosition().y - posicionAnterior.y) * alpha;

                anguloInterpolado = anguloAnterior + (body.getAngle() - anguloAnterior) * alpha;
            }
        }
    }
}
