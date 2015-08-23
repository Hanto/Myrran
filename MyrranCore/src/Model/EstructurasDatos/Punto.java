package Model.EstructurasDatos;

public class Punto
{
    public int x;
    public int y;

    public Punto()
    {   this.x = 0; this.y = 0; }

    public Punto (int x, int y)
    {   this.x = x; this.y = y; }

    public Punto (float x, float y)
    {   this.x = (int)x; this.y = (int)y; }

    public void set(int x, int y)
    {   this.x = x; this.y = y;}

    public void set(float x, float y)
    {   this.x = (int)x; this.y = (int)y; }
}
