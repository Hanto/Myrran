package Interfaces.Misc.Spell;// Created by Hanto on 24/06/2014.

import Interfaces.Misc.Observable.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface SkillI extends KeyI<Integer>, SkillAsociadoAlSlotI, ModelI, Disposable
{
    public void setID(String id);
    public void setNombre (String nombre);
    public void setDescripcion (String descripcion);
    public String getID();
    public String getTipoID();
    public String getNombre ();
    public String getDescripcion ();

    // SKILLSTATS:
    //------------------------------------------------------------------------------------------------------------------

    public SkillStatsI getStats();

    // SPELLSLOTS:
    //------------------------------------------------------------------------------------------------------------------

    public SkillSlotsI<SpellI> getSpellSlots();
}
