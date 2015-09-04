package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import InterfacesEntidades.EntidadesPropiedades.IDentificable;

public class IdentificableBase implements IDentificable
{
    protected int iD;

    @Override public int getID()                        { return iD; }
    @Override public void setID(int iD)                 { this.iD = iD; }
}
