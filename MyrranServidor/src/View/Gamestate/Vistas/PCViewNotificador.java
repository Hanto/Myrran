package View.Gamestate.Vistas;// Created by Hanto on 22/07/2014.

import DTO.DTOsPC;
import Interfaces.EntidadesTipos.MobPC;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class PCViewNotificador
{
    private DTOsPC.Posicion posicion = new DTOsPC.Posicion();
    private DTOsPC.Animacion animacion = new DTOsPC.Animacion();
    private DTOsPC.HPs hps = new DTOsPC.HPs();
    private DTOsPC.Nombre nombre = new DTOsPC.Nombre();

    private ObjectMap<Class, Object> cambiosExcluyentesPersonal = new ObjectMap<>();
    private ObjectMap<Class, Object> cambiosExcluyentesGlobal = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativosPersonal = new ArrayList<>();
    private ArrayList<Object> cambiosAcumulativosGlobal = new ArrayList<>();

    public DTOsPC.PCDTOs dtoPersonal;
    public DTOsPC.PCDTOs dtoGlobal;


    public PCViewNotificador(int connectionID)
    {
        this.dtoPersonal = new DTOsPC.PCDTOs(connectionID);
        this.dtoGlobal = new DTOsPC.PCDTOs(connectionID);
    }

    public void generarDTOs()
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

    //(Excluyente): Global
    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentesGlobal.put(DTOsPC.Posicion.class, posicion);
        }
    }

    //(Excluyente): Global
    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.numAnimacion != (short)numAnimacion)
        {
            animacion.numAnimacion = (short)numAnimacion;
            cambiosExcluyentesGlobal.put(DTOsPC.Animacion.class, animacion);
        }
    }

    //(Excluyente): Global - Personal
    public void setNombre(String nombre)
    {
        if (this.nombre.nombre != nombre)
        {
            this.nombre.nombre = nombre;
            cambiosExcluyentesPersonal.put(DTOsPC.Nombre.class, this.nombre);
            cambiosExcluyentesGlobal.put(DTOsPC.Nombre.class, this.nombre);
        }
    }

    //(Excluyente): Global - Personal
    public void setHPs(float actualHPs, float maxHPs)
    {
        if (hps.actualHPs != actualHPs || hps.maxHPs != maxHPs)
        {
            hps.actualHPs = actualHPs;
            hps.maxHPs = maxHPs;
            cambiosExcluyentesPersonal.put(DTOsPC.HPs.class, hps);
            cambiosExcluyentesGlobal.put(DTOsPC.HPs.class, hps);
        }
    }

    //(Acumulativo): Global - Personal
    public void añadirModificarHPs(float HPs)
    {
        Object modificarHPs = new DTOsPC.ModificarHPs(HPs);
        cambiosAcumulativosPersonal.add(modificarHPs);
        cambiosAcumulativosGlobal.add(modificarHPs);
    }

    //(Acumulativo): Global
    public void añadirEliminarPC(int connectionID)
    {   cambiosAcumulativosGlobal.add(new DTOsPC.EliminarPC(connectionID)); }

    //(Acumulativo): Personal
    public void añadirNoVeAlPC(int connectionID)
    {   cambiosAcumulativosPersonal.add(new DTOsPC.EliminarPC(connectionID)); }

    //(Acumulativo): Personal
    public void añadirVeAlPC(MobPC pc)
    {   cambiosAcumulativosPersonal.add(new DTOsPC.CrearPC(pc)); }

    //(Acumulativo): Personal
    public void añadirSkillPersonalizado(String skillID)
    {   cambiosAcumulativosPersonal.add(new DTOsPC.SkillPersonalizado(skillID)); }

    //(Acumulativo): Personal
    public void añadirNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativosPersonal.add(new DTOsPC.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //(Acumulativo): Personal
    public void añadirCambioTerreno(int tileX, int tileY, int numCapa, short iDTerreno)
    {   cambiosAcumulativosPersonal.add(new DTOsPC.CambioTerreno(tileX, tileY, numCapa, iDTerreno)); }
}
