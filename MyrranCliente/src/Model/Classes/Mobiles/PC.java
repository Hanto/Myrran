package Model.Classes.Mobiles;// Created by Hanto on 08/04/2014.

import DTO.NetDTO;
import Interfaces.EntidadesPropiedades.Vulnerable;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Model.AbstractModel;
import Model.Classes.Cuerpo.BodyFactory;
import com.badlogic.gdx.physics.box2d.*;

import static Data.Settings.PIXEL_METROS;

public class PC extends AbstractModel implements Vulnerable, MobPC
{
    protected Integer connectionID;
    //Posicion:
    protected float x;                      //Coordenadas X:
    protected float y;                      //Coordenadas Y:
    protected int numAnimacion = 5;

    protected float actualHPs;
    protected float maxHPs;

    protected Body body;

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
    @Override public double getDireccion()                      { return 0; }
    @Override public void setConnectionID (int connectionID)    { this.connectionID = connectionID; }
    @Override public void setNombre (String nombre)             {}
    @Override public void setNivel (int nivel)                  {}
    @Override public void setVelocidaMod(float velocidadMod)    {}
    @Override public void setVelocidadMax(float velocidadMax)   {}
    @Override public void setDireccion(double direccion)        {}


    //Constructor:
    public PC(int connectionID, World world)
    {
        this.connectionID = connectionID;
        body = BodyFactory.crearCuerpo.RECTANGULAR.nuevo(world, 48, 48);
    }

    @Override public void setPosition (float x, float y)
    {
        body.setTransform((x+24) * PIXEL_METROS, (y+24) * PIXEL_METROS, 0);

        this.x = x; this.y = y;
        Object posicionDTO = new NetDTO.PosicionPPC(this);
        notificarActualizacion("setPosition", null, posicionDTO);
    }

    @Override public void setNumAnimacion(int numAnimacion)
    {
        this.numAnimacion = numAnimacion;
        Object AnimacionDTO = new NetDTO.AnimacionPPC(this);
        notificarActualizacion("setTipoAnimacion", null, AnimacionDTO);
        //System.out.println("model animacion a ["+numAnimacion+"]");
    }

    public void dispose()
    {
        body.getWorld().destroyBody(body);
        Object eliminarPC = new NetDTO.EliminarPPC(connectionID);
        notificarActualizacion("eliminarPC", null, eliminarPC);
    }


    @Override public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
        Object modificarHPs = new NetDTO.ModificarHPsPPC(this, HPs);
        notificarActualizacion("modificarHPs", null, modificarHPs);
    }

    public void actualizar (float delta)
    {

    }
}
