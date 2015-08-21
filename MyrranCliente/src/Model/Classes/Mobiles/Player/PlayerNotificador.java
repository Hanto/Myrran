package Model.Classes.Mobiles.Player;// Created by Hanto on 21/07/2014.

import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PlayerI;
import Model.AI.Steering.AbstractSteerableAgent;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public abstract class PlayerNotificador extends AbstractSteerableAgent implements PlayerI
{
    //Stats Excluyentes para enviar por RED al servidor::
    private DTOsPlayer.Animacion animacionDTO = new DTOsPlayer.Animacion();
    private DTOsPlayer.Posicion posicionDTO = new DTOsPlayer.Posicion();
    private DTOsPlayer.ParametrosSpell parametrosSpellDTO = new DTOsPlayer.ParametrosSpell();
    private DTOsPlayer.SpellSeleccionado spellSeleccionadoDTO = new DTOsPlayer.SpellSeleccionado();
    private DTOsPlayer.StopCastear stopCastearDTO = new DTOsPlayer.StopCastear();
    private DTOsPlayer.StartCastear startCastearDTO = new DTOsPlayer.StartCastear();
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsPlayer.CastingTimePercent castingTimeDTO = new DTOsPlayer.CastingTimePercent();
    private DTOsPlayer.ModificarHPs modificarHPsDTO = new DTOsPlayer.ModificarHPs();

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

    public void notificarSetParametrosSpell()
    {
        if (parametrosSpellDTO.parametros != getParametrosSpell())
        {
            parametrosSpellDTO.parametros = getParametrosSpell();
            cambiosExcluyentes.put(DTOsPlayer.ParametrosSpell.class, parametrosSpellDTO);
        }
    }

    public void notificarSetSpellIDSeleccionado()
    {
        if (spellSeleccionadoDTO.spellIDSeleccionado != getSpellIDSeleccionado() || spellSeleccionadoDTO.parametrosSpell != getParametrosSpell())
        {
            spellSeleccionadoDTO.spellIDSeleccionado = getSpellIDSeleccionado();
            spellSeleccionadoDTO.parametrosSpell = getParametrosSpell();
            cambiosExcluyentes.put(DTOsPlayer.SpellSeleccionado.class, spellSeleccionadoDTO);
        }
    }

    public void notificarSetStopCastear(int screenX, int screenY)
    {
        //if (stopCastearDTO.screenX != screenX || stopCastearDTO.screenY != screenY)
        {
            stopCastearDTO.screenX = screenX;
            stopCastearDTO.screenY = screenY;
            cambiosExcluyentes.put(DTOsPlayer.StopCastear.class, stopCastearDTO);
        }
    }

    public void notificarSetStartCastear(int screenX, int screenY)
    {
        //if (startCastearDTO.screenX != screenX || startCastearDTO.screenY != screenY)
        {
            startCastearDTO.screenX = screenX;
            startCastearDTO.screenY = screenY;
            cambiosExcluyentes.put(DTOsPlayer.StartCastear.class, startCastearDTO);
        }
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
    {   notificarActualizacion("MaxHPsPlayer", null, new DTOsPlayer.MaxHPs(getMaxHPs())); }

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
