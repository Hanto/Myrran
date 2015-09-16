package Interfaces.Misc.Spell;

import Interfaces.Misc.Observable.ModelI;

import java.util.Iterator;

public interface SkillStatsI extends ModelI
{
    public void setStats(SkillStatsI skillStats);
    public void setNumStats(int numStats);
    public int getNumStats();
    public SkillStatI getStat(int numStat);
    public Iterator<SkillStatI> getIterator();

}
