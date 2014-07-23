package DTO;// Created by Hanto on 21/07/2014.

import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class NetPlayerCliente
{
    public Animacion animacion = new Animacion();
    public Posicion posicion = new Posicion();
    public ParametrosSpell parametrosSpell = new ParametrosSpell();
    public SpellSeleccionado spellSeleccionado = new SpellSeleccionado();
    public StopCastear stopCastear = new StopCastear();
    public StartCastear startCastear = new StartCastear();

    public ObjectMap<Class, Object> cambiosExcluyentes = new ObjectMap<>();
    public ArrayList<Object> cambiosAcumulativos = new ArrayList<>();

    private PlayerDTOs dtos = new PlayerDTOs();
    public static class PlayerDTOs
    {   public Object[] listaDTOs; }

    public boolean contieneDatos()          { return (cambiosExcluyentes.size >0 || cambiosAcumulativos.size() >0); }

    public PlayerDTOs getDTOs()
    {
        dtos.listaDTOs = juntarObjectMapYArrayList(cambiosExcluyentes, cambiosAcumulativos);
        cambiosExcluyentes.clear();
        cambiosAcumulativos.clear();
        return dtos;
    }

    private Object[] juntarObjectMapYArrayList(ObjectMap<Class, Object> map, ArrayList<Object> array)
    {
        int tamaño = map.size + array.size();
        Object[] fusion = new Object[tamaño];
        int i=0;
        ObjectMap.Values values = map.values();
        while (values.hasNext())
        {   fusion[i] = values.next(); i++; }
        while (i<tamaño)
        {   fusion[i] = array.get(i-map.size); i++; }
        return fusion;
    }

    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.animacion != numAnimacion)
        {
            animacion.animacion = numAnimacion;
            cambiosExcluyentes.put(Animacion.class, animacion);
        }
    }

    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentes.put(Posicion.class, posicion);
        }
    }

    public void setParametrosSpell(Object parametros)
    {
        if (parametrosSpell.parametros != parametros)
        {
            parametrosSpell.parametros = parametros;
            cambiosExcluyentes.put(ParametrosSpell.class, parametrosSpell);
        }
    }

    public void setSpellIDSeleccionado(String spellID, Object parametrosSpell)
    {
        if (spellSeleccionado.spellIDSeleccionado != spellID || spellSeleccionado.parametrosSpell != parametrosSpell)
        {
            spellSeleccionado.spellIDSeleccionado = spellID;
            spellSeleccionado.parametrosSpell = parametrosSpell;
            cambiosExcluyentes.put(SpellSeleccionado.class, spellSeleccionado);
        }
    }

    public void setStopCastear(int screenX, int screenY)
    {
        if (stopCastear.screenX != screenX || stopCastear.screenY != screenY)
        {
            stopCastear.screenX = screenX;
            stopCastear.screenY = screenY;
            cambiosExcluyentes.put(StopCastear.class, stopCastear);
        }
    }

    public void setStartCastear(int screenX, int screenY)
    {
        if (startCastear.screenX != screenX || startCastear.screenY != screenY)
        {
            startCastear.screenX = screenX;
            startCastear.screenY = screenY;
            cambiosExcluyentes.put(StartCastear.class, startCastear);
        }
    }

    //(Acumulativos)
    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativos.add(new NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    public static class LogIn
    {   public LogIn() {} }

    public static class Animacion
    {
        public int animacion;
        public Animacion() {}
        public Animacion(int animacion)
        {   this.animacion = animacion; }
    }

    public static class Posicion
    {
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion (int x, int y)
        {   this.posX = x; this.posY = y; }
    }

    public static class ParametrosSpell
    {
        public Object parametros;
        public ParametrosSpell() {}
        public ParametrosSpell(Object parametros)
        {   this.parametros = parametros; }
    }

    public static class SpellSeleccionado
    {
        public Object parametrosSpell;
        public String spellIDSeleccionado;
        public SpellSeleccionado() {}
        public SpellSeleccionado(String spellID, Object parametros)
        {   this.spellIDSeleccionado= spellID; this.parametrosSpell = parametros; }
    }

    public static class StopCastear
    {
        public int screenX;
        public int screenY;
        public StopCastear () {}
        public StopCastear(int screenX, int screenY)
        {   this.screenX = screenX; this.screenY = screenY; }
    }

    public static class StartCastear
    {
        public int screenX;
        public int screenY;
        public StartCastear() {}
        public StartCastear(int screenX, int screenY)
        {   this.screenX = screenX; this.screenY = screenY; }
    }

    public static class Nombre
    {
        public String nombre;
        public Nombre() {}
        public Nombre(String nombre)
        {   this.nombre = nombre; }
    }

    public static class NumTalentosSkillPersonalizado
    {
        public String skillID;
        public int statID;
        public int valor;
        public NumTalentosSkillPersonalizado() {}
        public NumTalentosSkillPersonalizado(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }
}
