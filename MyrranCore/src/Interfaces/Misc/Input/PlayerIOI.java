package Interfaces.Misc.Input;// Created by Hanto on 10/06/2014.

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface PlayerIOI
{
    //GET:
    public int getScreenX();
    public int getScreenY();
    public int getMundoX();
    public int getMundoY();
    public boolean getIrArriba();
    public boolean getIrAbajo();
    public boolean getIrDerecha();
    public boolean getirIzquierda();
    public boolean getDisparar();
    public boolean getStartCastear();
    public boolean getStopCastear();
    public String getSpellID();
    public int getNumAnimacion();

    //SET:
    public void setScreenX(int screenX);
    public void setScreenY(int screenY);
    public void setMundoXY(int mundoX, int mundoY);
    public void setIrArriba(boolean b);
    public void setIrAbajo(boolean b);
    public void setirDerecha(boolean b);
    public void setIrIzquierda(boolean b);
    public void setDisparar(boolean b);
    public void setStartCastear(boolean b);
    public void setStopCastear(boolean b);
    public void setSpellID(String s);
    public void setNumAnimacion(int numAnimacion);
    public void coordenadasScreenAMundo(OrthographicCamera Camara);
}
