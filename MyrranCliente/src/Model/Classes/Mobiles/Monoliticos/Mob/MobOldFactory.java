package Model.Classes.Mobiles.Monoliticos.Mob;// Created by Hanto on 11/08/2015.

import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum MobOldFactory
{
    NUEVO
    {
        @Override public MobOld nuevo(int iD, World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 32, 32);
            return new MobOld(iD, cuerpo);
        }
    };

    public abstract MobOld nuevo(int iD, World world);
}
