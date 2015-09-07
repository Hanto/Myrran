package Interfaces.EntidadesPropiedades.Espaciales;// Created by Hanto on 21/07/2014.

import Interfaces.Misc.GameState.MundoI;
import com.badlogic.gdx.math.Vector2;

public interface Dinamico extends Espacial
{
    //GET:
    public Vector2 getVelocidad();
    public float getVelocidadMax();
    public float getAceleracionMax();
    public float getVelocidadMod();

    public float getVelocidadAngular();
    public float getVelocidadAngularMax();
    public float getAceleracionAngularMax();

    //SET:
    public void setVelocidadMax(float velocidadMax);
    public void setAceleracionMax(float aceleracionMax);
    public void setVelocidadMod(float velocidadMod);

    public void setVelocidadAngular(float velocidadAngular);
    public void setVelocidadAngularMax(float velocidadAngularMax);
    public void setAceleracionAngularMax(float aceleracionAngularMax);

    public void setDireccion(float x, float y);
    public void setDireccion(float grados);

    //UPDATE:
    public void actualizarFisica(float delta, MundoI mundo);
}
