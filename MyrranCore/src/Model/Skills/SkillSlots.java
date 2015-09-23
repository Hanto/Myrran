package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SkillSlotI;
import Interfaces.Misc.Spell.SkillSlotsI;
import Interfaces.Misc.Spell.SkillAsociadoAlSlotI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkillSlots<T extends SkillI> extends AbstractModel implements SkillSlotsI<T>, PropertyChangeListener
{
    private List<SkillSlotI<T>> listaSkillSlots;
    private SkillAsociadoAlSlotI skillAsociadoAlSlot;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SkillSlots(SkillAsociadoAlSlotI skillAsociadoAlSlot)
    {   this.skillAsociadoAlSlot = skillAsociadoAlSlot; }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setSlots(SkillSlotsI<T> skillSlots)
    {
        if (skillSlots != null)
        {
            setNumSlots(skillSlots.getNumSlots());
            for (int iD = 0; iD < skillSlots.getNumSlots(); iD++)
            {   getSlot(iD).setKeys(skillSlots.getSlot(iD)); }
        }
    }

    @Override public void setNumSlots(int numSpellSlots)
    {
        if (listaSkillSlots == null) listaSkillSlots = new ArrayList<>(numSpellSlots);

        while (numSpellSlots - listaSkillSlots.size() > 0)
        {
            SkillSlotI<T> skillSlot = new SkillSlot<>(listaSkillSlots.size(), skillAsociadoAlSlot);
            listaSkillSlots.add(skillSlot);
            skillSlot.añadirObservador(this);
        }

        while (numSpellSlots - listaSkillSlots.size() < 0)
        {   listaSkillSlots.remove(listaSkillSlots.size()-1).eliminarObservador(this); }
    }

    @Override public int getNumSlots()
    {
        if (listaSkillSlots == null)
        {   logger.error("ERROR: falta definir el numero de SkillSlots en IniciacilizarSkill del skill"); return 0; }
        return listaSkillSlots.size();
    }

    @Override public SkillSlotI<T> getSlot(int numSpellSlot)
    {   return listaSkillSlots.get(numSpellSlot); }

    @Override public Iterator<SkillSlotI<T>> getIterator()
    {   return listaSkillSlots.iterator(); }


    // NOTIFICACIONES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillSlot)
        {   notificarActualizacion("SetSpellSlot", null, evt.getNewValue()); }
    }
}
