package Interfaces.EntidadesTipos;// Created by Ladrim on 18/04/2014.

import Interfaces.Misc.Model.ModelI;
import Interfaces.EntidadesPropiedades.Misc.Debuffeable;
import Interfaces.EntidadesPropiedades.Misc.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.MobStats;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentAutonomoI;
import Interfaces.EntidadesPropiedades.Misc.Vulnerable;
import com.badlogic.gdx.utils.Disposable;

public interface MobI extends
        IDentificable, SteerableAgentAutonomoI, Disposable,
        MobStats, ModelI,
        Vulnerable, Debuffeable
{

}
