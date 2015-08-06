package Model.Classes.Mobiles.Proyectil;// Created by Hanto on 06/08/2015.

import Interfaces.GameState.MundoI;
import Model.Cuerpos.BodyFactory;
import Model.Cuerpos.Cuerpo;

public enum ProyectilFactory
{
    ESFERA24x24
    {
        @Override public Proyectil nuevo (MundoI mundo)
        {
            Cuerpo cuerpo = BodyFactory.crearCuerpo.CIRCLE.nuevo(mundo.getWorld(), 24, 24);
            Proyectil proyectil = new Proyectil(mundo, cuerpo);
            return proyectil;
        }
    };

    public abstract Proyectil nuevo(MundoI mundo);
}
