package Model.Classes.Mobiles.MModulares.Player;// Created by Hanto on 21/07/2014.

import DTO.DTOsPlayer;
import DTOs.DTOsCaster;
import DTOs.DTOsVulnerable;
import InterfacesEntidades.EntidadesTipos.PlayerI;
import Model.Mobiles.Steerables.SteerableAgent;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public abstract class PlayerNotificador extends SteerableAgent implements PlayerI
{
    //Stats Excluyentes para enviar por RED al servidor::
    private DTOsPlayer.Animacion animacionDTO = new DTOsPlayer.Animacion();
    private DTOsPlayer.Posicion posicionDTO = new DTOsPlayer.Posicion();
    private DTOsCaster.Castear castearDTO = new DTOsCaster.Castear();
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsCaster.CastingTimePercent castingTimeDTO = new DTOsCaster.CastingTimePercent();
    private DTOsVulnerable.ModificarHPs modificarHPsDTO = new DTOsVulnerable.ModificarHPs();

    private ObjectMap<Class, Object> cambiosExcluyentes = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativos = new ArrayList<>();

    private DTOsPlayer.PlayerDTOs dtos = new DTOsPlayer.PlayerDTOs();

    public PlayerNotificador()
    {}


    public boolean notificadorContieneDatos()
    {   return (cambiosExcluyentes.size >0 || cambiosAcumulativos.size() >0); }

    public DTOsPlayer.PlayerDTOs getDTOs()
    {
        dtos.listaDTOs = juntarObjectMapYArrayList(cambiosExcluyentes, cambiosAcumulativos);
        cambiosExcluyentes.clear();
        cambiosAcumulativos.clear();
        return dtos;
    }

    public Object[] juntarObjectMapYArrayList(ObjectMap<Class, Object> map, ArrayList<Object> array)
    {
        int tamaño = map.size + array.size();
        Object[] fusion = new Object[tamaño];
        int i=0;
        ObjectMap.Values values = map.values();
        while (values.hasNext())
        {   fusion[i] = values.next(); i++; }
        while (i<tamaño)
        {   fusion[i] = array.get(i-map.size); i++; }
        return fusion;
    }

    //  NOTIFICACION REMOTA:
    //------------------------------------------------------------------------------------------------------------------

    //Cambios Excluyentes:
    //(Solo se envia el ultimo, y solo si cambia)
    public void notificarSetNumAnimacion()
    {
        if (animacionDTO.numAnimacion != (short)getNumAnimacion())
        {
            animacionDTO.numAnimacion = (short)getNumAnimacion();
            cambiosExcluyentes.put(DTOsPlayer.Animacion.class, animacionDTO);
            notificarActualizacion("numAnimacionPlayer", null, animacionDTO);
        }
    }

    public void notificarSetPosition()
    {
        if (posicionDTO.posX != (int)getX() || posicionDTO.posY != (int)getY())
        {
            posicionDTO.posX = (int) getX();
            posicionDTO.posY = (int) getY();
            cambiosExcluyentes.put(DTOsPlayer.Posicion.class, posicionDTO);
            notificarActualizacion("posicionPlayer", null, posicionDTO);
        }
    }

    public void notificarSetStartCastear(int screenX, int screenY)
    {
        castearDTO.screenX = screenX;
        castearDTO.screenY = screenY;
        castearDTO.spellID = this.getSpellIDSeleccionado();
        castearDTO.parametrosSpell = this.getParametrosSpell();
        cambiosExcluyentes.put(DTOsPlayer.StartCastear.class, castearDTO);
    }

    //Cambios Acumulativos:
    //(Se envia cualquier valor, aunque sea repetido)
    public void notificarSetNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativos.add(new DTOsPlayer.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetNombre()
    {   notificarActualizacion("nombrePlayer", null, new DTOsPlayer.Nombre(getNombre())); }

    public void notificarSetMaxHPs()
    {   notificarActualizacion("MaxHPsPlayer", null, new DTOsVulnerable.HPs(getActualHPs(), getMaxHPs())); }

    public void notificarSetModificarHPs (float HPs)
    {
        modificarHPsDTO.HPs = HPs;
        notificarActualizacion("ModificarHPsPlayer", null, modificarHPsDTO); }

    public void notificarSetCastingTime()
    {
        castingTimeDTO.castingTimePercent = getActualCastingTime() == 0 && getTotalCastingTime() == 0 ? 100 : getActualCastingTime() / getTotalCastingTime();
        notificarActualizacion("CastingTimePlayer", null, castingTimeDTO);
    }
}
