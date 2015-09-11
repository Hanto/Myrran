package DTOs;

import Model.Settings;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class KryoDTOs
{
    public static final int puertoTCP = Settings.NETWORK_PuertoTCP;
    public static final int puertoUDP = Settings.NETWORK_PuertoUDP;
    public static final int timeout = Settings.NETWORK_Client_Timeout;

    public static void register (EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();

        kryo.register(Object[].class);

        // NETDTOs:
        //--------------------------------------------------------------------------------------------------------------

        kryo.register(DTOsNet.PlayerDTOs.class);
        kryo.register(DTOsNet.PCDTOs.class);
        kryo.register(DTOsNet.MobDTOs.class);
        kryo.register(DTOsNet.ProyectilDTOs.class);
        kryo.register(DTOsNet.MiscDTOs.class);

        // general:
        kryo.register(DTOsNet.LogIn.class);
        kryo.register(DTOsNet.Eliminar.class);
        kryo.register(DTOsNet.Posicion.class);
        kryo.register(DTOsNet.NumAnimacion.class);
        kryo.register(DTOsNet.Orientacion.class);
        kryo.register(DTOsNet.setHPs.class);
        kryo.register(DTOsNet.ModificarHPs.class);
        kryo.register(DTOsNet.AñadirSpellPersonalizado.class);
        kryo.register(DTOsNet.SetNumTalentosSkillPersonalizado.class);
        kryo.register(DTOsNet.Castear.class);
        kryo.register(DTOsNet.ParametrosEditarTerreno.class);
        kryo.register(DTOsNet.AñadirAura.class);
        kryo.register(DTOsNet.EliminarAura.class);
        kryo.register(DTOsNet.ModificarAuraStacks.class);

        // pc:
        kryo.register(DTOsNet.DatosCompletosPC.class);
        kryo.register(DTOsNet.NombrePC.class);

        // proyectil:
        kryo.register(DTOsNet.DatosCompletosProyectil.class);

        // mob:
        kryo.register(DTOsNet.DatosCompletosMob.class);

        // misc:
        kryo.register(DTOsNet.CambioTerrenoMisc.class);

        // MapView:
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
        kryo.register(short[].class);
        kryo.register(DTOsNet.Mapa.Celda.class);
        kryo.register(DTOsNet.Mapa.Celda[].class);
        kryo.register(DTOsNet.Mapa.Celda[][].class);
        kryo.register(DTOsNet.Mapa.class);

        // MapaView:
        kryo.register(DTOsNet.MapTilesAdyacentes.class);
    }
}