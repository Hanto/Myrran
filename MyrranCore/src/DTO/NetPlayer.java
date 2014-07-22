package DTO;// Created by Hanto on 21/07/2014.

import java.util.ArrayList;
import java.util.Iterator;

public class NetPlayer
{
    private int connectionID;
    private boolean logIn = false;
    private ArrayList<Object> listaDTOs = new ArrayList<>();

    public int getConnectionID()            { return connectionID; }
    public boolean isLogIn()                { return logIn; }
    public Iterator<Object>getListaDTOs()   { return listaDTOs.iterator(); }
    public boolean contieneDatos()          { return (!listaDTOs.isEmpty() || logIn); }

    public void setConnectionID(int conID)  { connectionID = conID; }
    public void clear()                     { listaDTOs.clear(); logIn = false; }



    public void setLogin(boolean login)
    {   this.logIn = login; }


    public void setNumAnimacion(int numAnimacion)
    {
        Animacion animacion = new Animacion(numAnimacion);
        listaDTOs.add(animacion);
    }
    public static class Animacion
    {
        public int animacion;
        public Animacion() {}
        public Animacion(int animacion)
        {   this.animacion = animacion; }
    }

    public void setPosition(float x, float y)
    {
        Posicion posicion = new Posicion((int)x, (int)y);
        listaDTOs.add(posicion);
    }
    public static class Posicion
    {
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion (int x, int y)
        {   this.posX = x; this.posY = y; }
    }

    public void setParametrosSpell(Object parametros)
    {
        ParametrosSpell parametrosSpell = new ParametrosSpell(parametros);
        listaDTOs.add(parametrosSpell);
    }
    public static class ParametrosSpell
    {
        public Object parametrosSpell;
        public ParametrosSpell() {}
        public ParametrosSpell(Object parametrosSpell)
        {   this.parametrosSpell = parametrosSpell; }
    }

    public void setSpellIDSeleccionado(String spellID, Object parametrosSpell)
    {
        SpellSeleccionado spellIDSeleccionado = new SpellSeleccionado(spellID, parametrosSpell);
        listaDTOs.add(spellIDSeleccionado);
    }
    public static class SpellSeleccionado
    {
        public Object parametrosSpell;
        public String spellIDSeleccionado;
        public SpellSeleccionado() {}
        public SpellSeleccionado(String spellID, Object parametros)
        {   this.spellIDSeleccionado= spellID; this.parametrosSpell = parametros; }
    }

    public void setStopCastear(int screenX, int screenY)
    {
        StopCastear stopCastear = new StopCastear(screenX, screenY);
        listaDTOs.add(stopCastear);
    }
    public static class StopCastear
    {
        public int screenX;
        public int screenY;
        public StopCastear () {}
        public StopCastear(int screenX, int screenY)
        {   this.screenX = screenX; this.screenY = screenY; }
    }

    public void setStartCastear(int screenX, int screenY)
    {
        StartCastear startCastear = new StartCastear(screenX, screenY);
        listaDTOs.add(startCastear);
    }
    public static class StartCastear
    {
        public int screenX;
        public int screenY;
        public StartCastear() {}
        public StartCastear(int screenX, int screenY)
        {   this.screenX = screenX; this.screenY = screenY; }
    }
}
