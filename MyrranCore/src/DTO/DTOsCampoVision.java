package DTO;// Created by Hanto on 06/06/2015.

import InterfacesEntidades.EntidadesPropiedades.Animable;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Espacial;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Orientable;
import InterfacesEntidades.EntidadesPropiedades.PCStats;
import InterfacesEntidades.EntidadesPropiedades.Vulnerable;
import InterfacesEntidades.EntidadesTipos.PCI;
import InterfacesEntidades.EntidadesTipos.ProyectilI;

public class DTOsCampoVision
{

    //CONTENEDORES DE LOS DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class PCDTOs
    {
        public int connectionID;
        public Object[] listaDTOs;
        public PCDTOs() {}
        public PCDTOs(int connectionID)
        {   this.connectionID = connectionID; }
    }

    public static class ProyectilDTOs
    {
        public int iD;
        public Object[] listaDTOs;
        public ProyectilDTOs() {}
        public ProyectilDTOs(int iD)
        {   this.iD = iD; }
    }

    public static class MobDTOs
    {
        public int iD;
        public Object[] listaDTOs;
        public MobDTOs() {}
        public MobDTOs(int iD)
        {   this.iD = iD; }
    }

    public static class MiscDTOs
    {
        public Object[] listaDTOs;
        public MiscDTOs() {}
    }

    //PLAYER DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class Eliminar
    {   public Eliminar() {} }

    public static class Posicion
    {
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion(int x, int y)
        {   this.posX = x; this.posY = y; }
        public Posicion(Espacial pc)
        { posX = (int)pc.getX(); posY = (int)pc.getY(); }
    }

    public static class NumAnimacion
    {
        public short numAnimacion;
        public NumAnimacion() {}
        public NumAnimacion(short numAnimacion)
        {   this.numAnimacion = numAnimacion; }
        public NumAnimacion(Animable pc)
        {   this.numAnimacion = (short)pc.getNumAnimacion(); }
    }

    public static class Orientacion
    {
        public float orientacion;
        public Orientacion() {}
        public Orientacion(Orientable mob)
        {   this.orientacion = mob.getOrientacion(); }
    }

    public static class setHPs
    {
        public float actualHPs;
        public float maxHPs;
        public setHPs() {}
        public setHPs(int actualHPs, int maxHPs)
        {   this.actualHPs = actualHPs; this.maxHPs = maxHPs; }
        public setHPs(Vulnerable pc)
        {   this.actualHPs = pc.getActualHPs(); this.maxHPs = pc.getMaxHPs(); }
    }

    public static class ModificarHPs
    {
        public float HPs;
        public ModificarHPs() {}
        public ModificarHPs(float HPs)
        {   this.HPs = HPs; }
    }

    public static class Castear
    {
        public String spellID;
        public Object parametrosSpell;
        public int screenX;
        public int screenY;
        public Castear() {}
        public Castear(String spellID, Object parametrosSpell, int screenX, int screenY)
        {   this.spellID = spellID; this.parametrosSpell = parametrosSpell; this.screenX = screenX; this.screenY = screenY; }
    }

    public static class AñadirSpellPersonalizado
    {
        public String spellID;
        public AñadirSpellPersonalizado() {}
        public AñadirSpellPersonalizado(String spellID)
        {   this.spellID = spellID; }
    }

    public static class SetNumTalentosSkillPersonalizado
    {
        public String skillID;
        public int statID;
        public int valor;
        public SetNumTalentosSkillPersonalizado() {}
        public SetNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }

    // PC DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class DatosCompletosPC
    {
        public String nombre;
        public int nivel;
        public float maxHPs;
        public float actualHPs;
        public int numAnimacion;
        public DatosCompletosPC() {}
        public DatosCompletosPC(PCI pc)
        {
            nombre = pc.getNombre();
            nivel = pc.getNivel();
            maxHPs = pc.getMaxHPs(); actualHPs = pc.getActualHPs();
            numAnimacion = pc.getNumAnimacion();
        }
    }

    public static class NombrePC
    {
        public String nombre;
        public NombrePC() {}
        public NombrePC(String nombre)
        {   this.nombre = nombre; }
        public NombrePC(PCStats pc)
        {   this.nombre = pc.getNombre(); }
    }


    // PROYECTIL DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class DatosCompletosProyectil
    {
        public String spellID;
        public short ancho;
        public short alto;
        public int origenX;
        public int origenY;
        public float direccionEnGrados;
        public float velocidad;
        public float duracionActual;
        public float duracionMax;

        public DatosCompletosProyectil() {}
        public DatosCompletosProyectil(ProyectilI proyectil)
        {
            this.spellID = proyectil.getSpell().getID();
            this.ancho = (short)proyectil.getCuerpo().getAncho();
            this.alto = (short)proyectil.getCuerpo().getAlto();
            this.origenX = (int)proyectil.getX();
            this.origenY = (int)proyectil.getY();
            this.direccionEnGrados = proyectil.getCuerpo().getDireccion().angle();
            this.velocidad = proyectil.getVelocidadMax();
            this.duracionActual = proyectil.getDuracionActual();
            this.duracionMax = proyectil.getDuracionMaxima();
        }
    }

    //MISC DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class CambioTerrenoMisc
    {
        public int tileX;
        public int tileY;
        public short numCapa;
        public short iDTerreno;
        public CambioTerrenoMisc() {}
        public CambioTerrenoMisc(int tileX, int tileY, int numCapa, short iDTerreno)
        {   this.tileX = tileX; this.tileY = tileY;
            this.numCapa = (short)numCapa; this.iDTerreno = iDTerreno; }
    }

    public static class MapTilesAdyacentes
    {
        public boolean[][] mapaAdyacencias;
        public MapTilesAdyacentes() {}
        public MapTilesAdyacentes(boolean[][] mapaAdyacencias)
        {   this.mapaAdyacencias = mapaAdyacencias; }
    }

}
