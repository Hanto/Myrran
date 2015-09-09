package Interfaces.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.Misc.Observable.ModelI;
import Interfaces.EntidadesPropiedades.Propiedades.Debuffeable;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.MobStats;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import Interfaces.EntidadesPropiedades.Propiedades.Vulnerable;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends
        IDentificable, SteerableAgentAutonomoI, Disposable,
        MobStats, ModelI,
        Vulnerable, Debuffeable
{

}
