package View.Classes.Mobiles.PCView;// Created by Hanto on 10/08/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesTipos.PCI;
import View.Classes.Actores.PixiePC;
import View.Classes.Actores.NameplateView;
import View.Classes.Propiedades.DebuffeableView;

public enum PCViewFactory
{
    SINLUZ
    {
        @Override public PCView nuevo (PCI pc)
        {
            PixiePC pixie = new PixiePC("Golem");
            return new PCView(pc, pixie, new NameplateView((Caster)pc), new DebuffeableView(pc));
        }
    };

    public abstract PCView nuevo(PCI pc);
}
