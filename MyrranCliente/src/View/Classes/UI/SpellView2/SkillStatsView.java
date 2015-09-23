package View.Classes.UI.SpellView2;

import DB.RSC;
import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSkillStatsI;
import Interfaces.Misc.Spell.SkillI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SkillStatsView extends Table implements Disposable, PropertyChangeListener
{
    private SkillI skill;
    private ControladorSkillStatsI controlador;
    private List<SkillStatView>listaSkillStatsView;

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillStatsView(SkillI skill, ControladorSkillStatsI controlador)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.listaSkillStatsView = new ArrayList<>(skill.getStats().getNumStats());

        this.bottom().left();
        this.defaults().padRight(2).padLeft(2).padTop(-4).padBottom(-4);
        this.setTransform(false);

        crearView();

        this.skill.añadirObservador(this);
    }

    @Override public void dispose()
    {   this.skill.eliminarObservador(this); }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        for (int iD = 0; iD < skill.getStats().getNumStats(); iD++)
        {   listaSkillStatsView.add(new SkillStatView(skill, iD, controlador)); }

        crearCabecera();

        for (SkillStatView statView : listaSkillStatsView)
        {
            this.add(statView.cNombre).bottom().expand().width(120);
            this.add(statView.cValorBase).bottom().right();
            this.add(statView.cCasillero).center().left();
            this.add(statView.cTotal).bottom().right();
            this.add(statView.cTalentos).bottom().right();
            this.add(statView.cCosteTalento).bottom().right();
            this.add(statView.cBonoTalento).bottom().right();
            this.add(statView.cMaxTalentos).bottom().right();
            this.row();
        }
    }

    private void crearCabecera()
    {
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        this.add(new Texto("Name",  fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().left();
        this.add(new Texto("Base",  fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.add(new Texto("Level", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().center();
        this.add(new Texto("Total", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.add(new Texto("nv",    fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.add(new Texto("c",     fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.add(new Texto("bon",   fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.add(new Texto("mx",    fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        this.row();
    }

    // MODIFICACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    private void setStat(int statID)
    {
        listaSkillStatsView.get(statID).setSkillStatView();
        this.invalidate();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillStat)
        {   setStat(((DTOsSkill.SetSkillStat) evt.getNewValue()).statID); }
    }
}
