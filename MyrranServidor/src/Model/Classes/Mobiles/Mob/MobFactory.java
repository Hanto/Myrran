package Model.Classes.Mobiles.Mob;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Model.GameState.Mundo;
import Model.Mobiles.Propiedades.IdentificableBase;
import Model.Mobiles.Propiedades.MobStatsBase;
import Model.Mobiles.Propiedades.VulnerableBase;
import com.badlogic.gdx.utils.Pools;

public enum MobFactory
{
    NUEVOMOB
    {
        @Override public Mob nuevo (Mundo mundo)
        {
            return new Mob(mundo.getMobID(), 32, 32, new IdentificableBase(), new VulnerableBase(),
                    Pools.obtain(Debuffeable.class), new MobStatsBase());
        }
    };
    public abstract Mob nuevo (Mundo mundo);
}
