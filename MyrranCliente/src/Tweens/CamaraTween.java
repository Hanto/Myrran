package Tweens;// Created by Hanto on 15/07/2014.

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CamaraTween implements TweenAccessor<OrthographicCamera>
{
    public static final int POSICION = 1;
    public static final int ZOOM = 2;

    @Override public int getValues(OrthographicCamera camara, int tweenType, float[] returnValues)
    {
        switch (tweenType)
        {
            case POSICION:
                returnValues[0] = camara.position.x;
                returnValues[1] = camara.position.y;
                returnValues[2] = camara.position.z;
                return 3;
            case ZOOM:
                returnValues[0] = camara.zoom;
                return 1;
            default:
                return 0;
        }
    }

    @Override public void setValues(OrthographicCamera camara, int tweenType, float[] newValues)
    {
        switch (tweenType)
        {
            case POSICION:
                camara.position.x = newValues[0];
                camara.position.y = newValues[1];
                camara.position.z = newValues[2];
                break;
            case ZOOM:
                camara.zoom = newValues[0];
                break;
            default:
                break;
        }
    }
}
