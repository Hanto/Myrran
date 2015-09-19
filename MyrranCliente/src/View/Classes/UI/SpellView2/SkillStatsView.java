package View.Classes.UI.SpellView2;

import DB.RSC;
import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSkillStatsI;
import Interfaces.Misc.Spell.SkillI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SkillStatsView extends Group implements Disposable, PropertyChangeListener
{
    private SkillI skill;
    private ControladorSkillStatsI controlador;
    private List<SkillStatView>listaSkillStatsView;

    private Table tabla;

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public SkillStatsView(SkillI skill, ControladorSkillStatsI controlador)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.listaSkillStatsView = new ArrayList<>(skill.getStats().getNumStats());

        this.tabla = new Table().bottom().left();
        this.tabla.defaults().padRight(2).padLeft(2).padTop(-4).padBottom(-4);
        this.addActor(tabla);

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
            tabla.add(statView.cNombre).bottom().left();
            tabla.add(statView.cValorBase).bottom().right();
            tabla.add(statView.cCasillero).center().left();
            tabla.add(statView.cTotal).bottom().right();
            tabla.add(statView.cTalentos).bottom().right();
            tabla.add(statView.cCosteTalento).bottom().right();
            tabla.add(statView.cBonoTalento).bottom().right();
            tabla.add(statView.cMaxTalentos).bottom().right();
            tabla.row();
        }
    }

    private void crearCabecera()
    {
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        tabla.add(new Texto("Name", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().left();
        tabla.add(new Texto("Base", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.add(new Texto("Level", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().center();
        tabla.add(new Texto("Total", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.add(new Texto("niv", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.add(new Texto("c", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.add(new Texto("bonus", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.add(new Texto("max", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right();
        tabla.row();
    }

    // MODIFICACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    private void setStat(int statID)
    {
        listaSkillStatsView.get(statID).setSkillStatView();
        tabla.invalidate();
    }

    //
    //------------------------------------------------------------------------------------------------------------------

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillStat)
        {   setStat(((DTOsSkill.SetSkillStat) evt.getNewValue()).statID); }
    }
}
