package InterfacesEntidades.EntidadesTipos;

import Interfaces.Model.ModelI;
import InterfacesEntidades.EntidadesPropiedades.*;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.*;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import com.badlogic.gdx.utils.Disposable;

public interface PCSI extends PCI,
        IDentificable, Espacial, Solido, Colisionable, Dinamico, Orientable, SteerableAgentI, Disposable,
        Vulnerable, CasterS, CasterPersonalizable, Corporeo, Debuffeable, Animable, PCStats, ModelI
{
}
