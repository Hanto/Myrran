package Model.Classes.Mobiles.Monoliticos.Mob;// Created by Hanto on 11/08/2015.

import Model.GameState.Mundo;

public enum MobOldFactory
{
    NUEVO
    {
        @Override public MobOld nuevo(Mundo mundo)
        {   return new MobOld(mundo.getMobID(), 32, 32); }
    };

    public abstract MobOld nuevo(Mundo mundo);
}
