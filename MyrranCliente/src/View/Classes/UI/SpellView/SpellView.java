package View.Classes.UI.SpellView;// Created by Hanto on 19/06/2014.

import DB.RSC;
import DTO.DTOsSkillPersonalizado;
import Interfaces.EntidadesPropiedades.CasterPersonalizable;
import Interfaces.Skill.SkillPersonalizadoI;
import Interfaces.Spell.SpellPersonalizadoI;
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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

public class SpellView extends Group implements PropertyChangeListener, Disposable
{
    //Model:
    private SpellPersonalizadoI spell;
    private CasterPersonalizable caster;

    //View:
    private Image background;
    private Image icono;
    private Table tabla;
    private Texto talentosTotales;
    private Array<SkillView> listaSkills = new Array<>();

    private final int PAD = 8;
    private final int ANCHO_Descripcion = 80;
    private int textoSobresalePorArriba;

    public SpellView(String spellID, CasterPersonalizable caster)
    {
        this.caster = caster;
        this.spell = caster.getSpellPersonalizado(spellID);
        this.setTransform(false);

        background = new Image(RSC.miscRecusosDAO.getMiscRecursosDAO().cargarTextura("Casillero2"));
        background.setColor(1f,1f,1f,0.55f);
        tabla = new Table().bottom().left();
        icono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(spell.getID()).getIcono());
        icono.addListener(new VentanaMoverListener(icono, this));

        this.addActor(background);
        this.addActor(icono);
        this.addActor(tabla);

        icono.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   if (button == Input.Buttons.RIGHT) dispose(); return true; }
        });

        this.addListener(new InputListener()
        {
            @Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {   SpellView.this.toFront(); return false; }
        });

        spell.añadirObservador(this);

        construirView();
        recrearTabla();
        ajustarDimensiones();
        //tabla.debug();
    }

    private void añadirSkillView(SkillView skillView)
    {   listaSkills.add(skillView); }

    @Override public void dispose()
    {
        spell.eliminarObservador(this);

        Iterator<SkillView> iterator = listaSkills.iterator();
        SkillView skillPersonalizado;
        while (iterator.hasNext())
        {
            skillPersonalizado = iterator.next();
            skillPersonalizado.dispose();
        }
        if (this.getStage() != null) this.getStage().getRoot().removeActor(this);
    }

    public void construirView()
    {
        BitmapFont normal = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_Nombres);
        SkillView skillView;
        SkillPersonalizadoI skill;

        //SPELL:
        skill = spell.getCustomSpell();
        skillView = new SkillView(skill, caster);
        añadirSkillView(skillView);

        //DEBUFFS:
        Iterator<SkillPersonalizadoI> debuffIIterator = spell.getIteratorCustomDebuffs();
        while (debuffIIterator.hasNext())
        {
            skill = debuffIIterator.next();
            skillView = new SkillView(skill, caster);
            añadirSkillView(skillView);
        }

        //TALENTOS TOTALES:
        talentosTotales = new Texto("Rank: "+Integer.toString(spell.getCosteTotalTalentos()), normal, new Color(170/255f, 70/255f, 255/255f, 1f), Color.BLACK, Align.right, Align.center, 1);
        this.addActor(talentosTotales);
    }

    private void recrearTabla()
    {
        tabla.clear();
        tabla.padLeft(4).padRight(4).padBottom(4);
        tabla.defaults().padRight(2).padLeft(2);

        SkillView skill;
        for (int i=0; i<listaSkills.size; i++)
        {
            skill = listaSkills.get(i);

            if (i ==0) { tabla.add(skill.getNombreSkill()).padBottom(2).left(); tabla.row(); cabecera(); }
            else { tabla.add(skill.getNombreSkill()).height(skill.getNombreSkill().getHeight() - PAD).left(); tabla.row(); }

            for (int stat=0; stat<skill.getNumSkillStats(); stat++)
            {
                tabla.add(skill.getNombre(stat)).height(skill.getNombre(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .left();
                tabla.add(skill.getValorBase(stat)).height(skill.getValorBase(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.add(skill.getCasilleroTalentos(stat))
                        .top()
                        .left();
                tabla.add(skill.getValorTotal(stat)).height(skill.getValorTotal(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.add(skill.getTalentos(stat)).height(skill.getTalentos(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.add(skill.getCosteTalento(stat)).height(skill.getCosteTalento(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.add(skill.getBonoTalento(stat)).height(skill.getBonoTalento(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.add(skill.getMaxTalentos(stat)).height(skill.getMaxTalentos(stat)
                        .getHeight()-PAD)
                        .bottom()
                        .right();
                tabla.row();
            }
        }
    }

    private void ajustarDimensiones()
    {
        textoSobresalePorArriba = (int)(listaSkills.first().getNombreSkill().getHeight()/2+2);

        this.setSize(tabla.getMinWidth(), tabla.getMinHeight() - textoSobresalePorArriba);

        icono.setPosition(0 - icono.getWidth(), getHeight() - icono.getHeight());
        background.setBounds(0, 0, getWidth(), getHeight());
        talentosTotales.setPosition(getWidth()-6, getHeight());
    }

    private void cabecera()
    {
        Texto texto;
        BitmapFont fuenteMini = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente("10");

        texto = new Texto("Nombre", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).left().width(ANCHO_Descripcion > texto.getWidth() ? ANCHO_Descripcion : texto.getWidth());

        texto = new Texto("Base", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        texto = new Texto("Grados", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).center();

        texto = new Texto("Total", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        texto = new Texto("niv", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        texto = new Texto("c", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        texto = new Texto("bono", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        texto = new Texto("mx", fuenteMini, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);
        tabla.add(texto).height(texto.getHeight() - PAD).right();

        tabla.row();
    }

    private void modificarTalentosTotales()
    {
        talentosTotales.setTexto("Rank: "+Integer.toString(spell.getCosteTotalTalentos()));
        //Invalidate es lo que hace que la tabla recalcule las dimensiones de cada celda
        tabla.invalidate();
        ajustarDimensiones();

    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkillPersonalizado.SetNumTalentos)
        {   modificarTalentosTotales(); }
    }
}
