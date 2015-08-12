package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import Model.Cuerpos.BodyFactory;
import Model.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum MobFactory
{
    NUEVO
    {
        @Override public Mob nuevo(int iD, World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 48, 48);
            return new Mob(iD, cuerpo);
        }
    };

    public abstract Mob nuevo(int iD, World world);
}
