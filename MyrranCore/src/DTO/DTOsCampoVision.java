package DTO;// Created by Hanto on 06/06/2015.

import Interfaces.EntidadesPropiedades.Animable;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesPropiedades.PCStats;
import Interfaces.EntidadesPropiedades.Vulnerable;
import Interfaces.EntidadesTipos.MobI;
import Interfaces.EntidadesTipos.PCI;
import Interfaces.EntidadesTipos.ProyectilI;

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

    public static class EliminarPC
    {   public EliminarPC() {} }

    public static class PosicionPC
    {
        public int posX;
        public int posY;
        public PosicionPC() {}
        public PosicionPC (int x, int y)
        {   this.posX = x; this.posY = y; }
        public PosicionPC(Espacial pc)
        { posX = (int)pc.getX(); posY = (int)pc.getY(); }
    }

    public static class NumAnimacionPC
    {
        public short numAnimacion;
        public NumAnimacionPC() {}
        public NumAnimacionPC(short numAnimacion)
        {   this.numAnimacion = numAnimacion; }
        public NumAnimacionPC(Animable pc)
        {   this.numAnimacion = (short)pc.getNumAnimacion(); }
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

    public static class HPsPC
    {
        public float actualHPs;
        public float maxHPs;
        public HPsPC() {}
        public HPsPC(int actualHPs, int maxHPs)
        {   this.actualHPs = actualHPs; this.maxHPs = maxHPs; }
        public HPsPC (Vulnerable pc)
        {   this.actualHPs = pc.getActualHPs(); this.maxHPs = pc.getMaxHPs(); }
    }

    public static class ModificarHPsPC
    {
        public float HPs;
        public ModificarHPsPC() {}
        public ModificarHPsPC (float HPs)
        {   this.HPs = HPs; }
    }

    public static class AñadirSpellPersonalizadoPC
    {
        public String spellID;
        public AñadirSpellPersonalizadoPC() {}
        public AñadirSpellPersonalizadoPC(String spellID)
        {   this.spellID = spellID; }
    }

    public static class NumTalentosSkillPersonalizadoPC
    {
        public String skillID;
        public int statID;
        public int valor;
        public NumTalentosSkillPersonalizadoPC() {}
        public NumTalentosSkillPersonalizadoPC(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }

    // MOB DTOS:
    //------------------------------------------------------------------------------------------------------------------

    public static class PosicionMob
    {
        public int posX;
        public int posY;
        public PosicionMob() {}
        public PosicionMob(int x, int y)
        {   this.posX = x; this.posY = y; }
        public PosicionMob(Espacial mob)
        {   this.posX = (int) mob.getX(); this.posY = (int) mob.getY(); }
    }

    public static class OrientacionMob
    {
        public float orientacion;
        public OrientacionMob() {}
        public OrientacionMob(MobI mob)
        {   this.orientacion = mob.getOrientacion(); }
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

    public static class EliminarProyectil
    {
        public int iD;
        public EliminarProyectil() {}
        public EliminarProyectil(ProyectilI proyectil)
        {   this.iD = proyectil.getID(); }
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
