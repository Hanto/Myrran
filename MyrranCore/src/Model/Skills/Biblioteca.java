package Model.Skills;


import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SpellI;
import Model.Skills.BDebuff.BDebuff;
import Model.Skills.Spell.Spell;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Biblioteca
{
    private Map<String, SpellI>listaSpellsBase = new HashMap<>();
    private Map<String, BDebuffI>listaDebuffsBase = new HashMap<>();

    public void añadirSpell(SpellI spellOriginal)
    {
        SpellI spell = new Spell(spellOriginal);

        spell.setID(calcularIDMenor(spell, listaSpellsBase));
        listaSpellsBase.put(spell.getID(), spell);
    }

    public void añadirDebuff(BDebuffI debuffOriginal)
    {
        BDebuffI debuff = new BDebuff(debuffOriginal);

        debuff.setID(calcularIDMenor(debuff, listaDebuffsBase));
        listaDebuffsBase.put(debuff.getID(), debuff);
    }

    public SkillI getSkill(String skillID)
    {
        if (listaSpellsBase.containsKey(skillID)) return listaSpellsBase.get(skillID);
        if (listaDebuffsBase.containsKey(skillID)) return listaDebuffsBase.get(skillID);
        else return null;
    }

    public Iterator<BDebuffI>getIteratorDebuffs()
    {   return listaDebuffsBase.values().iterator(); }




    private String calcularIDMenor(SkillI skill, Map<String, ?> listaSkills)
    {
        if (!listaSkills.containsKey(skill.getID())) return skill.getID();
        else
        {
            for (int iDMenor = 0; iDMenor < listaSkills.size(); iDMenor++)
            {
                if (!listaSkills.containsKey(skill.getID()+"_"+Integer.toString(iDMenor)))
                    return skill.getID()+"_"+Integer.toString(iDMenor);
            }
            return skill.getID()+"_"+listaSkills.size();
        }
    }
}
