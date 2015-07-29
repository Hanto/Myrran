package DTO;// Created by Hanto on 28/07/2015.

import Interfaces.EntidadesTipos.PCI;

public class DTOsPC
{
    public static class PosicionPC
    {
        public PCI pc;
        public int posX;
        public int posY;
        public PosicionPC () {}
        public PosicionPC (PCI pc)
        {   this.posX = (int)pc.getX(); this.posY = (int)pc.getY(); this.pc = pc; }
    }

    public static class NumAnimacionPC
    {
        public PCI pc;
        public short numAnimacion;
        public NumAnimacionPC() {}
        public NumAnimacionPC(PCI pc)
        { this.pc = pc; this.numAnimacion = (short)pc.getNumAnimacion(); }

    }

    public static class EliminarPC
    {
        public PCI pc;
        public EliminarPC(PCI pc)
        { this.pc = pc; }
    }

    public static class AñadirSpellPersonalizadoPC
    {
        public PCI pc;
        public String spellID;
        public AñadirSpellPersonalizadoPC() {}
        public AñadirSpellPersonalizadoPC(PCI pc, String spellID)
        {   this.pc = pc; this.spellID = spellID; }
    }
}
