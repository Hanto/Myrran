package Interfaces.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.EntidadesPropiedades.Actualizable;
import Interfaces.EntidadesPropiedades.Espacial;
import Interfaces.EntidadesPropiedades.IDentificable;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends IDentificable, Espacial, Actualizable, Disposable, Steerable<Vector2>
{

}
