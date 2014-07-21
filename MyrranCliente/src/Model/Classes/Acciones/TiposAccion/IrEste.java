package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 07/05/2014.

import Interfaces.EntidadesPropiedades.Maquinable;
import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrEste extends Accion
{
    public IrEste()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MobPlayer player, ControladorUI controlador)
    {   ((Maquinable)player).getInput().setirDerecha(true); }

    @Override public void accionKeyUp(MobPlayer player, ControladorUI controlador)
    {   ((Maquinable)player).getInput().setirDerecha(false); }
}
