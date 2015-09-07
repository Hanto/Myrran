package View.Classes.UI.BarraAccionesView.CasillaView;// Created by Hanto on 13/07/2014.

import Interfaces.Misc.Controlador.ControladorBarraAccionI;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

public class CasillaTarget extends Target
{
    private CasillaView casillaView;
    private DragAndDrop dad;
    private ControladorBarraAccionI controlador;

    public CasillaTarget(CasillaView casillaView, DragAndDrop dad, ControladorBarraAccionI controlador)
    {
        super(casillaView.getApariencia());
        this.casillaView = casillaView;
        this.dad = dad;
        this.controlador = controlador;
    }

    @Override public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v2, int i)
    {   return true; }

    @Override public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload)
    {   super.reset(source, payload); }

    @Override public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float v, float v2, int i)
    {
        CasillaView origen = ((CasillaView) payload.getObject());
        controlador.barraAccionMoverAccion(origen.getCasilla(), casillaView.getCasilla());
    }
}
