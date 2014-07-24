package Model.Classes.Mobiles;// Created by Hanto on 21/07/2014.

import DTO.DTOsPC;
import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.Model.AbstractModel;
import com.badlogic.gdx.utils.ObjectMap;
import java.util.ArrayList;

public class PlayerNotificador
{
    public AbstractModel model;

    //Stats Excluyentes para enviar por RED al servidor::
    public DTOsPC.Animacion animacion = new DTOsPC.Animacion();
    public DTOsPC.Posicion posicion = new DTOsPC.Posicion();
    public DTOsPC.ParametrosSpell parametrosSpell = new DTOsPC.ParametrosSpell();
    public DTOsPC.SpellSeleccionado spellSeleccionado = new DTOsPC.SpellSeleccionado();
    public DTOsPC.StopCastear stopCastear = new DTOsPC.StopCastear();
    public DTOsPC.StartCastear startCastear = new DTOsPC.StartCastear();
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    public DTOsPC.CastingTimePercent castingTime = new DTOsPC.CastingTimePercent();
    public DTOsPC.ModificarHPs modificarHPs = new DTOsPC.ModificarHPs();

    protected ObjectMap<Class, Object> cambiosExcluyentes = new ObjectMap<>();
    protected ArrayList<Object> cambiosAcumulativos = new ArrayList<>();

    protected DTOsPC.PlayerDTOs dtos = new DTOsPC.PlayerDTOs();

    public PlayerNotificador(AbstractModel model)
    {   this.model = model; }


    public boolean contieneDatos()
    {   return (cambiosExcluyentes.size >0 || cambiosAcumulativos.size() >0); }

    public DTOsPC.PlayerDTOs getDTOs()
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
            cambiosExcluyentes.put(DTOsPC.Animacion.class, animacion);
            model.notificarActualizacion("numAnimacion", null, animacion);
        }
    }

    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentes.put(DTOsPC.Posicion.class, posicion);
            model.notificarActualizacion("posicion", null, posicion);
        }
    }

    public void setParametrosSpell(Object parametros)
    {
        if (parametrosSpell.parametros != parametros)
        {
            parametrosSpell.parametros = parametros;
            cambiosExcluyentes.put(DTOsPC.ParametrosSpell.class, parametrosSpell);
        }
    }

    public void setSpellIDSeleccionado(String spellID, Object parametrosSpell)
    {
        if (spellSeleccionado.spellIDSeleccionado != spellID || spellSeleccionado.parametrosSpell != parametrosSpell)
        {
            spellSeleccionado.spellIDSeleccionado = spellID;
            spellSeleccionado.parametrosSpell = parametrosSpell;
            cambiosExcluyentes.put(DTOsPC.SpellSeleccionado.class, spellSeleccionado);
        }
    }

    public void setStopCastear(int screenX, int screenY)
    {
        if (stopCastear.screenX != screenX || stopCastear.screenY != screenY)
        {
            stopCastear.screenX = screenX;
            stopCastear.screenY = screenY;
            cambiosExcluyentes.put(DTOsPC.StopCastear.class, stopCastear);
        }
    }

    public void setStartCastear(int screenX, int screenY)
    {
        if (startCastear.screenX != screenX || startCastear.screenY != screenY)
        {
            startCastear.screenX = screenX;
            startCastear.screenY = screenY;
            cambiosExcluyentes.put(DTOsPC.StartCastear.class, startCastear);
        }
    }

    //Cambios Acumulativos:
    //(Se envia cualquier valor, aunque sea repetido)
    public void setNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativos.add(new DTOsPC.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void setNombre(String nombre)
    {   model.notificarActualizacion("nombre", null, new DTOsPC.Nombre(nombre)); }

    public void setMaxHPs(float HPs)
    {   model.notificarActualizacion("MaxHPs", null, new DTOsPC.MaxHPs(HPs)); }

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
}
