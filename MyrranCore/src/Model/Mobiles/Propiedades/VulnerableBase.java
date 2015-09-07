package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Misc.Vulnerable;

public class VulnerableBase implements Vulnerable
{
    protected float actualHPs=1;
    protected float maxHPs=2000;

    public float getActualHPs()                                   { return actualHPs; }
    public float getMaxHPs()                                      { return maxHPs; }
    public void setActualHPs(float HPs)                           { modificarHPs(HPs - actualHPs); }
    public void setMaxHPs(float HPs)                              { maxHPs = HPs; }

    public void modificarHPs(float HPs)
    {
        actualHPs += HPs;
        if (actualHPs > maxHPs) actualHPs = maxHPs;
        else if (actualHPs < 0) actualHPs = 0;
    }
}
