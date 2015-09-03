package Model.AI;// Created by Hanto on 02/09/2015.

import Interfaces.AI.SistemaAggroI;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;

public class SistemaAggro implements SistemaAggroI
{
    @Override public boolean sonEnemigos(Object mob1, Object mob2)
    {
        if (mob1 == mob2) return false;
        if (mob1 instanceof ProyectilI || mob2 instanceof ProyectilI) return false;
        if (mob1 instanceof PCI && mob2 instanceof MobI) return true;
        if (mob1 instanceof MobI && mob1 instanceof PCI) return true;
        if (mob1 instanceof PCI && mob2 instanceof PCI) return true;

        return false;
    }

    public SistemaAggro()
    {}
}
