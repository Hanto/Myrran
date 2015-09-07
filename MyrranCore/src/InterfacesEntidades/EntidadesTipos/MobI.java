package InterfacesEntidades.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.Model.ModelI;
import InterfacesEntidades.EntidadesPropiedades.Debuffeable;
import InterfacesEntidades.EntidadesPropiedades.IDentificable;
import InterfacesEntidades.EntidadesPropiedades.MobStats;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import InterfacesEntidades.EntidadesPropiedades.Vulnerable;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends
        IDentificable, SteerableAgentAutonomoI, Disposable,
        MobStats, ModelI,
        Vulnerable, Debuffeable
{

}
