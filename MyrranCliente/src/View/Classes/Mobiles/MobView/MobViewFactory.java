package View.Classes.Mobiles.MobView;// Created by Hanto on 03/09/2015.

import DB.RSC;
import Interfaces.EntidadesTipos.MobI;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.Pixie;

public enum MobViewFactory
{
    MOBVIEW
    {
        @Override public MobView nuevo(MobI mob)
        {
            Pixie actor = new Pixie(RSC.pixieMobRecursosDAO.getPixieMobRecursosDaoDAO().getPixieMob("GrimReaper"));
            NameplateView nameplate =  new NameplateView(mob);
            return new MobView(mob, actor, nameplate);
        }
    };

    public abstract MobView nuevo(MobI mob);
}
