package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 07/05/2014.

import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrOeste extends Accion
{
    public IrOeste()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MobPlayer player, ControladorUI controlador)
    {   player.getInput().setIrIzquierda(true); }

    @Override public void accionKeyUp(MobPlayer player, ControladorUI controlador)
    {   player.getInput().setIrIzquierda(false); }
}
