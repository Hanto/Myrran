package Model.AI.Huellas;

import InterfacesEntidades.EntidadesPropiedades.Espaciales.Espacial;
import Model.Settings;

public class Huella
{
    public int x;
    public int y;
    public float duracion;

    public Huella(int x, int y)
    {
        ajustarPuntoCentroTile(x, y);
        this.duracion = 0;
    }

    public Huella(float x, float y)
    {
        ajustarPuntoCentroTile((int)x, (int)y);
        this.duracion = 0;
    }

    public Huella(Espacial espacial)
    {
        ajustarPuntoCentroTile((int)espacial.getX(), (int)espacial.getY());
        this.duracion = 0;
    }

    private void ajustarPuntoCentroTile(int x, int y)
    {
        this.x = x - x % Settings.TILESIZE + Settings.TILESIZE / 2;
        this.y = y - y % Settings.TILESIZE + Settings.TILESIZE / 2;
    }
}
