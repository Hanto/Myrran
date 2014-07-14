package View.Classes.UI.BarraAcciones.AccionIcono;// Created by Hanto on 13/07/2014.

import Interfaces.UI.Acciones.AccionI;
import Interfaces.UI.Acciones.CasillaI;
import Interfaces.UI.BarraAcciones.ControladorBarraAccionI;
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

        CasillaI origenC = origen.getCasilla();
        CasillaI destinoC = casillaView.getCasilla();

        AccionI accionOrigen = origenC.getAccion();
        AccionI accionDestino = destinoC.getAccion();


        if (accionDestino == null) origenC.eliminarAccion();
        else origenC.setAccion(accionDestino);

        if (accionOrigen == null) destinoC.eliminarAccion();
        else destinoC.setAccion(accionOrigen);

        origen.actualizarApariencia();
        casillaView.actualizarApariencia();
    }
}
