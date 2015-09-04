package InterfacesEntidades.EntidadesTipos;// Created by Hanto on 03/08/2015.

import InterfacesEntidades.EntidadesPropiedades.*;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.*;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentI, Disposable,
        ProyectilStats, EspacialInterpolado, Consumible, Corporeo, ModelI
{

}
