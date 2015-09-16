package Model.Skills;

import Interfaces.Misc.Spell.SkillStatI;
import Interfaces.Misc.Spell.SkillStatsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkillStats implements SkillStatsI
{
    private List<SkillStatI> listaSkillStats;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setStats(SkillStatsI skillStats)
    {
        if (listaSkillStats != null) { logger.error("ERROR: skillStats ya inicializados"); return; }

        setNumStats(skillStats.getNumStats());
        for (int iD = 0; iD < skillStats.getNumStats(); iD++)
        {   getStat(iD).setFullStats(skillStats.getStat(iD)); }
    }

    @Override public void setNumStats(int numStats)
    {
        if (listaSkillStats != null) { logger.error("ERROR: skillStats ya inicializados"); return; }

        listaSkillStats = new ArrayList<>(numStats);
        for  (int i = 0; i < numStats; i++)
        {   listaSkillStats.add(new SkillStat()); }
    }

    @Override public int getNumStats()
    {
        if (listaSkillStats == null)
        {   logger.error("ERROR: falta definir el numero de SkillStats en IniciacilizarSkill del skill"); return 0; }
        return listaSkillStats.size();
    }

    @Override public SkillStatI getStat(int numStat)
    {   return listaSkillStats.get(numStat); }

    @Override public Iterator<SkillStatI> getStats()
    {   return listaSkillStats.iterator(); }

    // MODEL:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void añadirObservador(PropertyChangeListener observador)
    {
        for (SkillStatI skillStat : listaSkillStats)
        {   skillStat.añadirObservador(observador); }
    }

    @Override public void eliminarObservador(PropertyChangeListener observador)
    {
        for (SkillStatI skillStat : listaSkillStats)
        {   skillStat.eliminarObservador(observador); }
    }
}
