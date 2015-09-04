package Model.Classes.Mobiles.Monoliticos.PC;// Created by Hanto on 12/08/2015.

import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum PCOldFactory
{
    NORMAL
    {
        @Override public PCOld nuevo(int connectionID, World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.RECTANGULAR.nuevo(world, 48, 48);
            return new PCOld(connectionID, cuerpo);
        }
    };

    public abstract PCOld nuevo(int connectionID, World world);
}
