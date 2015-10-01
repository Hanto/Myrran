package View.Classes.UI.SpellView2;// Created by Hanto on 22/09/2015.

import Interfaces.Misc.Controlador.ControladorSpellsI;
import Interfaces.Misc.Spell.SpellI;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebuffsSlotView implements Disposable
{
    private SpellI skill;
    private ControladorSpellsI controlador;
    private DragAndDrop dad;
    public List<DebuffSlotView> listaDebuffs = new ArrayList<>();

    // CONSTRUCTOR:
    //------------------------------------------------------------------------------------------------------------------

    public DebuffsSlotView(SpellI skill, ControladorSpellsI controlador, DragAndDrop dad)
    {
        this.skill = skill;
        this.controlador = controlador;
        this.dad = dad;

        crearView();
    }

    public void dispose()
    {
        for (DebuffSlotView debuffSlot : listaDebuffs)
        {   debuffSlot.dispose(); }
    }

    // CREACION:
    //------------------------------------------------------------------------------------------------------------------

    private void crearView()
    {
        for (int iD = 0; iD < skill.getDebuffSlots().getNumSlots(); iD++)
        {   listaDebuffs.add(new DebuffSlotView(skill, iD, controlador, dad)); }
    }

    // CONSULTA:
    //------------------------------------------------------------------------------------------------------------------

    public DebuffSlotView getDebuffSlot(int iDDebuffSlot)   { return listaDebuffs.get(iDDebuffSlot); }
    public int getNumDebuffSlots()                          { return listaDebuffs.size(); }
    public Iterator<DebuffSlotView>getIterator()            { return listaDebuffs.iterator(); }
}
