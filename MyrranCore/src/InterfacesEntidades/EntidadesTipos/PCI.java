package InterfacesEntidades.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import InterfacesEntidades.EntidadesPropiedades.*;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.*;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface PCI extends
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentI, Disposable,
        Vulnerable, Caster, CasterPersonalizable, Corporeo, Animable, PCStats, ModelI

{
}
