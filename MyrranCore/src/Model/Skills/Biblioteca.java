package Model.Skills;


import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SpellI;
import Model.Skills.BDebuff.BDebuff;
import Model.Skills.Spell.Spell;

import java.util.HashMap;
import java.util.Map;

public class Biblioteca
{
    private Map<String, SpellI>listaSpellsBase = new HashMap<>();
    private Map<String, BDebuffI>listaDebuffsBase = new HashMap<>();

    private Map<String, String>asignaciones = new HashMap<>();


    public void añadirSpell(SpellI spellOriginal)
    {
        SpellI spell = new Spell(spellOriginal);

        spell.setID(calcularIDMenor(spell, listaSpellsBase));
        listaSpellsBase.put(spell.getID(), spell);
        asignaciones.put(spell.getID(), "");
    }

    public void añadirDebuff(BDebuffI debuffOriginal)
    {
        BDebuffI debuff = new BDebuff(debuffOriginal);

        debuff.setID(calcularIDMenor(debuff, listaDebuffsBase));
        listaDebuffsBase.put(debuff.getID(), debuff);
        asignaciones.put(debuff.getID(), "");
    }

    public SkillI getSkill(String skillID)
    {
        if (listaSpellsBase.containsKey(skillID)) return listaSpellsBase.get(skillID);
        if (listaDebuffsBase.containsKey(skillID)) return listaDebuffsBase.get(skillID);
        else return null;
    }

    public void añadirDebuffASpell(BDebuffI debuff, SpellI spell)
    {
        if (asignaciones.get(debuff.getID()).equals(""))
        {
            spell.añadirDebuff(debuff);
            asignaciones.put(debuff.getID(), spell.getID());
        }
    }

    public void añadirDebuffASpell(String debuffID, String spellID)
    {   añadirDebuffASpell(listaDebuffsBase.get(debuffID), listaSpellsBase.get(spellID)); }






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
