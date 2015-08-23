package Model.Classes.AI.Steering.SimpleBehaviors;

import Interfaces.GameState.MundoI;
import Interfaces.Geo.TerrenoI;
import Model.Settings;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class RayCastMuros implements RaycastCollisionDetector<Vector2>
{
    protected MundoI mundo;
    protected int longitudMaximaLista;

    public RayCastMuros(MundoI mundo)
    {   this.mundo = mundo; }

    public RayCastMuros(MundoI mundo, int longitudMaximaRayo)
    {
        this.mundo = mundo;
        this.longitudMaximaLista = longitudMaximaRayo;
    }

    private List<Point> bresenhamLine(int x1, int y1, int x2, int y2)
    {   //Algortimo para locos, copy pasta from the internetz
        List<Point> resultado = new ArrayList<>(longitudMaximaLista != 0? longitudMaximaLista : 48);
        // delta of exact value and rounded value of the dependant variable
        int d = 0;

        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);

        int dy2 = (dy << 1); // slope scaling factors to avoid floating
        int dx2 = (dx << 1); // point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        if (dy <= dx)
        {
            for (;;)
            {
                resultado.add(new Point(x1, y1));

                if (x1 == x2) break;
                x1 += ix;
                d += dy2;
                if (d > dx)
                {
                    y1 += iy;
                    d -= dx2;
                }
            }
        }
        else
        {
            for (;;)
            {
                resultado.add(new Point(x1, y1));

                if (y1 == y2) break;
                y1 += iy;
                d += dx2;
                if (d > dy)
                {
                    x1 += ix;
                    d -= dy2;
                }
            }
        }
        return resultado;
    }


    @Override public boolean collides(Ray<Vector2> ray)
    {   return findCollision(null, ray); }

    @Override public boolean findCollision(Collision<Vector2> outputCollision, Ray<Vector2> inputRay)
    {
        List<Point> puntos = bresenhamLine((int)inputRay.start.x, (int)inputRay.start.y, (int)inputRay.end.x, (int)inputRay.end.y);

        int lastTileX = 0;
        int lastTileY = 0;

        for (Point punto : puntos)
        {   //Si el punto pertenece al mismo tile que el anterior no hace falta comprobar si es solido:
            if (lastTileX != punto.x/Settings.TILESIZE || lastTileY != punto.y/Settings.TILESIZE)
            {
                lastTileX = punto.x / Settings.TILESIZE;
                lastTileY = punto.y / Settings.TILESIZE;

                TerrenoI terreno = mundo.getMapa().getTerreno(lastTileX, lastTileY, 1);

                if (terreno != null && terreno.getIsSolido())
                {
                    if (outputCollision != null)
                    {
                        outputCollision.point.set(punto.x, punto.y);

                        int direccionChoqueX = punto.x % Settings.TILESIZE;
                        int direccionChoqueY = punto.y % Settings.TILESIZE;

                        outputCollision.normal.set(0, 0);

                        //Para averiguar por donde le pega al tile, miramos el punto X, e Y si son el principio o final de tile:
                        if (direccionChoqueX == 0) outputCollision.normal.x = -10;
                        else if (direccionChoqueX == (Settings.TILESIZE - 1)) outputCollision.normal.x = 10;
                        else if (direccionChoqueY == 0) outputCollision.normal.y = -10;
                        else if (direccionChoqueY == (Settings.TILESIZE - 1)) outputCollision.normal.y = 10;

                        if (outputCollision.normal.x == 0 && outputCollision.normal.y == 0)
                            System.out.println("NORMAL MAL CALCULADA");
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
