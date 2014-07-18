package Core.Cuerpos;// Created by Hanto on 16/07/2014.

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.IntMap;

import static Data.Settings.METROS_PIXEL;
import static Data.Settings.PIXEL_METROS;

public class ObjetoDinamico
{
    private World world;
    private Body body;

    private float ancho;
    private float alto;

    public int timeStamp = 1;
    public IntMap<Vector2>listaPosiciones = new IntMap<>(21);

    private Vector2 posicionInterpolada;
    private Vector2 posicionAnterior;
    private float anguloInterpolado;
    private float anguloAnterior;

    public World getWorld()                         { return world; }
    public Body getBody()                           { return body; }
    public int getTimeStamp()                       { return timeStamp; }
    public float getBoxAncho()                      { return ancho; }
    public float getBoxAlto()                       { return alto; }
    public int getAncho()                           { return (int)(ancho * METROS_PIXEL); }
    public int getAlto()                            { return (int)(alto * METROS_PIXEL); }

    public float getBoxXinterpolada()               { return posicionInterpolada.x -ancho/2; }
    public float getBoxYinterpolada()               { return posicionInterpolada.y -alto/2; }
    public int getXinterpolada()                    { return (int)((posicionInterpolada.x - ancho/2) * METROS_PIXEL); }
    public int getYinterpolada()                    { return (int)((posicionInterpolada.y - alto/2) * METROS_PIXEL); }
    public int getX()                               { return (int)((body.getPosition().x -ancho/2) * METROS_PIXEL); }
    public int getY()                               { return (int)((body.getPosition().y -alto/2) * METROS_PIXEL); }
    public float getAngulo()                        { return anguloInterpolado; }

    public void setBody(Body body)                  { this.body = body; }
    public void setTimeStamp(int timeStamp)         { this.timeStamp = timeStamp; }
    
    public ObjetoDinamico(World  world, int ancho, int alto)
    {
        posicionInterpolada = new Vector2();
        posicionAnterior = new Vector2();
        this.world = world;
        this.ancho = ancho * PIXEL_METROS;
        this.alto = alto * PIXEL_METROS;
    }

    public void setPosition(float x, float y)
    {   body.setTransform(x *PIXEL_METROS + ancho/2, y *PIXEL_METROS + alto/2, getAngulo()); }

    //Los ultimos datos recibidos son los anteriores ahora:
    public void copiarUltimosDatos()
    {
        if (body != null)
        {
            if (body.isActive() == true) // && body.getType() == BodyDef.BodyType.DynamicBody
            {
                posicionAnterior.x = body.getPosition().x;
                posicionAnterior.y = body.getPosition().y;
                anguloAnterior = body.getAngle();
            }
        }
    }

    public void copiarHistorialPosiciones()
    {
        listaPosiciones.put(timeStamp++, new Vector2(getX(), getY()));
        listaPosiciones.remove(timeStamp-20);
    }

    public void interpolar(float alpha)
    {
        if (body != null)
        {
            if (body.isActive() == true) // && body.getType() == BodyDef.BodyType.DynamicBody
            {
                posicionInterpolada.x = posicionAnterior.x + (body.getPosition().x - posicionAnterior.x) * alpha;
                posicionInterpolada.y = posicionAnterior.y + (body.getPosition().y - posicionAnterior.y) * alpha;

                anguloInterpolado = anguloAnterior + (body.getAngle() - anguloAnterior) * alpha;
            }
        }
    }
}
