package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 07/05/2014.

import InterfacesEntidades.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Model.ModelI;
import Model.Classes.Acciones.Accion;

public class IrOeste extends Accion
{
    public IrOeste()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrIzquierda(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrIzquierda(false); }
}
