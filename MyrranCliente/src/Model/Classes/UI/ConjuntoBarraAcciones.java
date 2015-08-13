package Model.Classes.UI;// Created by Hanto on 08/05/2014.

import DTO.DTOsBarraAcciones;
import Interfaces.EntidadesPropiedades.CasterPersonalizable;
import Model.AbstractClases.AbstractModel;
import Interfaces.UI.BarraAccionesI;
import Model.Classes.Input.InputManager;

import java.util.HashMap;
import java.util.Map;

public class ConjuntoBarraAcciones extends AbstractModel
{
    protected InputManager inputManager;
    protected Map<Integer, BarraAcciones> listaDeBarraAcciones = new HashMap<>();
    protected CasterPersonalizable caster;


    public ConjuntoBarraAcciones(CasterPersonalizable caster, InputManager inputManager)
    {
        this.caster = caster;
        this.inputManager = inputManager;
    }

    public BarraAcciones getBarraAcciones (int iD)
    {   return listaDeBarraAcciones.get(iD); }

    public void añadirBarraAcciones (int filas, int columnas)
    {
        int iDMenor;
        for (iDMenor =0; iDMenor < listaDeBarraAcciones.size(); iDMenor++)
        {   if (!listaDeBarraAcciones.containsKey(iDMenor)) break; }


        BarraAcciones barraAcciones = new BarraAcciones(caster, inputManager, iDMenor, filas, columnas);
        listaDeBarraAcciones.put(barraAcciones.getID(), barraAcciones);

        Object añadirBarraAccionesDTO = new DTOsBarraAcciones.AñadirBarraAcciones(barraAcciones);
        notificarActualizacion("añadirBarraAcciones", null, añadirBarraAccionesDTO);
    }

    public void eliminarBarraAccion(BarraAccionesI barraAccion)
    {   listaDeBarraAcciones.remove(barraAccion.getID());
        barraAccion.eliminar();
    }

    public void eliminarKeycode (int keycode)
    {
        for (Map.Entry<Integer,BarraAcciones> entry : listaDeBarraAcciones.entrySet())
        {   entry.getValue().eliminarKeycode(keycode); }
    }
}
