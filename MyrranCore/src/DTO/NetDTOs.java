package DTO;

import DTOs.DTOsCaster;
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
        //Player Cliente:
        kryo.register(DTOsPlayer.PlayerDTOs.class);
        //Player Servidor:
        kryo.register(DTOsPlayer.PCDTOs.class);

        //Player:
        kryo.register(DTOsPlayer.LogIn.class);
        kryo.register(DTOsPlayer.Animacion.class);
        kryo.register(DTOsPlayer.Posicion.class);
        kryo.register(DTOsPlayer.ParametrosSpell.class);
        kryo.register(DTOsPlayer.SpellSeleccionado.class);
        kryo.register(DTOsPlayer.StartCastear.class);
        kryo.register(DTOsPlayer.NumTalentosSkillPersonalizado.class);
        kryo.register(DTOsPlayer.CrearPC.class);
        kryo.register(DTOsPlayer.Nombre.class);
        kryo.register(DTOsPlayer.SkillPersonalizado.class);
        kryo.register(DTOsPlayer.CambioTerreno.class);

        kryo.register(DTOsCaster.Castear.class);

        //Parametros Spell:
        kryo.register(DTOsParametrosSpell.ParametrosEditarTerreno.class);

        //MapView:
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
        kryo.register(short[].class);
        kryo.register(DTOsMapView.Mapa.Celda.class);
        kryo.register(DTOsMapView.Mapa.Celda[].class);
        kryo.register(DTOsMapView.Mapa.Celda[][].class);
        kryo.register(DTOsMapView.Mapa.class);

        //PC
        kryo.register(DTOsCampoVision.PCDTOs.class);
        kryo.register(DTOsCampoVision.MiscDTOs.class);
        kryo.register(DTOsCampoVision.DatosCompletosPC.class);
        kryo.register(DTOsCampoVision.EliminarPC.class);
        kryo.register(DTOsCampoVision.PosicionPC.class);
        kryo.register(DTOsCampoVision.NumAnimacionPC.class);
        kryo.register(DTOsCampoVision.NombrePC.class);
        kryo.register(DTOsCampoVision.HPsPC.class);
        kryo.register(DTOsCampoVision.ModificarHPsPC.class);
        kryo.register(DTOsCampoVision.AñadirSpellPersonalizadoPC.class);
        kryo.register(DTOsCampoVision.NumTalentosSkillPersonalizadoPC.class);

        //MOBS:
        kryo.register(DTOsCampoVision.MobDTOs.class);
        kryo.register(DTOsCampoVision.PosicionMob.class);
        kryo.register(DTOsCampoVision.OrientacionMob.class);
        kryo.register(DTOsCampoVision.ModificarHPsMob.class);

        //PROYECTILES:
        kryo.register(DTOsCampoVision.ProyectilDTOs.class);
        kryo.register(DTOsCampoVision.DatosCompletosProyectil.class);
        kryo.register(DTOsCampoVision.EliminarProyectil.class);

        //MISC:
        kryo.register(DTOsCampoVision.MiscDTOs.class);
        kryo.register(DTOsCampoVision.CambioTerrenoMisc.class);
        kryo.register(DTOsCampoVision.MapTilesAdyacentes.class);
    }
}