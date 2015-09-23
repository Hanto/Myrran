package DTOs;

import Interfaces.Misc.Spell.SkillI;
import Model.Skills.SkillStat;
import Model.Skills.SkillSlot;

public class DTOsSkill
{
    public static class SetSkillStat
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

        public SetSkillStat() {}
        public SetSkillStat(SkillStat stat)
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

    public static class SetSkillSlot
    {
        public String skillID;

        public int slotID;
        public String spellID;
        public int[] keys;

        public SetSkillSlot() {}
        public SetSkillSlot(SkillSlot<?> spellSlot)
        {
            this.slotID = spellSlot.getID();
            this.spellID = spellSlot.getSkillID();
            this.keys = new int[spellSlot.getKeys().size()];

            for (int i=0; i< spellSlot.getKeys().size(); i++)
            {   keys[i] = spellSlot.getKeys().get(i); }
        }
    }

    public static class SetKey
    {
        public String skillID;

        public int[] keys;

        public SetKey() {}
        public SetKey(SkillI skill)
        {
            this.skillID = skill.getID();
            this.keys = new int[skill.getKeys().size()];

            for (int i=0; i< skill.getKeys().size(); i++)
            {   keys[i] = skill.getKeys().get(i); }
        }
    }

    public static class SetSpellPadre
    {
        public String skillID;

        public String skillPadreID;

        public SetSpellPadre() {}
        public SetSpellPadre(SkillI skill)
        {
            this.skillID = skill.getID();
            if (skill.getSkillPadre() != null)
                this.skillPadreID = skill.getSkillPadre().getID();
            else this.skillPadreID = null;
        }
    }
}
