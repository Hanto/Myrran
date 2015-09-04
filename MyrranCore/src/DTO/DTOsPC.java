package DTO;// Created by Hanto on 28/07/2015.

import InterfacesEntidades.EntidadesTipos.PCI;

public class DTOsPC
{
    public static class PosicionPC
    {
        public PCI pc;
        public int posX;
        public int posY;
        public PosicionPC (PCI pc) {this.pc = pc;}
    }

    public static class NumAnimacionPC
    {
        public PCI pc;
        public short numAnimacion;
        public NumAnimacionPC(PCI pc)
        {   this.pc = pc; }
    }

    public static class EliminarPC
    {
        public PCI pc;
        public EliminarPC(PCI pc)
        { this.pc = pc; }
    }

    public static class ModificarHPsPC
    {
        public PCI pc;
        public float HPs;
        public ModificarHPsPC(PCI pc)
        {   this.pc = pc; }
        public ModificarHPsPC (PCI pc, float HPs)
        {   this.pc = pc; this.HPs = HPs; }
    }

    public static class AñadirSpellPersonalizadoPC
    {
        public PCI pc;
        public String spellID;
        public AñadirSpellPersonalizadoPC(PCI pc, String spellID)
        {   this.pc = pc; this.spellID = spellID; }
    }

    public static class NumTalentosSkillPersonalizadoPC
    {
        public PCI pc;
        public String skillID;
        public int statID;
        public int valor;
        public NumTalentosSkillPersonalizadoPC(PCI pc,String skillID, int statID, int valor)
        {   this.pc = pc; this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }
}
