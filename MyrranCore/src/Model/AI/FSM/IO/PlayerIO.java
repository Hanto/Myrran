package Model.AI.FSM.IO;// Created by Hanto on 11/04/2014.

import Interfaces.Misc.Input.PlayerIOI;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class PlayerIO implements PlayerIOI
{
    public Vector3 coordenadasMundo = new Vector3();

    public int screenX;
    public int screenY;
    public int mundoX;
    public int mundoY;
    public boolean irArriba = false;
    public boolean irAbajo = false;
    public boolean irDerecha = false;
    public boolean irIzquierda = false;
    public boolean startCastear = false;
    public boolean stopCastear = false;
    public String spellID = null;
    public int numAnimacion = 5;
    public boolean disparar = false;

    @Override public void setScreenX(int screenX)           { this.screenX = screenX; }
    @Override public void setScreenY(int screenY)           { this.screenY = screenY; }
    @Override public void setMundoXY(int mundoX, int mundoY){ this.mundoX = mundoX; this.mundoY = mundoY; }
    @Override public void setIrArriba(boolean b)            { irArriba = b; }
    @Override public void setIrAbajo(boolean b)             { irAbajo = b; }
    @Override public void setirDerecha(boolean b)           { irDerecha = b; }
    @Override public void setIrIzquierda(boolean b)         { irIzquierda = b; }
    @Override public void setDisparar(boolean b)            { disparar = b; }
    @Override public void setStartCastear(boolean b)        { startCastear = b; }
    @Override public void setStopCastear(boolean b)         { stopCastear = b; }
    @Override public void setSpellID(String s)              { spellID = s; }
    @Override public void setNumAnimacion(int numAnimacion) { this.numAnimacion = numAnimacion; }

    @Override public int getScreenX()                       { return screenX; }
    @Override public int getScreenY()                       { return screenY; }
    @Override public int getMundoX()                        { return mundoX; }
    @Override public int getMundoY()                        { return mundoY; }
    @Override public boolean getIrArriba()                  { return irArriba; }
    @Override public boolean getIrAbajo()                   { return irAbajo; }
    @Override public boolean getIrDerecha()                 { return irDerecha; }
    @Override public boolean getirIzquierda()               { return irIzquierda; }
    @Override public boolean getDisparar()                  { return disparar; }
    @Override public boolean getStartCastear()              { return startCastear; }
    @Override public boolean getStopCastear()               { return stopCastear; }
    @Override public String getSpellID()                    { return spellID; }
    @Override public int getNumAnimacion()                  { return numAnimacion; }

    public PlayerIO() {}

    public void getPlayerIO(PlayerIO playerIO)
    {
        this.screenX = playerIO.getScreenX();
        this.screenY = playerIO.getScreenY();
        this.irArriba = playerIO.getIrArriba();
        this.irAbajo = playerIO.getIrAbajo();
        this.irDerecha = playerIO.getIrDerecha();
        this.irIzquierda = playerIO.getirIzquierda();
        this.startCastear = playerIO.getStartCastear();
        this.stopCastear = playerIO.getStopCastear();
        this.spellID = playerIO.getSpellID();
        this.numAnimacion = playerIO.getNumAnimacion();
        this.disparar = playerIO.getDisparar();
    }

    @Override public void coordenadasScreenAMundo(OrthographicCamera camara)
    {
        if (startCastear == true || stopCastear == true)
        {
            coordenadasMundo.set(screenX, screenY, 0);
            camara.unproject(coordenadasMundo);
            mundoX = (int) coordenadasMundo.x;
            mundoY = (int) coordenadasMundo.y;
        }
    }
}
