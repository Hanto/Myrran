package Model.Classes.AI.Steering.SimpleBehaviors;

import Interfaces.Geo.MapaI;
import Interfaces.Geo.TerrenoI;
import Model.EstructurasDatos.Punto;
import Model.Settings;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.Vector2;

public class RayCollisionMuros implements RaycastCollisionDetector<Vector2>
{
    private MapaI mapa;

    public RayCollisionMuros(MapaI mapa)
    {   this.mapa = mapa; }

    @Override public boolean collides(Ray<Vector2> ray)
    {   return findCollision(null, ray); }

    @Override public boolean findCollision(Collision<Vector2> outputCollision, Ray<Vector2> inputRay)
    {
        Punto lastTile = new Punto(0,0);
        int x1 = (int)inputRay.start.x;
        int y1 = (int)inputRay.start.y;
        int x2 = (int)inputRay.end.x;
        int y2 = (int)inputRay.end.y;

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
                if (!mismoTile(x1, y1, lastTile))
                    if (checkColision(x1, y1, lastTile, outputCollision)) return true;

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
                if (!mismoTile(x1, y1, lastTile))
                    if (checkColision(x1, y1, lastTile, outputCollision)) return true;

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
        return false;
    }

    private boolean checkColision (int x, int y, Punto tile, Collision<Vector2> outputCollision)
    {
        TerrenoI terreno = mapa.getTerreno(tile.x, tile.y, 1);
        if (terreno != null && terreno.getIsSolido())
        {
            if (outputCollision != null) calcularCollisionData(x, y, tile, outputCollision);
            return true;
        }
        return false;
    }

    private void calcularCollisionData (int x, int y, Punto tile, Collision<Vector2> outputCollision)
    {
        outputCollision.point.set(x, y);

        int direccionChoqueX = x - tile.x * Settings.TILESIZE;
        int direccionChoqueY = y - tile.y * Settings.TILESIZE;

        if (direccionChoqueX == 0)                              outputCollision.normal.x = -10;
        else if (direccionChoqueX == (Settings.TILESIZE - 1))   outputCollision.normal.x = 10;
        else if (direccionChoqueY == 0)                         outputCollision.normal.y = -10;
        else if (direccionChoqueY == (Settings.TILESIZE - 1))   outputCollision.normal.y = 10;
    }

    private boolean mismoTile (int x, int y, Punto tile)
    {
        int nuevoTileX = x / Settings.TILESIZE;
        int nuevoTileY = y / Settings.TILESIZE;

        if ( tile.x == nuevoTileX && tile.y == nuevoTileY ) return true;
        else { tile.set(nuevoTileX, nuevoTileY); return false; }
    }

}
