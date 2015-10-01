package View.Classes.UI.SpellView2.BibliotecaView;// Created by Hanto on 29/09/2015.

import DB.RSC;
import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.BDebuffI;
import Model.Settings;
import View.Classes.Actores.Texto;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

public class BibliotecaDebuffSlot implements Disposable
{
    private BDebuffI debuff;
    private ControladorSpellsI controlador;

    public Group iconoSlot = new Group();
    private Image imagenIcono;
    private Texto nombreSkill;
    private Texto lock;
    private DragAndDrop dad;

    public BibliotecaDebuffSlot(BDebuffI debuff, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.debuff = debuff;
        this.controlador = controlador;
        this.dad = dad;
    }


    @Override public void dispose()
    {

    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        imagenIcono = new Image(RSC.skillBaseRecursosDAO.getSkillBaseRecursosDAO().getIcono("IconoVacio"));
        iconoSlot.setSize(32, 32);

        iconoSlot.addActor(imagenIcono);
        imagenIcono.setTouchable(Touchable.disabled);

        BitmapFont fuente10 = RSC.fuenteRecursosDAO.getFuentesRecursosDAO().getFuente(Settings.FUENTE_10);

        lock        = new Texto(debuff.getKeys().toString(),
                      fuente10, Color.WHITE, Color.BLACK, Align.left, Align.bottom, 1);

        nombreSkill = new Texto(debuff.getNombre(),
                      fuente10, Color.ORANGE, Color.BLACK, Align.left, Align.bottom, 1);

        iconoSlot.addActor(lock);
        lock.setPosition(0, 16);
    }
}
