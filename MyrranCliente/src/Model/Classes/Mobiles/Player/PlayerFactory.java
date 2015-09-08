package Model.Classes.Mobiles.Player;// Created by Hanto on 04/09/2015.

import DB.DAO;
import Model.Mobiles.Propiedades.CasterPersonalizadoBase;
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
                    new IdentificableBase(), new CasterBase(), new CasterPersonalizadoBase(DAO.spellDAOFactory),
                    new VulnerableBase(), new Debuffeable(), new PCStatsBase(),
                    new AnimableBase());
        }
    };
    public abstract Player nuevo (World world);
}
