package Interfaces.Misc.Spell;// Created by Hanto on 24/06/2014.

import Interfaces.Misc.Observable.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface SkillI extends KeyI<Integer>, ModelI, Disposable
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

    public SkillStatsI stats();

    //public void setNumSkillStats(int numSkillStats);
    //public int getNumSkillStats();
    //public SkillStatI getSkillStat(int numSkillStat);
    //public Iterator<SkillStatI> getSkillStats();


    // SPELLSLOTS:
    //------------------------------------------------------------------------------------------------------------------

    public SkillSlotsI<SpellI> spellSlots();


    // KEYS:
    //------------------------------------------------------------------------------------------------------------------
}
