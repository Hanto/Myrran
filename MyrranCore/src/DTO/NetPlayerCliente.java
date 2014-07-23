package DTO;// Created by Hanto on 21/07/2014.

import com.badlogic.gdx.utils.ObjectMap;

public class NetPlayerCliente
{
    public Animacion animacion = new Animacion();
    public Posicion posicion = new Posicion();
    public ParametrosSpell parametrosSpell = new ParametrosSpell();
    public SpellSeleccionado spellSeleccionado = new SpellSeleccionado();
    public StopCastear stopCastear = new StopCastear();
    public StartCastear startCastear = new StartCastear();
    public NumTalentosSkillPersonalizado numTalentosSkillPersonalizado = new NumTalentosSkillPersonalizado();
    public ObjectMap<Class, Object> listaDTOs = new ObjectMap<>();

    private DTOs dtos = new DTOs();
    public static class DTOs
    {   public Object[] listaDTOs; }

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

    public void setNumAnimacion(int numAnimacion)
    {
        animacion.animacion = numAnimacion;
        listaDTOs.put(Animacion.class, animacion);
    }

    public void setPosition(float x, float y)
    {
        posicion.posX = (int)x;
        posicion.posY = (int)y;
        listaDTOs.put(Posicion.class, posicion);
    }

    public void setParametrosSpell(Object parametros)
    {
        parametrosSpell.parametros = parametros;
        listaDTOs.put(ParametrosSpell.class, parametrosSpell);
    }

    public void setSpellIDSeleccionado(String spellID, Object parametrosSpell)
    {
        spellSeleccionado.spellIDSeleccionado = spellID;
        spellSeleccionado.parametrosSpell = parametrosSpell;
        listaDTOs.put(SpellSeleccionado.class, spellSeleccionado);
    }

    public void setStopCastear(int screenX, int screenY)
    {
        stopCastear.screenX = screenX;
        stopCastear.screenY = screenY;
        listaDTOs.put(StopCastear.class, stopCastear);
    }

    public void setStartCastear(int screenX, int screenY)
    {
        startCastear.screenX = screenX;
        startCastear.screenY = screenY;
        listaDTOs.put(StartCastear.class, startCastear);
    }

    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {
        numTalentosSkillPersonalizado.skillID = skillID;
        numTalentosSkillPersonalizado.statID = statID;
        numTalentosSkillPersonalizado.valor = valor;
        listaDTOs.put(NumTalentosSkillPersonalizado.class, numTalentosSkillPersonalizado);
    }

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

    public static class NumTalentosSkillPersonalizado
    {
        public String skillID;
        public int statID;
        public int valor;
        public NumTalentosSkillPersonalizado() {}
        public NumTalentosSkillPersonalizado(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }

    public static class Nombre
    {
        public String nombre;
        public Nombre() {}
        public Nombre(String nombre)
        {   this.nombre = nombre; }
    }
}
