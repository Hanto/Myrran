package DTO.Remote;// Created by Hanto on 21/07/2014.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.Model.AbstractModel;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.ArrayList;

public class NotificadorPlayerCliente
{
    public AbstractModel model;

    //Stats Excluyentes para enviar por RED al servidor::
    public DTOs.Animacion animacion = new DTOs.Animacion();
    public DTOs.Posicion posicion = new DTOs.Posicion();
    public DTOs.ParametrosSpell parametrosSpell = new DTOs.ParametrosSpell();
    public DTOs.SpellSeleccionado spellSeleccionado = new DTOs.SpellSeleccionado();
    public DTOs.StopCastear stopCastear = new DTOs.StopCastear();
    public DTOs.StartCastear startCastear = new DTOs.StartCastear();
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOs.CastingTimePercent castingTime = new DTOs.CastingTimePercent();
    public DTOs.ModificarHPs modificarHPs = new DTOs.ModificarHPs();

    protected ObjectMap<Class, Object> cambiosExcluyentes = new ObjectMap<>();
    protected ArrayList<Object> cambiosAcumulativos = new ArrayList<>();

    protected DTOs.PlayerDTOs dtos = new DTOs.PlayerDTOs();

    public NotificadorPlayerCliente(AbstractModel model)
    {   this.model = model; }


    public boolean contieneDatos()
    {   return (cambiosExcluyentes.size >0 || cambiosAcumulativos.size() >0); }

    public DTOs.PlayerDTOs getDTOs()
    {
        dtos.listaDTOs = juntarObjectMapYArrayList(cambiosExcluyentes, cambiosAcumulativos);
        cambiosExcluyentes.clear();
        cambiosAcumulativos.clear();
        return dtos;
    }

    private Object[] juntarObjectMapYArrayList(ObjectMap<Class, Object> map, ArrayList<Object> array)
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
    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.numAnimacion != (short)numAnimacion)
        {
            animacion.numAnimacion = (short)numAnimacion;
            cambiosExcluyentes.put(DTOs.Animacion.class, animacion);
            model.notificarActualizacion("numAnimacion", null, animacion);
        }
    }

    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentes.put(DTOs.Posicion.class, posicion);
            model.notificarActualizacion("posicion", null, posicion);
        }
    }

    public void setParametrosSpell(Object parametros)
    {
        if (parametrosSpell.parametros != parametros)
        {
            parametrosSpell.parametros = parametros;
            cambiosExcluyentes.put(DTOs.ParametrosSpell.class, parametrosSpell);
        }
    }

    public void setSpellIDSeleccionado(String spellID, Object parametrosSpell)
    {
        if (spellSeleccionado.spellIDSeleccionado != spellID || spellSeleccionado.parametrosSpell != parametrosSpell)
        {
            spellSeleccionado.spellIDSeleccionado = spellID;
            spellSeleccionado.parametrosSpell = parametrosSpell;
            cambiosExcluyentes.put(DTOs.SpellSeleccionado.class, spellSeleccionado);
        }
    }

    public void setStopCastear(int screenX, int screenY)
    {
        if (stopCastear.screenX != screenX || stopCastear.screenY != screenY)
        {
            stopCastear.screenX = screenX;
            stopCastear.screenY = screenY;
            cambiosExcluyentes.put(DTOs.StopCastear.class, stopCastear);
        }
    }

    public void setStartCastear(int screenX, int screenY)
    {
        if (startCastear.screenX != screenX || startCastear.screenY != screenY)
        {
            startCastear.screenX = screenX;
            startCastear.screenY = screenY;
            cambiosExcluyentes.put(DTOs.StartCastear.class, startCastear);
        }
    }

    //Cambios Acumulativos:
    //(Se envia cualquier valor, aunque sea repetido)
    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativos.add(new DTOs.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setNombre(String nombre)
    {   model.notificarActualizacion("nombre", null, new DTOs.Nombre(nombre)); }

    public void setMaxHPs(float HPs)
    {   model.notificarActualizacion("MaxHPs", null, new DTOs.MaxHPs(HPs)); }

    public void setModificarHPs (float HPs)
    {
        modificarHPs.HPs = HPs;
        model.notificarActualizacion("ModificarHPs", null, modificarHPs); }

    public void setCastingTime(Caster caster)
    {
        castingTime.castingTimePercent = caster.getActualCastingTime() == 0 && caster.getTotalCastingTime() == 0 ? 100 :
                caster.getActualCastingTime() / caster.getTotalCastingTime();
        model.notificarActualizacion("CastingTime", null, castingTime);
    }

    public void setDispose()
    {   model.notificarActualizacion("Eliminar", null, new DTOs.Dispose()); }
}
