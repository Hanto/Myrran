package View.Classes.UI.BarraAcciones.CasillaView;// Created by Hanto on 14/07/2014.

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;

public class CasillaSource extends Source
{
    private CasillaView casillaView;
    private DragAndDrop dad;

    public CasillaSource(CasillaView casillaView, DragAndDrop dad)
    {
        super(casillaView.getApariencia());
        this.casillaView = casillaView;
        this.dad = dad;
    }

    @Override public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer)
    {
        if (casillaView.tieneDatos())
        {
            DragAndDrop.Payload payload = new DragAndDrop.Payload();
            Actor dragActor = casillaView.getDragActor();
            dad.setDragActorPosition(-dragActor.getWidth() / 2, dragActor.getHeight() / 2);
            payload.setDragActor(dragActor);
            payload.setObject(casillaView);
            return payload;
        }
        return null;
    }
}
