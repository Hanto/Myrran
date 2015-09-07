package Model.Classes.Mobiles.Mob;// Created by Hanto on 04/09/2015.

import Model.GameState.Mundo;
import Model.Mobiles.Propiedades.DebuffeableBase;
import Model.Mobiles.Propiedades.IdentificableBase;
import Model.Mobiles.Propiedades.MobStatsBase;
import Model.Mobiles.Propiedades.VulnerableBase;

public enum MobFactory
{
    NUEVOMOB
    {
        @Override public Mob nuevo (Mundo mundo)
        {
            return new Mob(mundo.getMobID(), 32, 32, new IdentificableBase(), new VulnerableBase(),
                    new DebuffeableBase(), new MobStatsBase());
        }
    };
    public abstract Mob nuevo (Mundo mundo);
}
