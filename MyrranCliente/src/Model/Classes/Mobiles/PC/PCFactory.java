package Model.Classes.Mobiles.PC;// Created by Hanto on 04/09/2015.

import DB.DAO;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Model.Mobiles.Propiedades.CasterPersonalizadoBase;
import Model.Mobiles.Cuerpos.BodyFactory;
import Model.Mobiles.Propiedades.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pools;

public enum PCFactory
{
    PCMODULAR
    {
        @Override public PC nuevo(int iD, World world)
        {
            return new PC(iD, BodyFactory.crearCuerpo.CIRCLE.nuevo(world, 48, 48),
                    new IdentificableBase(), new CasterBase(), new CasterPersonalizadoBase(DAO.spellDAOFactory),
                    new VulnerableBase(), Pools.obtain(Debuffeable.class), new PCStatsBase(), new AnimableBase());
        }
    };

    public abstract PC nuevo (int iD, World world);
}
