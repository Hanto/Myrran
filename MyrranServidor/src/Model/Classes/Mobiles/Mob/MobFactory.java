package Model.Classes.Mobiles.Mob;// Created by Hanto on 11/08/2015.

import Model.GameState.Mundo;

public enum MobFactory
{
    NUEVO
    {
        @Override public Mob nuevo(Mundo mundo)
        {   return new Mob(mundo.getMobID(), 32, 32); }
    };

    public abstract Mob nuevo(Mundo mundo);
}
