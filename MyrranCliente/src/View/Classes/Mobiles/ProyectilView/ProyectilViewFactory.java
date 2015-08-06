package View.Classes.Mobiles.ProyectilView;// Created by Hanto on 05/08/2015.

import DB.RSC;
import Interfaces.EntidadesTipos.ProyectilI;
import View.Classes.Actores.Pixie;
import View.GameState.MundoView;
import box2dLight.PointLight;

public enum ProyectilViewFactory
{
    ILUMINADO
    {
        @Override public ProyectilView nuevo(ProyectilI proyectil, MundoView mundoView)
        {
            Pixie animaciomProyectil = new Pixie(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(proyectil.getSpell()).getAnimacion(0));
            PointLight luz = new PointLight(mundoView.getRayHandler(), 300);
            ProyectilView proyectilView = new ProyectilView(proyectil, mundoView, animaciomProyectil, luz);
            return proyectilView;
        }
    };
    public abstract ProyectilView nuevo(ProyectilI proyectil, MundoView mundoView);
}
