package Model.Classes.Skill.Spell.TiposSpell;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesPropiedades.Caster;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesTipos.ProyectilI;
import Interfaces.GameState.MundoI;
import Interfaces.Spell.SpellI;
import Model.Classes.Mobiles.Proyectil;
import Model.Classes.Skill.Spell.TipoSpell;

public class Bolt extends TipoSpell
{
    @Override public void inicializarSkillStats()
    {
        setID(this.getClass().getSimpleName().toUpperCase());
        setNumSkillStats(4);
    }

    @Override public void ejecutarCasteo(SpellI spell, Caster caster, int targetX, int targetY, MundoI mundo)
    {
        ProyectilI proyectil = new Proyectil(mundo);
        if (caster instanceof Espacial)
        {
            proyectil.setPosition(((Espacial) caster).getX(), ((Espacial) caster).getY());
            proyectil.setDireccion(targetX, targetY);
        }
        else return;
    }
}
