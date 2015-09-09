package Model.Classes.Pools;// Created by Hanto on 09/09/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Model.Classes.Propiedades.DebuffeableCliente;
import com.badlogic.gdx.utils.Pool;

public class PoolDebuffeableCliente extends Pool<Debuffeable>
{
    public PoolDebuffeableCliente(int capacidadInicial, int capacidadMax)
    {   super(capacidadInicial, capacidadMax); }

    @Override protected Debuffeable newObject()
    {   return new DebuffeableCliente(this); }
}
