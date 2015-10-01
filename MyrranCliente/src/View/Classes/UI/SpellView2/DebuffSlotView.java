package View.Classes.UI.SpellView2;// Created by Hanto on 21/09/2015.

import DB.RSC;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

public class DebuffSlotView implements Disposable
{
    private SkillSlotI<BDebuffI> skillSlot;
    private SpellI spell;
    private ControladorSpellsI controlador;

    private Group iconoSlot = new Group();
    private Group iconoDebuff = new Group();
    private Image imagenIcono;
    private Texto nombreSkill;
    private Texto keys;
    private Texto lock;
    public Texto miniNombre;
    public SkillStatsView stats;

    private DragAndDrop dad;
    private DebuffSlotSource source;
    private DebuffSlotTarget target;

    public SpellI getSpell()                        { return spell; }
    public SkillSlotI<BDebuffI> getSkillSlot()      { return skillSlot; }
    public Group getIconoSlot()                     { return iconoSlot; }
    public Group getIconoDebuff()                   { return iconoDebuff; }
    public boolean isOcupado()                      { return skillSlot.getSkill() != null;}

    public DebuffSlotView(SpellI spell, int numSlot, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.dad =  dad;
        this.spell = spell;
        this.skillSlot = spell.getDebuffSlots().getSlot(numSlot);
        this.controlador = controlador;
        this.source = new DebuffSlotSource(this, dad);
        this.target = new DebuffSlotTarget(this, dad, controlador);

        crearView();

        dad.addSource(source);
        dad.addTarget(target);
    }

    public void dispose()
    {
        dad.removeSource(source);
        dad.removeTarget(target);
    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        if (skillSlot.getSkill() == null)   imagenIcono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        else                                imagenIcono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(spell).getIcono());

        iconoSlot.setSize(32, 32);
        iconoSlot.addListener(new DebuffSlotTooltipListener(this, controlador, dad));
        iconoSlot.addActor(imagenIcono);
        imagenIcono.setTouchable(Touchable.disabled);

        BitmapFont fuente14 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_14);
        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        lock        = new Texto(skillSlot.getKeys().toString(),
                      fuente10, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        nombreSkill = new Texto(skillSlot.getSkill() != null? skillSlot.getSkill().getNombre(): " ",
                      fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);

        miniNombre  = new Texto(skillSlot.getSkill() != null? skillSlot.getSkill().getNombre(): " ",
                      fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);

        keys        = new Texto(skillSlot.getSkill() != null? skillSlot.getSkill().getKeys().toString() : " ",
                      fuente10, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        stats       = new SkillStatsView(skillSlot.getSkill(), controlador);

        iconoSlot.addActor(lock);
        lock.setPosition(0, 16);

        iconoDebuff.addActor(nombreSkill);
        iconoDebuff.addActor(keys);
        keys.setPosition(0, 16);
    }

    // MODIFICACION VISTA:
    //------------------------------------------------------------------------------------------------------------------

    private void setLock()
    {   lock.setTexto(skillSlot.getKeys().toString()); }

    private void setNombreSkill()
    {   nombreSkill.setTexto(skillSlot.getSkill() != null ? skillSlot.getSkill().getNombre() : ""); }

    private void setMiniNombre()
    {   miniNombre.setTexto(skillSlot.getSkill() != null ? skillSlot.getSkill().getNombre(): "");}

    private void setKeys()
    {   keys.setTexto(skillSlot.getSkill() != null ? skillSlot.getSkill().getKeys().toString() : ""); }

    private void setStats()
    {   stats.setSkill(skillSlot.getSkill()); }

    private void setIcono()
    {
        iconoSlot.removeActor(imagenIcono);
        if (skillSlot.getSkill() == null)   imagenIcono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        else                                imagenIcono = new Image(RSC.skillRecursosDAO.getSkillRecursosDAO().getSpellRecursos(spell).getIcono());
        iconoSlot.addActor(imagenIcono);
        imagenIcono.toBack();
    }

    public void actualizarTodo()
    {
        setIcono();
        setLock();
        setKeys();
        setStats();
        setNombreSkill();
        setMiniNombre();
    }
}
