package View.Classes.UI.SpellView2;// Created by Hanto on 21/09/2015.

import DB.RSC;
import DTOs.DTOsSkill;
import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.BDebuffI;
import Interfaces.Misc.Spell.SkillSlotI;
import Interfaces.Misc.Spell.SpellI;
import Model.Settings;
import View.Classes.Actores.Texto;
import View.Classes.UI.SpellView2.SkillSlotView.DebuffSlotSource;
import View.Classes.UI.SpellView2.SkillSlotView.DebuffSlotTarget;
import View.Classes.UI.SpellView2.SkillSlotView.DebuffSlotTooltipListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DebuffSlotView implements PropertyChangeListener, Disposable
{
    private SkillSlotI<BDebuffI> skillSlot;
    private SpellI spell;
    private ControladorSpellsI controlador;

    private Group icono = new Group();
    private Image imagenIcono;
    private Texto nombreSkill;
    private Texto keys;
    private Texto lock;
    public SkillStatsView stats;

    public Container<Texto> cNombre;

    private DragAndDrop dad;
    private DebuffSlotSource source;
    private DebuffSlotTarget target;

    public SpellI getSpell()                        { return spell; }
    public SkillSlotI<BDebuffI> getSkillSlot()      { return skillSlot; }
    public Group getIcono()                         { return icono; }

    public DebuffSlotView(SpellI spell, int numSlot, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.dad =  dad;
        this.spell = spell;
        this.skillSlot = spell.getDebuffSlots().getSlot(numSlot);
        this.controlador = controlador;
        this.source = new DebuffSlotSource(this, dad);
        this.target = new DebuffSlotTarget(this, dad, controlador);

        crearView();

        skillSlot.añadirObservador(this);
        dad.addSource(source);
        dad.addTarget(target);
    }

    public void dispose()
    {
        skillSlot.eliminarObservador(this);
        dad.removeSource(source);
        dad.removeTarget(target);
    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        if (skillSlot.getSkill() == null)   imagenIcono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        else                                imagenIcono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(spell).getIcono());

        icono.setSize(32, 32);
        dad.addSource(new DebuffSlotSource(this, dad));
        dad.addTarget(new DebuffSlotTarget(this, dad, null));


        icono.addActor(imagenIcono);
        imagenIcono.setTouchable(Touchable.disabled);
        icono.addListener(new DebuffSlotTooltipListener(this, null));

        BitmapFont fuente14 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_14);
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        lock        = new Texto(skillSlot.getKeys().toString(),
                      fuente10, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        nombreSkill = new Texto(skillSlot.getSkill() != null? skillSlot.getSkill().getNombre(): " ",
                      fuente14, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);

        keys        = new Texto(skillSlot.getSkill() != null? skillSlot.getSkill().getKeys().toString() : " ",
                      fuente10, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        cNombre = new Container<>(nombreSkill);

        icono.addActor(lock);
        icono.addActor(nombreSkill);
        icono.addActor(keys);
        lock.setPosition(0, 16);
        nombreSkill.setPosition(34, 0);
        keys.setPosition(32, 16);

        setStats();
    }

    private void setLock()
    {   lock.setTexto(skillSlot.getKeys().toString()); }

    private void setNombreSkill()
    {   nombreSkill.setTexto(skillSlot.getSkill() != null ? skillSlot.getSkill().getNombre() : ""); }

    private void setKeys()
    {   keys.setTexto(skillSlot.getSkill() != null ? skillSlot.getSkill().getKeys().toString() : ""); }

    private void setStats()
    {
        if (stats != null) stats.dispose();
        if (skillSlot.getSkill() != null)   stats = new SkillStatsView(skillSlot.getSkill(), controlador);
        else                                stats = null;
    }

    private void setIcono()
    {
        icono.removeActor(imagenIcono);
        if (skillSlot.getSkill() == null)   imagenIcono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        else                                imagenIcono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(spell).getIcono());
        icono.addActor(imagenIcono);
        imagenIcono.toBack();
    }

    public void setDebuffSlowView()
    {
        setIcono();
        setLock();
        setKeys();
        setStats();
        setNombreSkill();
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsSkill.SetSkillSlot)
        {
            setDebuffSlowView();
        }

    }
}
