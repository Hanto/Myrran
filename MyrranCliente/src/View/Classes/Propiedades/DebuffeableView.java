package View.Classes.Propiedades;// Created by Hanto on 08/09/2015.

import DTOs.DTOsDebuffeable;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.Misc.Observable.ModelI;
import Interfaces.Misc.Spell.AuraI;
import ch.qos.logback.classic.Logger;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DebuffeableView extends Table implements PropertyChangeListener, Disposable
{
    private ModelI model;
    private List<AuraView> listaAuraViews = new ArrayList<>();

    private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    public DebuffeableView(Debuffeable debuffeable)
    {
        super.top().left();

        if (debuffeable instanceof ModelI)
        {
            model = (ModelI) debuffeable;
            model.añadirObservador(this);
        }
        else logger.error("ERROR: debuffeable no es Observable:");
    }

    @Override public void dispose()
    {   if (model != null) model.eliminarObservador(this); }

    public void añadirAuraView(AuraI aura)
    {
        AuraView auraView = new AuraView(aura);
        listaAuraViews.add(auraView);
        this.add(auraView).left().padBottom(-6);
        this.row();
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
                auraView.dispose();
                iterator.remove();
            }
        }
        crearTabla();
    }

    private void crearTabla()
    {
        this.clear();
        for (AuraView auraView : listaAuraViews)
        {
            this.add(auraView).left().padBottom(-6);
            this.row();
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
