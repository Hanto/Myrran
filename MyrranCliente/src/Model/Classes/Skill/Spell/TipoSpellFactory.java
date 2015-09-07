package Model.Classes.Skill.Spell;// Created by Ladrim on 20/04/2014.

import Model.Classes.Skill.Spell.TiposSpell.Bolt;
import Model.Classes.Skill.Spell.TiposSpell.EditarTerreno;
import Model.Classes.Skill.Spell.TiposSpell.Heal;
import Model.Skills.Spell.TipoSpell;

public enum TipoSpellFactory
{
    EDITARTERRENO("Editar Terreno")
    {
        @Override public TipoSpell nuevo()
        {   return new EditarTerreno(); }
    },
    HEAL("Heal")
    {
        @Override public TipoSpell nuevo()
        {   return new Heal(); }
    },
    BOLT("Bolt")
    {
        @Override public TipoSpell nuevo()
        {   return new Bolt(); }
    };

    public abstract TipoSpell nuevo();
    private TipoSpellFactory(String nombre) {}
}
