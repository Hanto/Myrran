package DTOs;// Created by Hanto on 06/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Misc.CasterPersonalizable;

public class DTOsCasterPersonalizable
{
    public static class AñadirSpellPersonalizado
    {
        public CasterPersonalizable caster;
        public String spellID;
        public AñadirSpellPersonalizado(CasterPersonalizable caster, String spellID)
        {   this.caster = caster; this.spellID = spellID; }
    }

    public static class SetNumTalentosSkillPersonalizado
    {
        public CasterPersonalizable caster;
        public String skillID;
        public int statID;
        public int valor;
        public SetNumTalentosSkillPersonalizado(CasterPersonalizable caster, String skillID, int statID, int valor)
        {   this.caster = caster; this.skillID = skillID; this.statID = statID; this.valor = valor; }
    }
}
