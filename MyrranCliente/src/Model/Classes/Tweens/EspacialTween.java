package Model.Classes.Tweens;// Created by Hanto on 25/07/2014.

import Interfaces.EntidadesPropiedades.Espacial;
import aurelienribon.tweenengine.TweenAccessor;

public class EspacialTween implements TweenAccessor<Espacial>
{
    public static final int POSICION = 1;

    @Override public int getValues(Espacial espacial, int tweenType, float[] returnValues)
    {
        switch (tweenType)
        {
            case POSICION:
                returnValues[0] = espacial.getX();
                returnValues[1] = espacial.getY();
                return 2;
            default:
                return 0;
        }
    }

    @Override public void setValues(Espacial espacial, int tweenType, float[] newValues)
    {
        switch (tweenType)
        {
            case POSICION: espacial.setPosition(newValues[0],newValues[1]); break;
            default: break;
        }
    }
}
