package DTO;

import Model.Settings;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class NetDTOs
{
    public static final int puertoTCP = Settings.NETWORK_PuertoTCP;
    public static final int puertoUDP = Settings.NETWORK_PuertoUDP;
    public static final int timeout = Settings.NETWORK_Client_Timeout;

    public static void register (EndPoint endPoint)
    {
        Kryo kryo = endPoint.getKryo();

        kryo.register(Object[].class);
        // Player Cliente:
        kryo.register(DTOsPlayer.PlayerDTOs.class);
        kryo.register(DTOsPlayer.LogIn.class);

        // Parametros Spell:
        kryo.register(DTOsParametrosSpell.ParametrosEditarTerreno.class);

        // MapView:
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
        kryo.register(short[].class);
        kryo.register(DTOsMapView.Mapa.Celda.class);
        kryo.register(DTOsMapView.Mapa.Celda[].class);
        kryo.register(DTOsMapView.Mapa.Celda[][].class);
        kryo.register(DTOsMapView.Mapa.class);

        // CAMPOVISION:
        //--------------------------------------------------------------------------------------------------------------

        kryo.register(DTOsCampoVision.PCDTOs.class);
        kryo.register(DTOsCampoVision.MobDTOs.class);
        kryo.register(DTOsCampoVision.ProyectilDTOs.class);
        kryo.register(DTOsCampoVision.MiscDTOs.class);

        // general:
        kryo.register(DTOsCampoVision.Eliminar.class);
        kryo.register(DTOsCampoVision.Posicion.class);
        kryo.register(DTOsCampoVision.NumAnimacion.class);
        kryo.register(DTOsCampoVision.Orientacion.class);
        kryo.register(DTOsCampoVision.setHPs.class);
        kryo.register(DTOsCampoVision.ModificarHPs.class);
        kryo.register(DTOsCampoVision.AñadirSpellPersonalizado.class);
        kryo.register(DTOsCampoVision.SetNumTalentosSkillPersonalizado.class);
        kryo.register(DTOsCampoVision.Castear.class);

        // pc:
        kryo.register(DTOsCampoVision.DatosCompletosPC.class);
        kryo.register(DTOsCampoVision.NombrePC.class);

        // proyectil:
        kryo.register(DTOsCampoVision.DatosCompletosProyectil.class);

        // misc:
        kryo.register(DTOsCampoVision.CambioTerrenoMisc.class);
        kryo.register(DTOsCampoVision.MapTilesAdyacentes.class);
    }
}