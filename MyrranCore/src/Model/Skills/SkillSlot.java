package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkillSlot<T extends SkillI> extends AbstractModel implements SkillSlotI<T>
{
    private int iD;
    private List<Integer> lock = new ArrayList<>();
    private SkillAsociadoAlSlotI skillAsociadoAlSlot;
    private T skill;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillSlot(int iD, SkillAsociadoAlSlotI skillAsociadoAlSlot)
    {
        this.iD = iD;
        this.skillAsociadoAlSlot = skillAsociadoAlSlot;
    }

    // IDENTIFICABLE:
    //------------------------------------------------------------------------------------------------------------------

    @Override public int getID()                    { return iD; }
    @Override public void setID(int iD)             { this.iD = iD; }

    // LOCKI:
    //------------------------------------------------------------------------------------------------------------------

    @Override public List<Integer> getKeys()
    {   return lock;}

    @Override public boolean abreLaCerradura(KeyI<Integer> key)
    {
        if (key == null) return true;
        else return !Collections.disjoint(key.getKeys(), lock);
    }

    @Override public void addKey(Integer key)
    {   lock.add(key);
        notificarActualizacion();
    }

    @Override public void removeKey(Integer key)
    {   lock.remove(key);
        notificarActualizacion();
    }

    // SKILLSLOT:
    //------------------------------------------------------------------------------------------------------------------

    @Override public T getSkill()
    {   return skill; }

    @Override public String getSkillID()
    {
        if (skill == null) return null;
        else return skill.getID();
    }

    @Override public void setSkill(T skill)
    {
        if (abreLaCerradura(skill))
        {
            if (this.skill != null)
                this.skill.setSkillPadre(null);

            this.skill = skill;

            if (skill != null)
                this.skill.setSkillPadre(skillAsociadoAlSlot.getSkill());

            notificarActualizacion();
        }
        else logger.warn("[{}] no coincide en el Slot {}", skill.getID(), iD);
    }

    @Override public void setKeys(SkillSlotI<T> spellSlot)
    {
        for (Integer cerradura : spellSlot.getKeys())
            lock.add(cerradura);

        notificarActualizacion();
    }

    // NOTIFICAR:
    //------------------------------------------------------------------------------------------------------------------

    //TODO no se diferencia entre Spell y Debuff
    private void notificarActualizacion()
    {
        DTOsSkill.SetSkillSlot setSkillSlot = new DTOsSkill.SetSkillSlot(this);
        notificarActualizacion("setSkillSlot", null, setSkillSlot);
    }
}
