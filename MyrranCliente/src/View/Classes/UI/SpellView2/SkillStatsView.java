package View.Classes.UI.SpellView2;

import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSkillStatsI;
import Interfaces.Misc.Spell.SkillI;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SkillStatsView implements Disposable, PropertyChangeListener
{
    private SkillI skill;
    private ControladorSkillStatsI controlador;
    public List<SkillStatView>listaSkillStatsView;

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillStatsView(SkillI skill, ControladorSkillStatsI controlador)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.listaSkillStatsView = new ArrayList<>();

        crearView();

        if (skill != null)
            this.skill.añadirObservador(this);
    }

    @Override public void dispose()
    {
        if (skill != null)
            this.skill.eliminarObservador(this);
    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        if (skill != null)
        {
            for (int iD = 0; iD < skill.getStats().getNumStats(); iD++)
            {   listaSkillStatsView.add(new SkillStatView(skill, iD, controlador)); }
        }
    }


    // MODIFICACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    private void actualizarStats(int statID)
    {   listaSkillStatsView.get(statID).actualizarTodo(); }

    private void actualizarStats()
    {   for (SkillStatView statView : listaSkillStatsView)
            statView.actualizarTodo();
    }

    public void setSkill(SkillI skill)
    {
        if (this.skill != null)
            this.skill.eliminarObservador(this);

        this.skill = skill;

        if (this.skill !=null)
            this.skill.añadirObservador(this);

        listaSkillStatsView.clear();
        crearView();
        actualizarStats();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillStat)
        {   actualizarStats(((DTOsSkill.SetSkillStat) evt.getNewValue()).statID); }
    }
}
