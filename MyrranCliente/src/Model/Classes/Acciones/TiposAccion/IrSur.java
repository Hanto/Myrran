package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 13/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrSur extends Accion
{
    public IrSur()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrAbajo(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrAbajo(false); }
}
