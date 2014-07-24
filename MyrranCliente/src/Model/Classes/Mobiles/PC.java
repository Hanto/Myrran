package Model.Classes.Mobiles;// Created by Hanto on 08/04/2014.

import Core.Cuerpos.BodyFactory;
import Core.Cuerpos.Cuerpo;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Model.AbstractModel;
import com.badlogic.gdx.physics.box2d.World;

public class PC extends AbstractModel implements MobPC
{
    protected int connectionID;

    protected float x;
    protected float y;
    protected int numAnimacion = 5;

    protected float actualHPs;
    protected float maxHPs;

    protected Cuerpo cuerpo;
    public PCNotificador notificador;

    public int getConnectionID()            { return connectionID; }
    public int getNumAnimacion()            { return numAnimacion; }
    public float getX()                     { return x; }
    public float getY()                     { return y; }

    //TODO
    @Override public float getActualHPs()                       { return actualHPs; }
    @Override public float getMaxHPs()                          { return maxHPs; }
    @Override public void setActualHPs(float HPs)               { modificarHPs(HPs - actualHPs);}
    @Override public void setMaxHPs(float HPs)                  { this.maxHPs = HPs; }
    @Override public String getNombre()                         { return null; }
    @Override public int getNivel()                             { return 0; }
    @Override public float getVelocidadMod()                    { return 0; }
    @Override public float getVelocidadMax()                    { return 0; }
    @Override public void setConnectionID (int connectionID)    { this.connectionID = connectionID; }
    @Override public void setNombre (String nombre)             {}
    @Override public void setNivel (int nivel)                  {}
    @Override public void setVelocidaMod(float velocidadMod)    {}
    @Override public void setVelocidadMax(float velocidadMax)   {}
    @Override public void setDireccion(float x, float y)        {}
    @Override public void setDireccion(float grados)            {}
    @Override public void setVectorDireccion(float x, float y)  {}

    //Constructor:
    public PC(int connectionID, World world)
    {
        this.notificador = new PCNotificador(this);
        this.connectionID = connectionID;
        this.cuerpo = new Cuerpo(world, 48, 48);
        BodyFactory.darCuerpo.RECTANGULAR.nuevo(cuerpo);
    }

    @Override public void setPosition (float x, float y)
    {
        cuerpo.setPosition(x, y);

        this.x = x; this.y = y;
        notificador.setPosition(x, y);
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        notificador.setNumAnimacion(numAnimacion);
    }

    public void dispose()
    {
        cuerpo.dispose();
        notificador.setDispose();
    }

    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        notificador.setModificarHPs(HPs);
    }

    public void actualizar (float delta)
    {

    }
}
