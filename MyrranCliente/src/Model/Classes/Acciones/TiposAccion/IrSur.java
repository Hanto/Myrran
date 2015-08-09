package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 13/05/2014.

import Interfaces.EntidadesPropiedades.MaquinablePlayer;
import Interfaces.Model.ModelI;
import Model.Classes.Acciones.Accion;

public class IrSur extends Accion
{
    public IrSur()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrAbajo(true); }

    @Override public void accionKeyUp(MaquinablePlayer player, ModelI inputManager)
    {   player.getInput().setIrAbajo(false); }
}
