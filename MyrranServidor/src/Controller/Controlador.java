package Controller;// Created by Hanto on 07/04/2014.

import Model.GameState.Mundo;
import View.Gamestate.MundoView;

public class Controlador
{
    protected Mundo mundo;
    protected MundoView mundoView;

    public Mundo getMundo()         { return mundo; }

    public Controlador (Mundo mundo, MundoView mundoView)
    {
        this.mundo = mundo;
        this.mundoView = mundoView;
    }

    public void eliminarPC (int connectionID)
    {   mundo.eliminarPC(connectionID); }
}
