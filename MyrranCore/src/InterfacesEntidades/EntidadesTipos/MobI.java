package InterfacesEntidades.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import InterfacesEntidades.EntidadesPropiedades.Espaciales.*;
import InterfacesEntidades.EntidadesPropiedades.IDentificable;
import InterfacesEntidades.EntidadesPropiedades.MobStats;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import InterfacesEntidades.EntidadesPropiedades.Vulnerable;
import Interfaces.Model.ModelI;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentAutonomoI, Disposable,
        Vulnerable, MobStats, ModelI
{

}
