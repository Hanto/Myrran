package Model.Classes.Mobiles.Player;// Created by Hanto on 21/07/2014.

import DTOs.*;
import InterfacesEntidades.EntidadesTipos.PlayerI;
import Model.Mobiles.Steerables.SteerableAgent;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public abstract class PlayerNotificador extends SteerableAgent implements PlayerI
{
    //Stats Excluyentes para enviar por RED al servidor::
    private DTOsEspacial.Posicion posicionDTO;
    private DTOsAnimable.NumAnimacion animacionDTO;

    private DTOsNet.Posicion posicionNET;
    private DTOsNet.NumAnimacion animacionNET;
    private DTOsNet.Castear castearNET;
    //Notificaciones locales muy usadas para las cuales creamos variable reusables
    private DTOsCaster.CastingTimePercent castingTimeDTO;
    private DTOsVulnerable.ModificarHPs modificarHPsDTO;

    private ObjectMap<Class, Object> cambiosExcluyentes = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativos = new ArrayList<>();

    private DTOsNet.PlayerDTOs dtos;

    public PlayerNotificador()
    {
        dtos = new DTOsNet.PlayerDTOs();

        posicionDTO = new DTOsEspacial.Posicion(this);
        animacionDTO = new DTOsAnimable.NumAnimacion(this);
        castingTimeDTO = new DTOsCaster.CastingTimePercent();
        modificarHPsDTO = new DTOsVulnerable.ModificarHPs();

        posicionNET = new DTOsNet.Posicion();
        animacionNET = new DTOsNet.NumAnimacion();
        castearNET = new DTOsNet.Castear();
    }


    @Override public boolean notificadorContieneDatos()
    {   return (cambiosExcluyentes.size >0 || cambiosAcumulativos.size() >0); }

    @Override public DTOsNet.PlayerDTOs getDTOs()
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
    //Cambios Excluyentes (Solo se envia el ultimo, y solo si cambia):

    public void notificarSetPosition()
    {
        if (posicionNET.posX != (int)getX() || posicionNET.posY != (int)getY())
        {
            posicionNET.posX = (int) getX();
            posicionNET.posY = (int) getY();
            cambiosExcluyentes.put(DTOsNet.Posicion.class, posicionNET);

            posicionDTO.set(getX(), getY());
            notificarActualizacion("posicionPlayer", null, posicionDTO);
        }
    }

    public void notificarSetNumAnimacion()
    {
        if (animacionNET.numAnimacion != (short)getNumAnimacion())
        {
            animacionNET.numAnimacion = (short)getNumAnimacion();
            cambiosExcluyentes.put(DTOsNet.NumAnimacion.class, animacionNET);

            animacionDTO.set(getNumAnimacion());
            notificarActualizacion("numAnimacionPlayer", null, animacionDTO);
        }
    }

    public void notificarSetStartCastear(int screenX, int screenY)
    {
        castearNET.screenX = screenX;
        castearNET.screenY = screenY;
        castearNET.spellID = this.getSpellIDSeleccionado();
        castearNET.parametrosSpell = this.getParametrosSpell();
        cambiosExcluyentes.put(DTOsNet.Castear.class, castearNET);
    }

    //Cambios Acumulativos:
    //(Se envia cualquier valor, aunque sea repetido)
    public void notificarSetNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativos.add(
            new DTOsNet.SetNumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //  NOTIFICACION LOCAL:
    //------------------------------------------------------------------------------------------------------------------

    public void notificarSetNombre()
    {   notificarActualizacion("nombrePlayer", null, new DTOsPCStats.Nombre(getNombre())); }

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
