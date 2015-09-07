package Model.Classes.Skill.Spell;// Created by Hanto on 07/09/2015.

import Interfaces.Misc.Spell.TipoSpellI;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Model.Skills.Spell.Spell;

public class SpellCliente extends Spell
{
    public SpellCliente(TipoSpellI tipospell)
    {   super(tipospell); }

    @Override public void aplicarDebuffs (Caster Caster, Debuffeable target) {}
}
