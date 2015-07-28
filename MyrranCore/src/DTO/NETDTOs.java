package DTO;

import Data.Settings;
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
        kryo.register(DTOsPC.PlayerDTOs.class);
        //Player Servidor:
        kryo.register(DTOsPC.PCDTOs.class);

        //Player:
        kryo.register(DTOsPC.LogIn.class);
        kryo.register(DTOsPC.Animacion.class);
        kryo.register(DTOsPC.Posicion.class);
        kryo.register(DTOsPC.ParametrosSpell.class);
        kryo.register(DTOsPC.SpellSeleccionado.class);
        kryo.register(DTOsPC.StopCastear.class);
        kryo.register(DTOsPC.StartCastear.class);
        kryo.register(DTOsPC.NumTalentosSkillPersonalizado.class);
        kryo.register(DTOsPC.CrearPC.class);
        kryo.register(DTOsPC.EliminarOtroPC.class);
        kryo.register(DTOsPC.Nombre.class);
        kryo.register(DTOsPC.HPs.class);
        kryo.register(DTOsPC.ModificarHPs.class);
        kryo.register(DTOsPC.SkillPersonalizado.class);
        kryo.register(DTOsPC.CambioTerreno.class);
        kryo.register(DTOsPC.CastingTimePercent.class);
        //Parametros Spell:
        kryo.register(DTOsParametrosSpell.ParametrosEditarTerreno.class);

        //MapView:
        kryo.register(boolean[].class);
        kryo.register(boolean[][].class);
        kryo.register(DTOsMapView.MapTilesAdyacentes.class);
        kryo.register(short[].class);
        kryo.register(DTOsMapView.Mapa.Celda.class);
        kryo.register(DTOsMapView.Mapa.Celda[].class);
        kryo.register(DTOsMapView.Mapa.Celda[][].class);
        kryo.register(DTOsMapView.Mapa.class);
    }
}