package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.

import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.GameState.MundoI;

import java.util.Iterator;

public interface SpellI extends SkillI
{
    //SET
    public void setTipoSpell(TipoSpellI tipoSpell);

    //GET:
    public TipoSpellI getTipoSpell();
    public SkillSlotsI<BDebuffI> debuffSlots();
    public Iterator<BDebuffI> getDebuffsQueAplica();
    public int getNumDebuffsQueAplica();

    //METODOS:
    public void añadirDebuff (BDebuffI debuff);
    public void castear (Caster Caster, int targetX, int targetY, MundoI mundo);
    public void aplicarDebuffs (Caster Caster, Debuffeable target);

    //TODO CAMBIAR Y BORRAR:
    public float getValorTotal(Caster Caster, int statID);
}
