package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 07/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrOeste extends Accion
{
    public IrOeste()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrIzquierda(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrIzquierda(false); }
}
