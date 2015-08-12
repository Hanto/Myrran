package Interfaces.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import Interfaces.EntidadesPropiedades.*;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public interface PCI extends Espacial, Dinamico, Animable, PCStats, Vulnerable, Disposable, Actualizable,
                             CasterPersonalizable, Corporeo, IDentificable, Steerable<Vector2>
{

}
