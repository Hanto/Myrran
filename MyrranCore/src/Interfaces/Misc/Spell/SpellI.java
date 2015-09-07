package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.

import Model.Skills.SkillsPersonalizados.SkillStat;
import Interfaces.Misc.BDebuff.BDebuffI;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Skill.SkillI;

import java.util.Iterator;

public interface SpellI extends SkillI
{
    //SET
    public void setID(String id);
    public void setNombre (String nombre);
    public void setDescripcion (String descripcion);
    public void setTipoSpell(TipoSpellI tipoSpell);

    //GET:
    public String getID();
    public String getNombre ();
    public String getDescripcion ();
    public TipoSpellI getTipoSpell();
    public float getValorTotal(Caster Caster, int statID);
    public SkillStat getSkillStat(int statID);
    public Iterator<SkillStat> getSkillStats();
    public Iterator<BDebuffI> getDebuffsQueAplica();
    public int getNumSkillStats();
    public int getNumDebuffsQueAplica();
    public SkillI getSkill(String skillID);

    //METODOS:
    public void añadirDebuff (BDebuffI debuff);
    public void castear (Caster Caster, int targetX, int targetY, MundoI mundo);
    public void aplicarDebuffs (Caster Caster, Debuffeable target);
}
