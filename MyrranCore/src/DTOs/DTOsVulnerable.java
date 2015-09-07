package DTOs;// Created by Hanto on 03/09/2015.

import Interfaces.EntidadesPropiedades.Misc.Vulnerable;

public class DTOsVulnerable
{
    public static class ModificarHPs
    {
        public Vulnerable vulnerable;
        public float HPs;
        public ModificarHPs() {}
        public ModificarHPs(Vulnerable vulnerable)
        {   this.vulnerable = vulnerable; }
    }

    public static class HPs
    {
        public Vulnerable vulnerable;
        public float actualHPs;
        public float maxHPs;
        public HPs() {}
        public HPs(Vulnerable vulnerable)
        {   this.vulnerable = vulnerable; }
        public HPs(float actualHPs, float maxHPs)
        {   this.actualHPs = actualHPs; this.maxHPs = maxHPs; }
    }
}
