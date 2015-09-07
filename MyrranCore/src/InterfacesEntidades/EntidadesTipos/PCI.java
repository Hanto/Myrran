package InterfacesEntidades.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import InterfacesEntidades.EntidadesPropiedades.Misc.*;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import InterfacesEntidades.EntidadesPropiedades.TipoMobile.PCStats;
import com.badlogic.gdx.utils.Disposable;

public interface PCI extends
        IDentificable, SteerableAgentI, Disposable,
        PCStats,
        Vulnerable, Caster, CasterPersonalizable, Animable, Debuffeable

{
}
