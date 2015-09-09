package Model.Pools;// Created by Hanto on 09/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Model.Mobiles.Propiedades.DebuffeableBase;
import com.badlogic.gdx.utils.Pool;

public class PoolDebuffeable extends Pool<Debuffeable>
{
    public PoolDebuffeable(int capacidadInicial, int capacidadMax)
    {   super(capacidadInicial, capacidadMax); }

    @Override protected Debuffeable newObject()
    {   return new DebuffeableBase(this); }
}
