package DTO;// Created by Hanto on 24/07/2014.

public class DTOsSkillPersonalizado
{
    public static class SetNumTalentos
    {
        public String skillID;
        public int statID;
        public int valor;
        public SetNumTalentos() {}
        public SetNumTalentos(String skillID, int statID, int valor)
        {   this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }
}
