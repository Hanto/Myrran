package DTO;// Created by Hanto on 07/04/2014.


import Data.Settings;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesTipos.MobPC;
import Interfaces.Spell.SpellI;
import com.badlogic.gdx.utils.ObjectMap;
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
        kryo.register(ObjectMap.class);
        kryo.register(Object[].class);
        kryo.register(Class.class);
        kryo.register(NetPlayerCliente.PlayerDTOs.class);
        kryo.register(NetPlayerCliente.LogIn.class);
        kryo.register(NetPlayerCliente.Animacion.class);
        kryo.register(NetPlayerCliente.Posicion.class);
        kryo.register(NetPlayerCliente.ParametrosSpell.class);
        kryo.register(NetPlayerCliente.SpellSeleccionado.class);
        kryo.register(NetPlayerCliente.StopCastear.class);
        kryo.register(NetPlayerCliente.StartCastear.class);
        kryo.register(NetPlayerCliente.NumTalentosSkillPersonalizado.class);

        kryo.register(NetPCServidor.PCDTOs.class);
        kryo.register(NetPCServidor.CrearPC.class);
        kryo.register(NetPCServidor.Posicion.class);
        kryo.register(NetPCServidor.Animacion.class);
        kryo.register(NetPCServidor.EliminarPC.class);
        kryo.register(NetPCServidor.Nombre.class);
        kryo.register(NetPCServidor.ModificarHPs.class);
        kryo.register(NetPCServidor.SkillPersonalizado.class);
        kryo.register(NetPCServidor.NumTalentosSkillPersonalizado.class);


        kryo.register(AñadirPPC.class);
        kryo.register(PosicionPPC.class);
        kryo.register(AnimacionPPC.class);
        kryo.register(ModificarHPsPPC.class);
        kryo.register(EliminarPPC.class);
        kryo.register(AñadirSpellPersonalizadoPPC.class);
        kryo.register(ModificarNumTalentosSkillPersonalizadoPPC.class);

        kryo.register(CastingTimePercent.class);
        kryo.register(SetTerreno.class);

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

    public static class CastingTimePercent
    {
        public float castingTimePercent;
        public CastingTimePercent() {}
        public CastingTimePercent(Caster Caster)
        {   this.castingTimePercent = Caster.getActualCastingTime() == 0 && Caster.getTotalCastingTime() == 0 ? 100 : Caster.getActualCastingTime() / Caster.getTotalCastingTime(); }
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
