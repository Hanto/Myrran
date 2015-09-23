package View.Classes.UI.SpellView2;


import DB.RSC;
import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.SkillI;
import Interfaces.Misc.Spell.SpellI;
import Model.Settings;
import View.Classes.Actores.Texto;
import View.Classes.UI.Ventana.VentanaMoverListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SpellView2 extends Group implements Disposable, PropertyChangeListener
{
    private SkillI skill;
    private ControladorSpellsI controlador;

    private Table tabla;
    private Image icono;
    private Image fondo;
    private SkillStatsView skillStatsView;
    private DebuffsSlotView debuffsSlotView;


    public SpellView2(SkillI skill, ControladorSpellsI controlador)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.tabla = new Table().top().left();
        this.tabla.defaults().padTop(1).padBottom(2).padLeft(3).padRight(2);
        this.addActor(tabla);

        crearIcono();
        crearFondo();
        crearTabla();

        skill.añadirObservador(this);
    }

    @Override public void dispose()
    {
        skillStatsView.dispose();


        if (controlador != null)
            controlador.eliminarDelStage(this);
    }

    private void crearIcono()
    {
        if (skill instanceof SpellI)
            icono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(skill.getID()).getIcono());
        else icono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        icono.addListener(new VentanaMoverListener(icono, this));
        icono.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.RIGHT)
                    dispose();
                return true;
            }
        });

        icono.setPosition(-icono.getWidth(), -icono.getHeight());
        this.addActor(icono);
    }

    private void crearFondo()
    {
        fondo = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura("Casillero2"));
        fondo.setColor(1f, 1f, 1f, 0.55f);
        this.addActor(fondo);
        fondo.toBack();
    }

    private void crearTabla()
    {

        tabla.add(new Texto(skill.getNombre(), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_20),
                Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 2)).padBottom(-2).left().row();
        skillStatsView = new SkillStatsView(skill, controlador);
        tabla.add(skillStatsView).left();
        tabla.row();

        DragAndDrop dad = new DragAndDrop();

        if (skill instanceof SpellI) debuffsSlotView = new DebuffsSlotView((SpellI)skill, controlador, dad);

        if (debuffsSlotView != null)
        {
            for (int i=0 ; i<debuffsSlotView.listaDebuffs.size() ; i++)
            {
                this.addActor(debuffsSlotView.listaDebuffs.get(i).getIcono());
                debuffsSlotView.listaDebuffs.get(i).getIcono().setPosition(tabla.getMinWidth()+2,i*-32-50);
            }


            //tabla.add(debuffsSlotView).left().row();
        }

        fondo.setBounds(0, -tabla.getMinHeight(), tabla.getMinWidth(), tabla.getMinHeight() - 18);
    }

    private void recrearTabla()
    {
        tabla.clear();
        tabla.add(new Texto(skill.getNombre(), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_20),
                Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 2)).padBottom(-2).left().row();
        tabla.add(skillStatsView).left();
        tabla.row();
        if (debuffsSlotView != null) tabla.add(debuffsSlotView).left().row();
        fondo.setBounds(0, -tabla.getMinHeight(), tabla.getMinWidth(), tabla.getMinHeight() - 18);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillSlot)
        {
            recrearTabla();
        }

    }
}
