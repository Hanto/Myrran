package View.Classes.Mobiles.MobView;// Created by Hanto on 03/09/2015.

import DB.RSC;
import Interfaces.EntidadesTipos.MobI;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.Pixie;
import View.Classes.Propiedades.DebuffeableView;

public enum MobViewFactory
{
    MOBVIEW
    {
        @Override public MobView nuevo(MobI mob)
        {
            Pixie actor = new Pixie(RSC.pixieMobRecursosDAO.getPixieMobRecursosDaoDAO().getPixieMob("GrimReaper"));
            return new MobView(mob, actor, new NameplateView(mob), new DebuffeableView(mob));
        }
    };

    public abstract MobView nuevo(MobI mob);
}
