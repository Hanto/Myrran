package Interfaces.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.EntidadesPropiedades.*;
import Interfaces.EntidadesPropiedades.Espaciales.*;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentI, Disposable,
        Vulnerable, MobStats, ModelI
{

}
