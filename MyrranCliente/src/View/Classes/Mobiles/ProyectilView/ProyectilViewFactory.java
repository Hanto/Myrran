package View.Classes.Mobiles.ProyectilView;// Created by Hanto on 05/08/2015.

import DB.RSC;
import InterfacesEntidades.EntidadesTipos.ProyectilI;
import View.Classes.Actores.Pixie;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public enum ProyectilViewFactory
{
    ILUMINADO
    {
        @Override public ProyectilView nuevo(ProyectilI proyectil, RayHandler rayHandler)
        {
            Pixie pixieProyectil = new Pixie(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(proyectil.getSpell()).getAnimacion(0));
            PointLight luz = new PointLight(rayHandler, 300);
            ProyectilView proyectilView = new ProyectilView(proyectil, pixieProyectil, luz);
            return proyectilView;
        }
    };
    public abstract ProyectilView nuevo(ProyectilI proyectil, RayHandler rayHandler);
}
