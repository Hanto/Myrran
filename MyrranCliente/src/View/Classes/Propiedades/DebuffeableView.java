package View.Classes.Propiedades;// Created by Hanto on 08/09/2015.

import DTOs.DTOsDebuffeable;
import Interfaces.Misc.Spell.AuraI;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebuffeableView extends Group implements PropertyChangeListener
{
    private List<AuraView> listaAuraViews = new ArrayList<>();



    public void añadirAuraView(AuraI aura)
    {
        AuraView auraView = new AuraView(aura);
        listaAuraViews.add(auraView);
        this.addActor(auraView);
    }

    public void eliminarAuraView(int auraViewID)
    {
        AuraView auraView;
        Iterator<AuraView>iterator = listaAuraViews.iterator();
        while(iterator.hasNext())
        {
            auraView = iterator.next();
            if (auraView.getID() == auraViewID)
            {
                this.removeActor(auraView);
                iterator.remove();
            }
        }
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getNewValue() instanceof DTOsDebuffeable.AñadirAura)
        {   añadirAuraView(((DTOsDebuffeable.AñadirAura) evt.getNewValue()).aura); }

        else if (evt.getNewValue() instanceof DTOsDebuffeable.EliminarAura)
        {   eliminarAuraView(((DTOsDebuffeable.EliminarAura) evt.getNewValue()).aura.getID());}
    }
}
