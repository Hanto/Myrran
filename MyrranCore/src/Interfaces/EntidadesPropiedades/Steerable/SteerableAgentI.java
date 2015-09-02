package Interfaces.EntidadesPropiedades.Steerable;// Created by Hanto on 13/08/2015.

import Interfaces.EntidadesPropiedades.Espaciales.*;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;

public interface SteerableAgentI extends Steerable<Vector2>,
        Espacial, Solido, Colisionable, Dinamico, Orientable, Seguible, Autonomo

{
}
