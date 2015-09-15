package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.Misc.GameState.MundoI;

public interface TipoSpellI extends SkillI
{
    //METODOS:
    public void inicializarSkillStats();
    public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo);
}
