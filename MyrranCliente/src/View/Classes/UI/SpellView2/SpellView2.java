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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private Texto nombre;
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
        this.tabla.defaults().padLeft(4).padRight(2);
        this.tabla.setTransform(false);
        this.addActor(tabla);

        crearNombre();
        crearIcono();
        crearFondo();
        crearStats();
        crearDebuffSlots();
        crearTabla();

        skill.añadirObservador(this);
    }

    @Override public void dispose()
    {
        skillStatsView.dispose();

        if (debuffsSlotView != null)
            debuffsSlotView.dispose();

        if (controlador != null)
            controlador.eliminarDelStage(this);
    }

    private void crearIcono()
    {
        if (skill instanceof SpellI)
            icono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(skill.getID()).getIcono());
        else icono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        icono.addListener(new VentanaMoverListener(icono, this));
        icono.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                if (button == Input.Buttons.RIGHT)
                    dispose();
                return true;
            }
        });

        icono.setPosition(-icono.getWidth(), -icono.getHeight() - 18);
        this.addActor(icono);
    }

    private void crearFondo()
    {
        fondo = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura("Casillero2"));
        fondo.setColor(1f, 1f, 1f, 0.55f);
        this.addActor(fondo);
        fondo.toBack();
    }

    private void crearNombre()
    {
        nombre = new Texto(skill.getNombre(), RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_20),
                 Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 2);
    }

    private void crearStats()
    {   skillStatsView = new SkillStatsView(skill, controlador); }

    private void crearDebuffSlots()
    {
        if (skill instanceof SpellI) debuffsSlotView = new DebuffsSlotView((SpellI)skill, controlador, new DragAndDrop());
    }

    private void crearTabla()
    {
        tabla.clear();
        tabla.add(nombre).padBottom(-2).left().row();
        crearCabecera();
        añadirStats(skillStatsView);
        if (debuffsSlotView != null)
        {
            for (DebuffSlotView debuffSlot : debuffsSlotView.listaDebuffs)
            {
                if (debuffSlot.isOcupado())
                {
                    tabla.add(debuffSlot.miniNombre).bottom().left().padTop(-1).padBottom(-4).row();
                    añadirStats(debuffSlot.stats);
                }
            }
        }
        if (debuffsSlotView != null)
        {
            for (int i=0 ; i<debuffsSlotView.listaDebuffs.size() ; i++)
            {
                this.addActor(debuffsSlotView.listaDebuffs.get(i).getIconoSlot());
                debuffsSlotView.listaDebuffs.get(i).getIconoSlot().setPosition(tabla.getMinWidth() + 2+i*32, -50); //i * -32 - 50);
                //this.addActor(debuffsSlotView.listaDebuffs.get(i).getIconoDebuff());
                //debuffsSlotView.listaDebuffs.get(i).getIconoDebuff().setPosition(tabla.getMinWidth()+2+32,i*-32-50);
            }

        }

        fondo.setBounds(0, -tabla.getMinHeight()-6, tabla.getMinWidth(), tabla.getMinHeight()-12);
    }

    private void añadirStats(SkillStatsView statsView)
    {
        for (SkillStatView statView : statsView.listaSkillStatsView)
        {
            tabla.add(statView.cNombre).bottom().left().padTop(-4).padBottom(-4);
            tabla.add(statView.cValorBase).bottom().right().padTop(-4).padBottom(-4);
            tabla.add(statView.cCasillero).center().left().padTop(-4).padBottom(-4);
            tabla.add(statView.cTotal).bottom().right().padTop(-4).padBottom(-4);
            tabla.add(statView.cTalentos).bottom().right().padTop(-4).padBottom(-4);
            tabla.add(statView.cCosteTalento).bottom().right().padTop(-4).padBottom(-4);
            tabla.add(statView.cBonoTalento).bottom().right().padTop(-4).padBottom(-4);
            tabla.add(statView.cMaxTalentos).bottom().right().padTop(-4).padBottom(-4);
            tabla.row();
        }
    }

    private void crearCabecera()
    {
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        tabla.add(new Texto("Name", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().left().padBottom(-4).padTop(-4);
        tabla.add(new Texto("Base", fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.add(new Texto("Level",fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().center().padBottom(-4).padTop(-4);
        tabla.add(new Texto("Total",fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.add(new Texto("nv",   fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.add(new Texto("c",    fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.add(new Texto("bon",  fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.add(new Texto("mx",   fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1)).bottom().right().padBottom(-4).padTop(-4);
        tabla.row();
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillSlot)
        {
            int slotID = ((DTOsSkill.SetSkillSlot) evt.getNewValue()).slotID;
            debuffsSlotView.listaDebuffs.get(slotID).actualizarTodo();
            crearTabla();
        }
    }
}
