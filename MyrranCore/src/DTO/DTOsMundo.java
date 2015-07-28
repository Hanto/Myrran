package DTO;// Created by Hanto on 24/07/2014.

import Interfaces.EntidadesTipos.PCI;

public class DTOsMundo
{
    //Mundo (MundoView)
    public static class AñadirPC
    {
        public PCI pc;
        public AñadirPC() {}
        public AñadirPC(PCI pc)
        {   this.pc = pc; }
    }
}
