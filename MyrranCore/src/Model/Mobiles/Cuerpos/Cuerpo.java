package Model.Mobiles.Cuerpos;// Created by Hanto on 16/07/2014.

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
    private Vector2 direccion;
    private float boxVelocidad;
    private float boxAncho;
    private float boxAlto;
    private Vector2 boxPosicionInterpolada;
    private Vector2 boxPosicionAnterior;
    private Vector2 posicionInterpolada;
    private float anguloAnterior;
    private float anguloInterpolado;

    private boolean calculosInterpolados = false;

    public World getWorld()                         { return world; }
    public Body getBody()                           { return body; }
    public Vector2 getDireccion()                   { return direccion; }
    public float getBoxAncho()                      { return boxAncho; }
    public float getBoxAlto()                       { return boxAlto; }
    public Vector2 getBoxPosicionInterpolada()      { return boxPosicionInterpolada; }
    public float getAnguloInterpolado()             { return anguloInterpolado; }
    public void setBody(Body body)                  { this.body = body; }
    public void setCalculosInterpolados(boolean b)  { this.calculosInterpolados = b; }
    
    public Cuerpo(World world, int ancho, int alto)
    {
        direccion = new Vector2();
        boxPosicionInterpolada = new Vector2();
        boxPosicionAnterior = new Vector2();
        this.world = world;
        this.boxAncho = ancho * PIXEL_METROS;
        this.boxAlto = alto * PIXEL_METROS;
    }

    @Override public void dispose()
    {   world.destroyBody(body); }

    //
    //------------------------------------------------------------------------------------------------------------------

    public void setPosition(float x, float y)
    {
        if (calculosInterpolados)
        {
            body.setTransform(x * PIXEL_METROS + boxAncho / 2, y * PIXEL_METROS + boxAlto / 2, getAnguloInterpolado());
            boxPosicionAnterior.set(body.getPosition().x, body.getPosition().y);
            boxPosicionInterpolada.set(body.getPosition().x, body.getPosition().y);
        }
        else
        {   body.setTransform(x * PIXEL_METROS + boxAncho / 2, y * PIXEL_METROS + boxAlto / 2, body.getAngle()); }
    }

    public int getAncho()
    {   return (int)(boxAncho * METROS_PIXEL); }

    public int getAlto()
    {   return (int)(boxAlto * METROS_PIXEL); }

    public int getBodyCentroX()
    {   return (int)(body.getPosition().x * METROS_PIXEL); }

    public int getBodyCentroY()
    {   return (int)(body.getPosition().y * METROS_PIXEL); }

    public int getX()
    {
        if (calculosInterpolados) return (int)((boxPosicionInterpolada.x - boxAncho /2) * METROS_PIXEL);
        else return (int)((body.getPosition().x - boxAncho /2) * METROS_PIXEL);
    }

    public int getY()
    {
        if (calculosInterpolados) return (int)((boxPosicionInterpolada.y - boxAlto /2) * METROS_PIXEL);
        else return (int)((body.getPosition().y - boxAlto /2) * METROS_PIXEL);
    }

    public int getCentroX()
    {
        if (calculosInterpolados) return (int)(boxPosicionInterpolada.x * METROS_PIXEL);
        else return (int)(body.getPosition().x * METROS_PIXEL);
    }

    public int getCentroY()
    {
        if (calculosInterpolados) return (int)(boxPosicionInterpolada.y * METROS_PIXEL);
        else return (int)(body.getPosition().y * METROS_PIXEL);
    }

    public void setDireccion(float grados)
    {
        Float anguloRadianes = grados * MathUtils.degreesToRadians;
        direccion.x = (float)Math.cos(anguloRadianes);
        direccion.y = (float)Math.sin(anguloRadianes);

        body.setLinearVelocity(direccion.x * boxVelocidad, direccion.y * boxVelocidad);

        //TODO codigo de prueba:
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.atan2(direccion.y, direccion.x));
    }

    public void setDireccion(float x, float y)
    {
        direccion.set(x - getBodyCentroX(), y - getBodyCentroY());
        direccion.nor();

        body.setLinearVelocity(direccion.x * boxVelocidad, direccion.y * boxVelocidad);

        //TODO codigo de prueba:
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.atan2(direccion.y, direccion.x));
    }

    public void setVelocidad(float velocidad)
    {
        boxVelocidad = velocidad * PIXEL_METROS;

        body.setLinearVelocity(direccion.x * velocidad * PIXEL_METROS, direccion.y * velocidad * PIXEL_METROS);
    }

    public void setDireccionVelocidad(float x, float y, float velocidad)
    {
        direccion.set(x - getBodyCentroX(), y - getBodyCentroY());
        direccion.nor();
        boxVelocidad = velocidad * PIXEL_METROS;

        body.setLinearVelocity(direccion.x * velocidad * PIXEL_METROS, direccion.y * velocidad * PIXEL_METROS);

        //TODO codigo de prueba:
        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.atan2(direccion.y, direccion.x));
    }

    public void setDireccionNorVelocidad (float x, float y, float velocidad)
    {
        direccion.set(x, y);
        boxVelocidad = velocidad * PIXEL_METROS;

        body.setLinearVelocity(direccion.x * velocidad * PIXEL_METROS, direccion.y * velocidad * PIXEL_METROS);

        //TODO codigo de prueba:
        body.setTransform(body.getPosition().x, body.getPosition().y, (float)Math.atan2(direccion.y, direccion.x));
    }


    // CODIGO DE INTERPOLACION:
    //------------------------------------------------------------------------------------------------------------------

    //Los ultimos datos recibidos son los anteriores ahora:
    public void copiarUltimaPosicion()
    {
        if (body != null)
        {
            if (body.isActive()) // && body.getType() == BodyDef.BodyType.DynamicBody
            {
                boxPosicionAnterior.x = body.getPosition().x;
                boxPosicionAnterior.y = body.getPosition().y;
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
                boxPosicionInterpolada.set( boxPosicionAnterior.x + (body.getPosition().x - boxPosicionAnterior.x) * alpha,
                                            boxPosicionAnterior.y + (body.getPosition().y - boxPosicionAnterior.y) * alpha);

                anguloInterpolado = anguloAnterior + (body.getAngle() - anguloAnterior) * alpha;
            }
        }
    }
}
