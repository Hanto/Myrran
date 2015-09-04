package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 05/05/2014.

import InterfacesEntidades.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Model.ModelI;
import Model.Classes.Acciones.Accion;

public class IrNorte extends Accion
{
    public IrNorte()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrArriba(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrArriba(false); }
}
