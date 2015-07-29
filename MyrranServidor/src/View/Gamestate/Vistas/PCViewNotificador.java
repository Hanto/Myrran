package View.Gamestate.Vistas;// Created by Hanto on 22/07/2014.

import DTO.DTOsPlayer;
import Interfaces.EntidadesTipos.PCI;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class PCViewNotificador
{
    private DTOsPlayer.Posicion posicion = new DTOsPlayer.Posicion();
    private DTOsPlayer.Animacion animacion = new DTOsPlayer.Animacion();
    private DTOsPlayer.HPs hps = new DTOsPlayer.HPs();
    private DTOsPlayer.Nombre nombre = new DTOsPlayer.Nombre();

    private ObjectMap<Class, Object> cambiosExcluyentesPersonal = new ObjectMap<>();
    private ObjectMap<Class, Object> cambiosExcluyentesGlobal = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativosPersonal = new ArrayList<>();
    private ArrayList<Object> cambiosAcumulativosGlobal = new ArrayList<>();

    public DTOsPlayer.PCDTOs dtoPersonal;
    public DTOsPlayer.PCDTOs dtoGlobal;


    public PCViewNotificador(int connectionID)
    {
        this.dtoPersonal = new DTOsPlayer.PCDTOs(connectionID);
        this.dtoGlobal = new DTOsPlayer.PCDTOs(connectionID);
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
            cambiosExcluyentesGlobal.put(DTOsPlayer.Posicion.class, posicion);
        }
    }

    //(Excluyente): Global
    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.numAnimacion != (short)numAnimacion)
        {
            animacion.numAnimacion = (short)numAnimacion;
            cambiosExcluyentesGlobal.put(DTOsPlayer.Animacion.class, animacion);
        }
    }

    //(Excluyente): Global - Personal
    public void setNombre(String nombre)
    {
        if (this.nombre.nombre != nombre)
        {
            this.nombre.nombre = nombre;
            cambiosExcluyentesPersonal.put(DTOsPlayer.Nombre.class, this.nombre);
            cambiosExcluyentesGlobal.put(DTOsPlayer.Nombre.class, this.nombre);
        }
    }

    //(Excluyente): Global - Personal
    public void setHPs(float actualHPs, float maxHPs)
    {
        if (hps.actualHPs != actualHPs || hps.maxHPs != maxHPs)
        {
            hps.actualHPs = actualHPs;
            hps.maxHPs = maxHPs;
            cambiosExcluyentesPersonal.put(DTOsPlayer.HPs.class, hps);
            cambiosExcluyentesGlobal.put(DTOsPlayer.HPs.class, hps);
        }
    }

    //(Acumulativo): Global - Personal
    public void añadirModificarHPs(float HPs)
    {
        Object modificarHPs = new DTOsPlayer.ModificarHPs(HPs);
        cambiosAcumulativosPersonal.add(modificarHPs);
        cambiosAcumulativosGlobal.add(modificarHPs);
    }

    //(Acumulativo): Global
    public void añadirEliminarPC(int connectionID)
    {   cambiosAcumulativosGlobal.add(new DTOsPlayer.EliminarPC(connectionID)); }

    //(Acumulativo): Personal
    public void añadirNoVeAlPC(int connectionID)
    {   cambiosAcumulativosPersonal.add(new DTOsPlayer.EliminarPC(connectionID)); }

    //(Acumulativo): Personal
    public void añadirVeAlPC(PCI pc)
    {   cambiosAcumulativosPersonal.add(new DTOsPlayer.CrearPC(pc)); }

    //(Acumulativo): Personal
    public void añadirSkillPersonalizado(String skillID)
    {   cambiosAcumulativosPersonal.add(new DTOsPlayer.SkillPersonalizado(skillID)); }

    //(Acumulativo): Personal
    public void añadirNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativosPersonal.add(new DTOsPlayer.NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    //(Acumulativo): Personal
    public void añadirCambioTerreno(int tileX, int tileY, int numCapa, short iDTerreno)
    {   cambiosAcumulativosPersonal.add(new DTOsPlayer.CambioTerreno(tileX, tileY, numCapa, iDTerreno)); }
}
