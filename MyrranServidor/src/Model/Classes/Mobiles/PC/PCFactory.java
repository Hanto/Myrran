package Model.Classes.Mobiles.PC;// Created by Hanto on 11/08/2015.

import Model.Cuerpos.BodyFactory;
import Model.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum PCFactory
{
    NUEVOPC
    {
        @Override public PC nuevo(int connectionID, World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.RECTANGULAR.nuevo(world, 48, 48);
            return new PC(connectionID, cuerpo);
        }
    };
    public abstract PC nuevo (int connectionID, World world);
}
