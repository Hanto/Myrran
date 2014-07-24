package DTO.Remote;// Created by Hanto on 22/07/2014.

import Interfaces.EntidadesTipos.MobPC;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class notificadorPCServidor
{
    private int connectionID;
    private DTOs.Posicion posicion = new DTOs.Posicion();
    private DTOs.Animacion animacion = new DTOs.Animacion();
    private DTOs.HPs hps = new DTOs.HPs();
    private DTOs.Nombre nombre = new DTOs.Nombre();

    private ObjectMap<Class, Object> cambiosExcluyentesPersonal = new ObjectMap<>();
    private ObjectMap<Class, Object> cambiosExcluyentesGlobal = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativosPersonal = new ArrayList<>();
    private ArrayList<Object> cambiosAcumulativosGlobal = new ArrayList<>();

    public DTOs.PCDTOs dtoPersonal;
    public DTOs.PCDTOs dtoGlobal;


    public notificadorPCServidor(int connectionID)
    {
        this.connectionID = connectionID;
        this.dtoPersonal = new DTOs.PCDTOs(connectionID);
        this.dtoGlobal = new DTOs.PCDTOs(connectionID);
    }

    public void getDTOs()
    {
        dtoPersonal.listaDTOs = juntarObjectMapYArrayList(cambiosExcluyentesPersonal, cambiosAcumulativosPersonal);
        dtoGlobal.listaDTOs = juntarObjectMapYArrayList(cambiosExcluyentesGlobal, cambiosAcumulativosGlobal);
        cambiosExcluyentesPersonal.clear();
        cambiosExcluyentesGlobal.clear();
        cambiosAcumulativosPersonal.clear();
        cambiosAcumulativosGlobal.clear();
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

    public boolean contieneDatosDTOPersonal()   { return (dtoPersonal.listaDTOs.length > 0); }
    public boolean contieneDatosDTOGlobal()     { return (dtoGlobal.listaDTOs.length > 0); }

    //  NOTIFICACION REMOTA:
    //------------------------------------------------------------------------------------------------------------------

    //Global (excluyente)
    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentesGlobal.put(DTOs.Posicion.class, posicion);
        }
    }

    //Global (excluyente)
    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.numAnimacion != (short)numAnimacion)
        {
            animacion.numAnimacion = (short)numAnimacion;
            cambiosExcluyentesGlobal.put(DTOs.Animacion.class, animacion);
        }
    }

    //Global - Personal (excluyente)
    public void setNombre(String s)
    {
        if (nombre.nombre != s)
        {
            nombre.nombre = s;
            cambiosExcluyentesPersonal.put(DTOs.Nombre.class, nombre);
            cambiosExcluyentesGlobal.put(DTOs.Nombre.class, nombre);
        }
    }

    //Global - Personal (Excluyente)
    public void setHPs(float actualHPs, float maxHPs)
    {
        if (hps.actualHPs != actualHPs || hps.maxHPs != maxHPs)
        {
            hps.actualHPs = actualHPs;
            hps.maxHPs = maxHPs;
            cambiosExcluyentesPersonal.put(DTOs.HPs.class, hps);
            cambiosExcluyentesGlobal.put(DTOs.HPs.class, hps);
        }
    }

    //Global - Personal (Acumulativo)
    public void añadirModificarHPs(float HPs)
    {
        Object modificarHPs = new DTOs.ModificarHPs(HPs);
        cambiosAcumulativosPersonal.add(modificarHPs);
        cambiosAcumulativosGlobal.add(modificarHPs);
    }

    //Global (Acumulativo)
    public void añadirEliminarPC(int connectionID)
    {   cambiosAcumulativosGlobal.add(new DTOs.EliminarPC(connectionID)); }

    //Personal (Acumulativo)
    public void añadirNoVeAlPC(int connectionID)
    {   cambiosAcumulativosPersonal.add(new DTOs.EliminarPC(connectionID)); }

    //Personal (Acumulativo)
    public void añadirVeAlPC(MobPC pc)
    {   cambiosAcumulativosPersonal.add(new DTOs.CrearPC(pc)); }

    //Personal (Acumulativo)
    public void añadirSkillPersonalizado(String skillID)
    {   cambiosAcumulativosPersonal.add(new DTOs.SkillPersonalizado(skillID)); }

    //Personal (Acumulativo)
    public void añadirNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativosPersonal.add(new DTOs.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //Personal (Acumulativo)
    public void añadirCambioTerreno(int tileX, int tileY, int numCapa, short iDTerreno)
    {   cambiosAcumulativosPersonal.add(new DTOs.CambioTerreno(tileX, tileY, (short)numCapa, iDTerreno)); }
}
