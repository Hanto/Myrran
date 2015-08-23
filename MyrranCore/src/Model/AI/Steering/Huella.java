package Model.AI.Steering;

import Model.EstructurasDatos.Punto;
import Model.Settings;

public class Huella
{
    public Punto punto;
    public float duracion;

    public Huella(int x, int y)
    {
        this.punto = new Punto(x, y);
        this.duracion = 0;
        ajustarPuntoCentroTile(punto);
    }

    public Huella(float x, float y)
    {
        this.punto = new Punto(x, y);
        this.duracion = 0;
        ajustarPuntoCentroTile(punto);
    }

    private void ajustarPuntoCentroTile(Punto punto)
    {
        punto.x = punto.x - punto.x % Settings.TILESIZE + Settings.TILESIZE / 2;
        punto.y = punto.y - punto.y % Settings.TILESIZE + Settings.TILESIZE / 2;
    }
}
