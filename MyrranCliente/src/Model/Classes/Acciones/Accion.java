package Model.Classes.Acciones;// Created by Hanto on 05/05/2014.

import Interfaces.Misc.UI.AccionI;

public abstract class Accion implements AccionI
{
    protected Object parametros;
    protected String iD;

    @Override public String getID()             { return iD; }
}
