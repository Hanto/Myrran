package DTO;// Created by Hanto on 07/04/2014.


import DTO.Remote.DTOs;
import Data.Settings;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Spell.SpellI;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetDTO
{
    public static final int puertoTCP = Settings.NETWORK_PuertoTCP;
    public static final int puertoUDP = Settings.NETWORK_PuertoUDP;
    public static final int timeout = Settings.NETWORK_Client_Timeout;

    public static void register (EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();

        kryo.register(Object[].class);
        //Player Cliente:
        kryo.register(DTOs.PlayerDTOs.class);
        //Player Servidor:
        kryo.register(DTOs.PCDTOs.class);

        //Contenido Player:
        kryo.register(DTOs.LogIn.class);
        kryo.register(DTOs.Animacion.class);
        kryo.register(DTOs.Posicion.class);
        kryo.register(DTOs.ParametrosSpell.class);
        kryo.register(DTOs.SpellSeleccionado.class);
        kryo.register(DTOs.StopCastear.class);
        kryo.register(DTOs.StartCastear.class);
        kryo.register(DTOs.NumTalentosSkillPersonalizado.class);
        kryo.register(DTOs.CrearPC.class);
        kryo.register(DTOs.EliminarPC.class);
        kryo.register(DTOs.Nombre.class);
        kryo.register(DTOs.HPs.class);
        kryo.register(DTOs.ModificarHPs.class);
        kryo.register(DTOs.SkillPersonalizado.class);
        kryo.register(DTOs.CambioTerreno.class);


        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
        kryo.register(MapTilesAdyacentesEnCliente.class);

        kryo.register(short[].class);
        kryo.register(ActualizarMapa.CeldaMapa.class);
        kryo.register(ActualizarMapa.CeldaMapa[].class);
        kryo.register(ActualizarMapa.CeldaMapa[][].class);
        kryo.register(ActualizarMapa.class);


        kryo.register(ParametrosSpellDTO.ParametrosEditarTerreno.class);
    }

    public static class AñadirPPC
    {
        public int connectionID;

        public AñadirPPC() {}
        public AñadirPPC(MobPC pc)
        {   connectionID = pc.getConnectionID(); }
    }

    public static class PosicionPPC
    {
        public int connectionID;
        public float x;
        public float y;

        public PosicionPPC() {}
        public PosicionPPC(MobPC pc)
        {
            connectionID = pc.getConnectionID();
            x = pc.getX();
            y = pc.getY();
        }
        public PosicionPPC(int connectionID, float x, float y)
        {   this.connectionID = connectionID; this.x = x; this.y = y; }
    }

    public static class AnimacionPPC
    {
        public int connectionID;
        public int numAnimacion;
        public AnimacionPPC() {}
        public AnimacionPPC(MobPC pc)
        {
            connectionID = pc.getConnectionID();
            numAnimacion = pc.getNumAnimacion();
        }
        public AnimacionPPC(int connectionID, int numAnimacion)
        {   this.connectionID = connectionID; this.numAnimacion = numAnimacion; }
    }

    public static class ModificarHPsPPC
    {
        public int connectionID;
        public float HPs;
        public ModificarHPsPPC() {}
        public ModificarHPsPPC(MobPC pc, float HPs)
        {
            this.connectionID = pc.getConnectionID();
            this.HPs = HPs;
        }
    }

    public static class EliminarPPC
    {
        public int connectionID;

        public EliminarPPC() {}
        public EliminarPPC(MobPC pc)
        {   this.connectionID = pc.getConnectionID(); }
        public EliminarPPC(int connectionID)
        {   this.connectionID = connectionID; }
    }

    public static class AñadirSpellPersonalizadoPPC
    {
        public String spellID;
        public AñadirSpellPersonalizadoPPC() {}
        public AñadirSpellPersonalizadoPPC(SpellI spell)
        {   this.spellID = spell.getID(); }
    }

    public static class ModificarNumTalentosSkillPersonalizadoPPC
    {
        public String skillID;
        public int statID;
        public int valor;
        public ModificarNumTalentosSkillPersonalizadoPPC() {}
        public ModificarNumTalentosSkillPersonalizadoPPC(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }

    public static class SetTerreno
    {
        public int celdaX;
        public int celdaY;
        public int numCapa;
        public short iDTerreno;
        public SetTerreno() {}
        public SetTerreno(int celdaX, int celdaY, int numCapa, short iDTerreno)
        {   this.celdaX = celdaX; this.celdaY = celdaY; this.numCapa = numCapa; this.iDTerreno = iDTerreno; }
    }

    public static class MapTilesAdyacentesEnCliente
    {
        public boolean[][] mapaAdyacencias;
        public MapTilesAdyacentesEnCliente() {}
        public MapTilesAdyacentesEnCliente(boolean[][] mapaAdyacencias)
        {   this.mapaAdyacencias = mapaAdyacencias; }
    }

    public static class ActualizarMapa
    {
        public static class CeldaMapa
        {   public short[] celda= new short[Settings.MAPA_Max_Capas_Terreno];
            public CeldaMapa() { }
        }

        public int esquinaInfIzdaX;
        public int esquinaInfIzdaY;
        public CeldaMapa[][] mapa;
        public ActualizarMapa () {}
        public ActualizarMapa(int esquinaInfIzdaX, int esquinaInfIzdaY, int tamañoX, int tamañoY)
        {
            this.esquinaInfIzdaX = esquinaInfIzdaX;
            this.esquinaInfIzdaY = esquinaInfIzdaY;
            mapa = new CeldaMapa[tamañoX][tamañoY];
            for (CeldaMapa[] fila: mapa)
            {   for (int i=0; i<fila.length; i++)
                {   fila[i] = new CeldaMapa(); }
            }
        }
    }
}
