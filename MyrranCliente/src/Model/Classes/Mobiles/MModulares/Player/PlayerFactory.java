package Model.Classes.Mobiles.MModulares.Player;// Created by Hanto on 04/09/2015.

import Model.Classes.Mobiles.MModulares.Abstractos.CasterPersonalizadoAbstract;
import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Propiedades.*;
import com.badlogic.gdx.physics.box2d.World;

public enum PlayerFactory
{
    PLAYERMODULAR
    {
        @Override public Player nuevo (World world)
        {
            return new Player(BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 48, 48),
                    new IdentificableBase(), new CasterBase(), new CasterPersonalizadoAbstract(),
                    new VulnerableBase(), new DebuffeableBase(), new PCStatsBase(),
                    new AnimableBase());
        }
    };
    public abstract Player nuevo (World world);
}
