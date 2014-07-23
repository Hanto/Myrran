package DTO;// Created by Hanto on 22/07/2014.

import com.badlogic.gdx.utils.ObjectMap;

public class NetPCServidor
{
    public Posicion posicion = new Posicion();
    public Nombre nombre = new Nombre();
    public ActualHPs actualHPs = new ActualHPs();
    public ObjectMap<Class, Object> listaDTOs = new ObjectMap<>();

    private DTOs dtos = new DTOs();
    public static class DTOs
    {
        public int connectionID;
        public Object[] listaDTOs;
    }

    public boolean contieneDatos()          { return (listaDTOs.size >0); }

    public DTOs getDTOs()
    {
        dtos.listaDTOs = new Object[listaDTOs.size];
        ObjectMap.Values values = listaDTOs.values();
        int i=0;
        while (values.hasNext())
        {   dtos.listaDTOs[i] = values.next(); i++; }
        listaDTOs.clear();
        return dtos;
    }

    public void setPosition(float x, float y)
    {
        posicion.posX = (int)x;
        posicion.posY = (int)y;
        listaDTOs.put(Posicion.class, posicion);
    }

    public void setNombre(String s)
    {
        nombre.nombre = s;
        listaDTOs.put(Nombre.class, nombre);
    }

    public void setActualHPs(float HPs)
    {
        actualHPs.HPs = HPs;
        listaDTOs.put(ActualHPs.class, actualHPs);
    }

    public static class Posicion
    {
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion (int x, int y)
        {   this.posX = x; this.posY = y; }
    }

    public static class Nombre
    {
        public String nombre;
        public Nombre() {}
        public Nombre(String nombre)
        {   this.nombre = nombre; }
    }

    public static class ActualHPs
    {
        public float HPs;
        public ActualHPs() {}
        public ActualHPs(float HPs)
        {   this.HPs = HPs; }
    }

}
