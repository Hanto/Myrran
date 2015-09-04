package Model.EstructurasDatos;// Created by Hanto on 01/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.EstructurasDatos.QuadTreeI;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class QuadTree implements QuadTreeI
{
    private final int maxObjectosNivel = 5;
    private final int maxNiveles = 10;

    private int nivel;
    private Rectangle bounds;
    private List<Colisionable> objetos;
    private QuadTree[] nodos;

    public QuadTree(int nivel, Rectangle bounds)
    {
        this.nivel = nivel;
        this.bounds = bounds;
        this.objetos = new ArrayList<>(maxObjectosNivel);
        this.nodos = new QuadTree[4];
    }

    public QuadTree(float maxX, float maxY)
    {
        this.nivel = 0;
        this.bounds = new Rectangle(0, 0, maxX, maxY);
        this.objetos = new ArrayList<>(maxObjectosNivel);
        this.nodos = new QuadTree[4];
    }

    private void split()
    {
        int subAncho = (int)(bounds.getWidth() /2);
        int subAlto = (int)(bounds.getHeight() /2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        nodos[0] = new QuadTree(nivel +1, new Rectangle(x + subAncho, y, subAncho, subAlto));
        nodos[1] = new QuadTree(nivel +1, new Rectangle(x, y, subAncho, subAlto));
        nodos[2] = new QuadTree(nivel +1, new Rectangle(x, y + subAlto, subAncho, subAlto));
        nodos[3] = new QuadTree(nivel +1, new Rectangle(x + subAncho, y + subAlto, subAncho, subAlto));

        int i = 0;
        while (i < objetos.size())
        {
            int index = getIndice(objetos.get(i).getHitbox());
            if (index != -1)
                nodos[index].add(objetos.remove(i));
            else i++;
        }
    }


    private int getIndice(Rectangle hitbox)
    {
        //Devuelve el cuadrante al que corresponde el objeto, si ocupa varios, devuelve -1
        int indice = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        //Object can completely fit within the top quadrants
        boolean topQuadrant = (hitbox.getY() < horizontalMidpoint && hitbox.getY() + hitbox.getHeight() < horizontalMidpoint);
        //Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (hitbox.getY() > horizontalMidpoint);

        //Object can completely fit within the left quadrants
        if (hitbox.getX() < verticalMidpoint && hitbox.getX() + hitbox.getWidth() < verticalMidpoint)
        {
            if (topQuadrant) indice = 1;
            else if (bottomQuadrant) indice = 2;

        }
        //Object can completely fit within the right quadrants
        else if (hitbox.getX() > verticalMidpoint)
        {
            if (topQuadrant) indice = 0;
            else if (bottomQuadrant) indice = 3;
        }
        return indice;
    }

    @Override public void clear()
    {
        objetos.clear();

        for (int i =0; i < nodos.length; i++)
        {
            if (nodos[i] != null)
            {
                nodos[i].clear();
                nodos[i] = null;
            }
        }
    }

    @Override public void add(Colisionable colisionable)
    {
        int index = getIndice(colisionable.getHitbox());

        if (nodos[0] != null && index != -1)
            nodos[index].add(colisionable);
        else
        {
            objetos.add(colisionable);

            if (nodos[0] == null && objetos.size() >= maxObjectosNivel && nivel < maxNiveles)
                split();
        }
    }

    public List<Colisionable> getCercanos(List<Colisionable> objetosCercanos, Rectangle hitbox)
    {
        if (nodos[0] != null)
        {
            int indice = getIndice(hitbox);
            if (indice != -1)
                nodos[indice].getCercanos(objetosCercanos, hitbox);
            else
                for (QuadTree nodo : nodos)
                    nodo.getCercanos(objetosCercanos, hitbox);
        }
        objetosCercanos.addAll(objetos);

        return objetosCercanos;
    }

    @Override public List<Colisionable> getCercanos(List<Colisionable> objetosCercanos, Colisionable colisionable)
    {
        if (nodos[0] != null)
        {
            int indice = getIndice(colisionable.getHitbox());
            if (indice != -1)
                nodos[indice].getCercanos(objetosCercanos, colisionable);
            else
                for (QuadTree nodo : nodos)
                    nodo.getCercanos(objetosCercanos, colisionable);
        }
        for (Colisionable objeto : objetos)
            if (objeto != colisionable) objetosCercanos.add(objeto);


        return objetosCercanos;
    }

    @Override public void getCercanos(QuadTreeCallBack callBack, Colisionable colisionable)
    {
        if (nodos[0] != null)
        {
            int indice = getIndice(colisionable.getHitbox());
            if (indice != -1)
                nodos[indice].getCercanos(callBack, colisionable);
            else
                for (QuadTree nodo : nodos)
                    nodo.getCercanos(callBack, colisionable);
        }
        for (Colisionable objeto : objetos)
            if (objeto != colisionable) callBack.returnCercano(colisionable, objeto);
    }
}
