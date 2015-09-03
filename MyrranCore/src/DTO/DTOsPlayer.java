package DTO;// Created by Hanto on 24/07/2014.

import Interfaces.EntidadesTipos.PCI;

public class DTOsPlayer
{
    //  DTOS: PLAYER:
    //------------------------------------------------------------------------------------------------------------------

    //Cliente:
    public static class PlayerDTOs
    {   public Object[] listaDTOs; }

    //Servidor:
    public static class PCDTOs
    {
        public int connectionID;
        public Object[] listaDTOs;
        public PCDTOs() {}
        public PCDTOs(int connectionID)
        {   this.connectionID = connectionID; }
    }

    //------------------------------------------------------------------------------------------------------------------

    public static class LogIn
    {   public LogIn() {} }

    public static class Animacion
    {
        public short numAnimacion;
        public Animacion() {}
        public Animacion(short numAnimacion)
        {   this.numAnimacion = numAnimacion; }
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

    public static class SkillPersonalizado
    {
        public String skillID;
        public SkillPersonalizado() {}
        public SkillPersonalizado(String skillID)
        {   this.skillID = skillID; }
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

    public static class CrearPC
    {
        public int connectionID;
        public String nombre;
        public int posX;
        public int posY;
        public int nivel;
        public float maxHPs;
        public float actualHPs;
        public int numAnimacion;
        public CrearPC() {}
        public CrearPC(PCI pc)
        {
            connectionID = pc.getID(); nombre = pc.getNombre();
            posX = (int)pc.getX(); posY = (int)pc.getY(); nivel = pc.getNivel();
            maxHPs = pc.getMaxHPs(); actualHPs = pc.getActualHPs();
            numAnimacion = pc.getNumAnimacion();
        }
    }

    public static class CambioTerreno
    {
        public int tileX;
        public int tileY;
        public short numCapa;
        public short iDTerreno;
        public CambioTerreno() {}
        public CambioTerreno(int tileX, int tileY, int numCapa, short iDTerreno)
        {   this.tileX = tileX; this.tileY = tileY;
            this.numCapa = (short)numCapa; this.iDTerreno = iDTerreno; }
    }

    public static class Nombre
    {
        public String nombre;
        public Nombre() {}
        public Nombre(String nombre)
        {   this.nombre = nombre; }
    }


    //  DTOS: no registrados (uso Local)
    //------------------------------------------------------------------------------------------------------------------

    public static class MaxHPs
    {
        public float maxHPs;
        public MaxHPs () {}
        public MaxHPs(float maxHPs)
        {   this.maxHPs = maxHPs; }
    }
}
