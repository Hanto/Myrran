package Interfaces.Misc.Spell;// Created by Hanto on 24/06/2014.

import Interfaces.Misc.Observable.ModelI;
import Model.Skills.SkillStat;

import java.util.Iterator;

public interface SkillI extends ModelI
{
    public void setID(String id);
    public void setNombre (String nombre);
    public void setDescripcion (String descripcion);
    public void setNumSkillStats(int numSkillStats);
    public void setSkillStat(SkillStat skillStat, int statID);

    public String getID();
    public String getTipoID();
    public String getNombre ();
    public String getDescripcion ();
    public SkillStat getSkillStat(int numSkillStat);
    public Iterator<SkillStat> getSkillStats();
    public int getNumSkillStats();
}
