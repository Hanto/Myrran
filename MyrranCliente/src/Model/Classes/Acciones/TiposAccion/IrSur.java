package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 13/05/2014.

import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrSur extends Accion
{
    public IrSur()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MobPlayer player, ControladorUI controlador)
    {   player.getInput().setIrAbajo(true); }

    @Override public void accionKeyUp(MobPlayer player, ControladorUI controlador)
    {   player.getInput().setIrAbajo(false); }
}
