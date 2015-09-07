package Model.Classes.Mobiles.Mob;// Created by Hanto on 04/09/2015.

import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Propiedades.DebuffeableBase;
import Model.Mobiles.Propiedades.IdentificableBase;
import Model.Mobiles.Propiedades.MobStatsBase;
import Model.Mobiles.Propiedades.VulnerableBase;
import com.badlogic.gdx.physics.box2d.World;

public enum MobFactory
{
    MOBMODULAR
    {
        @Override public Mob nuevo(int iD, World world)
        {
            return new Mob(iD, BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 32, 32),
                    new IdentificableBase(), new VulnerableBase(),
                    new DebuffeableBase(), new MobStatsBase());
        }
    };
    public abstract Mob nuevo (int iD, World world);
}
