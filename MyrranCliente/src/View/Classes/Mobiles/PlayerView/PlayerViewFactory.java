package View.Classes.Mobiles.PlayerView;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Caster;
import Model.Classes.Mobiles.Player;
import View.Classes.Actores.NameplateView;
import View.Classes.Actores.PixiePC;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public enum PlayerViewFactory
{
    ILUMINADO
    {
        @Override public PlayerView nuevo (Player player, RayHandler rayHandler)
        {
            PixiePC pixie = new PixiePC("Golem");
            NameplateView nameplateView = new NameplateView((Caster)player);
            PointLight luz = new PointLight(rayHandler, 300);

            return new PlayerView(player, pixie, nameplateView, luz);
        }
    };

    public abstract PlayerView nuevo(Player player, RayHandler rayHandler);
}
