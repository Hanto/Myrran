package DTOs;

import Model.Skills.SkillStat;

public class DTOsSkill
{
    public static class setSkillStat
    {
        public String skillID;

        public int statID;
        public String nombre;
        public float valorBase;
        public boolean isMejorable;
        public int talentosMaximos;
        public int costeTalento;
        public float bonoTalento;

        public int numTalentos;
        public float bonosPorObjetos;

        public setSkillStat() {}
        public setSkillStat(SkillStat stat)
        {
            this.statID = stat.getID();
            this.nombre = stat.getNombre();
            this.valorBase = stat.getValorBase();
            this.isMejorable = stat.getisMejorable();
            this.talentosMaximos = stat.getTalentosMaximos();
            this.costeTalento = stat.getCosteTalento();
            this.bonoTalento = stat.getBonoTalento();
            this.numTalentos = stat.getNumTalentos();
            this.bonosPorObjetos = stat.getBonosPorObjetos();
        }
    }
}
