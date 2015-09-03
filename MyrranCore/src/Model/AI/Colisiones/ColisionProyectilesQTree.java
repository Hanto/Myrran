package Model.AI.Colisiones;// Created by Hanto on 03/09/2015.

import Interfaces.AI.ColisionProyectilesI;
import Interfaces.AI.SistemaAggroI;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.EstructurasDatos.QuadTreeI;
import Model.EstructurasDatos.QuadTreeCallBack;

public class ColisionProyectilesQTree implements ColisionProyectilesI, QuadTreeCallBack
{
    private SistemaAggroI aggro;
    private QuadTreeI quadTree;

    public ColisionProyectilesQTree(QuadTreeI quadTree, SistemaAggroI sistemaAggro)
    {
        this.aggro = sistemaAggro;
        this.quadTree = quadTree;
    }

    @Override public void collides (ProyectilI proyectil)
    {   quadTree.getCercanos(this, proyectil); }

    @Override public void returnCercano(Colisionable proyectil, Colisionable mobCercano)
    {
        if (aggro.sonEnemigos(((ProyectilI)proyectil).getOwner(), mobCercano) && proyectil.getHitbox().overlaps(mobCercano.getHitbox()))
        {   proyectil.checkColisionesConMob(mobCercano); }
    }

    /*@Override public void collides(ProyectilI proyectil)
    {
        List<Colisionable> listaMobsCercanos = new ArrayList<>();
        quadTree.getCercanos(listaMobsCercanos, proyectil);
        for (Colisionable mobCercano : listaMobsCercanos)
        {
            if (aggro.sonEnemigos(proyectil.getOwner(), mobCercano) && proyectil.getHitbox().overlaps(mobCercano.getHitbox()))
            {   proyectil.checkColisionesConMob(mobCercano); }
        }
    }*/
}
