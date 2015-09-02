package Interfaces.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import Interfaces.EntidadesPropiedades.*;
import Interfaces.EntidadesPropiedades.Espaciales.*;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface PCI extends
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentI, Disposable,
        Vulnerable, CasterPersonalizable, Corporeo, Animable, PCStats, ModelI

{
}
