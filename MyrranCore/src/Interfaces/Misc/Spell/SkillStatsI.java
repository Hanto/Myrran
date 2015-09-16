package Interfaces.Misc.Spell;

import java.beans.PropertyChangeListener;
import java.util.Iterator;

public interface SkillStatsI
{
    public void setStats(SkillStatsI skillStats);
    public void setNumStats(int numStats);
    public int getNumStats();
    public SkillStatI getStat(int numStat);
    public Iterator<SkillStatI> getStats();
    public void añadirObservador(PropertyChangeListener observador);
    public void eliminarObservador(PropertyChangeListener observador);
}
