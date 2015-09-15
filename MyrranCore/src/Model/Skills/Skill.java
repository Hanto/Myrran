package Model.Skills;

import Interfaces.Misc.Observable.AbstractModel;
import Interfaces.Misc.Spell.SkillI;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Skill extends AbstractModel implements SkillI, Disposable, PropertyChangeListener
{
    protected String id;
    protected String nombre;
    protected String descripcion;
    private List<SkillStat> listaSkillStats;

    //SET
    @Override public void setID(String id)                      { this.id = id; }
    @Override public void setNombre (String nombre)             { this.nombre = nombre; }
    @Override public void setDescripcion (String descripcion)   { this.descripcion = descripcion; }


    @Override public String getID()                             { return id; }
    @Override public String getNombre ()                        { return nombre; }
    @Override public String getDescripcion ()                   { return descripcion; }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public Skill() {}

    //constructor copia:
    public Skill(SkillI skill)
    {
        this.setNombre(skill.getNombre());
        this.setDescripcion(skill.getDescripcion());
        this.setNumSkillStats(skill.getNumSkillStats());

        for  (int iD = 0; iD < getNumSkillStats(); iD++)
        {  setSkillStat(new SkillStat(skill.getSkillStat(iD)), iD); }
    }

    @Override public void dispose()
    {
        for (SkillStat skillStat : listaSkillStats)
        {   skillStat.eliminarObservador(this); }
    }

    // SKILLSTATS:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setNumSkillStats(int numSkillStats)
    {
        listaSkillStats = new ArrayList<>(numSkillStats);
        for  (int i = 0; i < numSkillStats; i++)
        {   listaSkillStats.add(null); }
    }

    @Override public int getNumSkillStats()
    {   return listaSkillStats.size(); }

    @Override public void setSkillStat(SkillStat skillStat, int statID)
    {
        if (getSkillStat(statID) != null) getSkillStat(statID).eliminarObservador(this);
        listaSkillStats.set(statID, skillStat);

        skillStat.añadirObservador(this);
    }

    @Override public SkillStat getSkillStat(int numSkillStat)
    {   return listaSkillStats.get(numSkillStat); }

    @Override public Iterator<SkillStat> getSkillStats()
    {   return listaSkillStats.iterator(); }
}
