package Model.Classes.Acciones.TiposAccion;// Created by Hanto on 07/05/2014.

import Interfaces.EntidadesPropiedades.Maquinable;
import Interfaces.EntidadesTipos.MobPlayer;
import Interfaces.Input.PlayerEstadoI;
import Interfaces.UI.ControladorUI;
import Model.Classes.Acciones.Accion;

public class IrEste extends Accion
{
    public IrEste()
    {   iD = getClass().getSimpleName(); }

    @Override public void accionKeyDown(MobPlayer player, PlayerEstadoI playerE, ControladorUI controlador)
    {   playerE.getPlayerI().setirDerecha(true);;
        playerE.procesarInput();
        player.setInput(playerE.getPlayerO());

        ((Maquinable)player).getInput().setirDerecha(true);
    }

    @Override public void accionKeyUp(MobPlayer player, PlayerEstadoI playerE, ControladorUI controlador)
    {   playerE.getPlayerI().setirDerecha(false);
        playerE.procesarInput();
        player.setInput(playerE.getPlayerO());

        ((Maquinable)player).getInput().setirDerecha(false);
    }
}
