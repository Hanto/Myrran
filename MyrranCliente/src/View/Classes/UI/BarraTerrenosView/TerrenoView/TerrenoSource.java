package View.Classes.UI.BarraTerrenosView.TerrenoView;// Created by Hanto on 13/05/2014.

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import static com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import static com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;

public class TerrenoSource extends Source
{
    private TerrenoView icono;
    private DragAndDrop dad;

    public TerrenoSource(TerrenoView icono, DragAndDrop dad)
    {
        super(icono.getApariencia());
        this.icono = icono;
        this.dad = dad;
    }

    @Override public Payload dragStart(InputEvent event, float x, float y, int pointer)
    {
        if (icono.tieneDatos())
        {
            Payload payload = new DragAndDrop.Payload();
            Actor dragActor = icono.getDragActor();
            dad.setDragActorPosition(-dragActor.getWidth() / 2, dragActor.getHeight() / 2);
            payload.setDragActor(dragActor);
            payload.setObject(icono);
            return payload;
        }
        return null;
    }
}
