package View.Classes.Mobiles.PlayerView;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesTipos.PlayerI;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.PixiePC;
import View.Classes.Propiedades.DebuffeableView;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public enum PlayerViewFactory
{
    ILUMINADO
    {
        @Override public PlayerView nuevo (PlayerI player, RayHandler rayHandler)
        {
            PixiePC pixie = new PixiePC("Golem");
            PointLight luz = new PointLight(rayHandler, 300);

            return new PlayerView(player, pixie, new NameplateView((Caster)player), new DebuffeableView(player),luz);
        }
    };

    public abstract PlayerView nuevo(PlayerI player, RayHandler rayHandler);
}
