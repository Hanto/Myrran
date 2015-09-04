package Model.Classes.Mobiles.Player;// Created by Hanto on 12/08/2015.

import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum PlayerOldFactory
{
    GOLEM
    {
        @Override public PlayerOld nuevo (World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 48, 48);
            return new PlayerOld(cuerpo);
        }
    };

    public abstract PlayerOld nuevo (World world);
}
