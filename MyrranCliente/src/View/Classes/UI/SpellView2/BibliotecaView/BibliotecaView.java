package View.Classes.UI.SpellView2.BibliotecaView;// Created by Hanto on 29/09/2015.

import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.BDebuffI;
import Model.Skills.Biblioteca;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.Iterator;

public class BibliotecaView extends Group
{
    private Biblioteca biblioteca;
    private ControladorSpellsI controlador;

    private DragAndDrop dad;
    private Table tablaDebuffs;
    private ScrollPane scrollDebuffs;

    public BibliotecaView(Biblioteca biblioteca, ControladorSpellsI controlador)
    {
        this.biblioteca = biblioteca;
        this.controlador = controlador;

        this.dad = new DragAndDrop();
        this.tablaDebuffs = new Table().top().left();
        this.scrollDebuffs = new ScrollPane(tablaDebuffs);
        this.addActor(scrollDebuffs);

        crearView();
    }

    private void crearView()
    {
        Iterator<BDebuffI>iterator = biblioteca.getIteratorDebuffs();
        BDebuffI debuff;
        while (iterator.hasNext())
        {
            debuff = iterator.next();
            if (debuff.getSkillPadre() == null)
            {
                BibliotecaDebuffSlot debuffSlot = new BibliotecaDebuffSlot(debuff, controlador, dad);

                tablaDebuffs.add(debuffSlot.iconoSlot).bottom().left();
                tablaDebuffs.row();
            }
        }
    }
}
