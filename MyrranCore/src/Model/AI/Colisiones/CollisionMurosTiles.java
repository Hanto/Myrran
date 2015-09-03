package Model.AI.Colisiones;// Created by Hanto on 02/09/2015.

import Interfaces.AI.ColisionMurosI;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.Geo.MapaI;
import Interfaces.Geo.TerrenoI;
import Model.Settings;
import com.badlogic.gdx.math.Rectangle;

public class CollisionMurosTiles implements ColisionMurosI
{
    private MapaI mapa;

    public CollisionMurosTiles(MapaI mapa)
    {   this.mapa = mapa; }

    @Override public void collides(Colisionable colisionable)
    {
        Rectangle rectangle = colisionable.getHitbox();

        int tileAbajo =     (int)(rectangle.getY() / Settings.TILESIZE);
        int tileIzquierdo = (int)rectangle.getX() / Settings.TILESIZE;
        int tileArriba =    (int)(rectangle.getY() + rectangle.getHeight()) / Settings.TILESIZE;
        int tileDerecha =   (int)(rectangle.getX() + rectangle.getWidth()) / Settings.TILESIZE;

        for (int y = tileAbajo; y <= tileArriba; y++)
        {
            for (int x = tileIzquierdo; x <= tileDerecha; x++)
            {
                TerrenoI terreno = mapa.getTerreno(x, y, 1);
                if (terreno != null && terreno.getIsSolido())
                    colisionable.checkColisionesConMuro();
            }
        }
    }
}
