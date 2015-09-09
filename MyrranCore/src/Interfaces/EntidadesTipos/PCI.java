package Interfaces.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import Interfaces.EntidadesPropiedades.Propiedades.*;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import Interfaces.EntidadesPropiedades.TipoMobile.PCStats;
import com.badlogic.gdx.utils.Disposable;

public interface PCI extends
        IDentificable, SteerableAgentI, Disposable,
        PCStats,
        Vulnerable, Caster, CasterPersonalizable, Animable, Debuffeable

{
}
