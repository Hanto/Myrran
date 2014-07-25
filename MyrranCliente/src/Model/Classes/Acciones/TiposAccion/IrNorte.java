package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 05/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrNorte extends Accion
{
    public IrNorte()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrArriba(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ControladorUI controlador)
    {   player.getInput().setIrArriba(false); }
}
