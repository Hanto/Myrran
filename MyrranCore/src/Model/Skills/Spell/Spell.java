package Model.Skills.Spell;
// @author Ivan Delgado Huerta

import DTOs.DTOsSkill;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.EntidadesPropiedades.Propiedades.CasterPersonalizable;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.GameState.MundoI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SpellI;
import Interfaces.Misc.Spell.TipoSpellI;
import Model.Skills.Skill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Spell extends Skill implements SpellI
{
    public static final int STAT_Cast = 0;

    protected TipoSpellI tipoSpell;
    private List<BDebuffI> listaDeDebuffsQueAplica = new ArrayList<>();

    @Override public void setTipoSpell(TipoSpellI tipoSpell)    { this.tipoSpell = tipoSpell; }
    @Override public String getTipoID()                         { return tipoSpell.getID(); }
    @Override public TipoSpellI getTipoSpell()                  { return tipoSpell; }
    @Override public Iterator<BDebuffI> getDebuffsQueAplica()   { return listaDeDebuffsQueAplica.iterator(); }
    @Override public int getNumDebuffsQueAplica()               { return listaDeDebuffsQueAplica.size(); }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Spell (TipoSpellI tipospell)
    {
        super(tipospell);
        this.tipoSpell = tipospell;
    }

    public Spell (SpellI spell)
    {
        super(spell);
        this.tipoSpell = spell.getTipoSpell();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirDebuff (BDebuffI debuff)
    {
        if (debuff == null) { logger.error("ERROR: debuff que añadir al Spell {} no encontrado.", id); return; }
        if (!listaDeDebuffsQueAplica.contains(debuff)) { listaDeDebuffsQueAplica.add(debuff); }
    }

    @Override public void aplicarDebuffs (Caster Caster, Debuffeable target)
    {
        for (BDebuffI debuff: listaDeDebuffsQueAplica)
        {   debuff.aplicarDebuff(Caster, target);}
    }

    public float getValorTotal(Caster Caster, int statID)
    {
        if (Caster instanceof CasterPersonalizable)
        {   return ((CasterPersonalizable) Caster).getSkillPersonalizado(id).getValorTotal(statID); }
        else return getSkillStat(statID).getValorBase();
    }

    @Override public void castear (Caster Caster, int targetX, int targetY, MundoI mundo)
    {
        if (Caster.isCasteando()) { }
        else
        {   //Marcamos al personaje como Casteando, y actualizamos su tiempo de casteo con el que marque el Spell (Stat Slot 0)
            //Caster.setTotalCastingTime(getValorTotal(Caster, STAT_Cast));
            Caster.setTotalCastingTime(getSkillStat(STAT_Cast).getValorTotal());
            tipoSpell.ejecutarCasteo(this, Caster, targetX, targetY, mundo);
        }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.setSkillStat)
        {
            ((DTOsSkill.setSkillStat) evt.getNewValue()).skillID = id;
            notificarActualizacion("setSkillStat", null, evt.getNewValue());
        }
        else if (evt.getNewValue() instanceof DTOsSkill.setSpellSlot)
        {
            ((DTOsSkill.setSpellSlot) evt.getNewValue()).spellID = id;
            notificarActualizacion("setSpellSlot", null, evt.getNewValue());
        }
    }
}
