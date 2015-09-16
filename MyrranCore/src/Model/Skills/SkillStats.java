package Model.Skills;

import DTOs.DTOsSkill;
import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillStatI;
import Interfaces.Misc.Spell.SkillStatsI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SkillStats extends AbstractModel implements SkillStatsI, PropertyChangeListener
{
    private List<SkillStatI> listaSkillStats;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setStats(SkillStatsI skillStats)
    {
        setNumStats(skillStats.getNumStats());
        for (int iD = 0; iD < skillStats.getNumStats(); iD++)
        {   getStat(iD).setFullStats(skillStats.getStat(iD)); }
    }




    @Override public void setNumStats(int numStats)
    {
        if (listaSkillStats == null) listaSkillStats = new ArrayList<>(numStats);

        while ( numStats - listaSkillStats.size() > 0)
        {
            SkillStatI stats = new SkillStat();
            listaSkillStats.add(stats);
            stats.añadirObservador(this);
        }

        while ( numStats - listaSkillStats.size() < 0)
        {   listaSkillStats.remove(listaSkillStats.size()-1).eliminarObservador(this); }
    }

    @Override public int getNumStats()
    {
        if (listaSkillStats == null)
        {   logger.error("ERROR: falta definir el numero de SkillStats en IniciacilizarSkill del skill"); return 0; }
        return listaSkillStats.size();
    }

    @Override public SkillStatI getStat(int numStat)
    {   return listaSkillStats.get(numStat); }

    @Override public Iterator<SkillStatI> getIterator()
    {   return listaSkillStats.iterator(); }


    // NOTIFICACIONES:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillStat)
        {   notificarActualizacion("SetSkillStat", null, evt.getNewValue()); }
    }
}
