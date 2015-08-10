package View.Classes.Mobiles.PCView;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.PCI;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.NameplateView;

public enum PCViewFactory
{
    SINLUZ
    {
        @Override public PCView nuevo (PCI pc)
        {
            PixiePC pixie = new PixiePC("Golem");
            NameplateView nameplate = new NameplateView((Caster)pc);
            return new PCView(pc, pixie, nameplate);
        }
    };

    public abstract PCView nuevo(PCI pc);
}
