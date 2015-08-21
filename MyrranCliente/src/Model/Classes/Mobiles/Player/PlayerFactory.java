package Model.Classes.Mobiles.Player;// Created by Hanto on 12/08/2015.

import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Cuerpos.Cuerpo;
import com.badlogic.gdx.physics.box2d.World;

public enum PlayerFactory
{
    GOLEM
    {
        @Override public Player nuevo (World world)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 48, 48);
            return new Player(cuerpo);
        }
    };

    public abstract Player nuevo (World world);
}
