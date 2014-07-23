package DTO;// Created by Hanto on 22/07/2014.

import Interfaces.EntidadesTipos.MobPC;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

public class NetPCServidor
{
    private int connectionID;
    private Posicion posicion = new Posicion();
    private Animacion animacion = new Animacion();
    private Nombre nombre = new Nombre();

    private ObjectMap<Class, Object> cambiosExcluyentesPersonal = new ObjectMap<>();
    private ObjectMap<Class, Object> cambiosExcluyentesGlobal = new ObjectMap<>();
    private ArrayList<Object> cambiosAcumulativosPersonal = new ArrayList<>();
    private ArrayList<Object> cambiosAcumulativosGlobal = new ArrayList<>();

    public PCDTOs dtoPersonal;
    public PCDTOs dtoGlobal;

    public static class PCDTOs
    {
        public int connectionID;
        public Object[] listaDTOs;
        public PCDTOs() {}
        public PCDTOs(int connectionID)
        {   this.connectionID = connectionID; }
    }


    public NetPCServidor(int connectionID)
    {
        this.connectionID = connectionID;
        this.dtoPersonal = new PCDTOs(connectionID);
        this.dtoGlobal = new PCDTOs(connectionID);
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


    //Global (excluyente)
    public void setPosition(float x, float y)
    {
        if (posicion.posX != (int)x || posicion.posY != (int)y)
        {
            posicion.posX = (int) x;
            posicion.posY = (int) y;
            cambiosExcluyentesGlobal.put(Posicion.class, posicion);
        }
    }

    //Global (excluyente)
    public void setNumAnimacion(int numAnimacion)
    {
        if (animacion.animacion != numAnimacion)
        {
            animacion.animacion = numAnimacion;
            cambiosExcluyentesGlobal.put(Animacion.class, animacion);
        }
    }

    //Personal - Global (excluyente)
    public void setNombre(String s)
    {
        if (nombre.nombre != s)
        {
            nombre.nombre = s;
            cambiosExcluyentesPersonal.put(Nombre.class, nombre);
            cambiosExcluyentesGlobal.put(Nombre.class, nombre);
        }
    }

    //Personal - Global (Acumulativo)
    public void añadirModificarHPs(float HPs)
    {
        Object modificarHPs = new ModificarHPs(HPs);
        cambiosAcumulativosPersonal.add(modificarHPs);
        cambiosAcumulativosGlobal.add(modificarHPs);
    }

    //Personal (Acumulativo)
    public void añadirCrearPC(MobPC pc)
    {   cambiosAcumulativosPersonal.add(new CrearPC(pc)); }

    //Personal (acumulativo)
    public void añadirtEliminarPC(int connectionID)
    {   cambiosAcumulativosPersonal.add(new EliminarPC(connectionID)); }

    //Personal (Acumulativo)
    public void añadirSkillPersonalizado(String skillID)
    {   cambiosAcumulativosPersonal.add(new SkillPersonalizado(skillID)); }

    //Personal (Acumulativo)
    public void añadirNumTalentosSkillPersonalizado(String skillID, int statID, int valor)
    {   cambiosAcumulativosPersonal.add(new NumTalentosSkillPersonalizado(skillID, statID, valor)); }

    public static class CrearPC
    {
        public int connectionID;
        public String nombre;
        public int posX;
        public int posY;
        public int nivel;
        public float maxHPs;
        public float actualHPs;
        public int numAnimacion;
        public CrearPC() {}
        public CrearPC(MobPC pc)
        {
            connectionID = pc.getConnectionID(); nombre = pc.getNombre();
            posX = (int)pc.getX(); posY = (int)pc.getY(); nivel = pc.getNivel();
            maxHPs = pc.getMaxHPs(); actualHPs = pc.getActualHPs();
            numAnimacion = pc.getNumAnimacion();
        }
    }

    public static class EliminarPC
    {
        public int connectionID;
        public EliminarPC() {}
        public EliminarPC(int connectionID)
        {   this.connectionID = connectionID; }
    }

    public static class Posicion
    {
        public int posX;
        public int posY;
        public Posicion() {}
        public Posicion (int x, int y)
        {   this.posX = x; this.posY = y; }
    }

    public static class Animacion
    {
        public int animacion;
        public Animacion() {}
        public Animacion(int animacion)
        {   this.animacion = animacion; }
    }

    public static class Nombre
    {
        public String nombre;
        public Nombre() {}
        public Nombre(String nombre)
        {   this.nombre = nombre; }
    }

    public static class ModificarHPs
    {
        public float HPs;
        public ModificarHPs() {}
        public ModificarHPs (float HPs)
        {   this.HPs = HPs; }
    }

    public static class SkillPersonalizado
    {
        public String skillID;
        public SkillPersonalizado() {}
        public SkillPersonalizado(String skillID)
        {   this.skillID = skillID; }
    }

    public static class NumTalentosSkillPersonalizado
    {
        public String skillID;
        public int statID;
        public int valor;
        public NumTalentosSkillPersonalizado() {}
        public NumTalentosSkillPersonalizado(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }
}
