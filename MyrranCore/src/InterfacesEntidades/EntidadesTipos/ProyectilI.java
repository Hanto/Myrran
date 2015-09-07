package InterfacesEntidades.EntidadesTipos;// Created by Hanto on 03/08/2015.

import InterfacesEntidades.EntidadesPropiedades.Consumible;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.Corporeo;
import InterfacesEntidades.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import InterfacesEntidades.EntidadesPropiedades.IDentificable;
import InterfacesEntidades.EntidadesPropiedades.ProyectilStats;
import InterfacesEntidades.EntidadesPropiedades.Steerable.SteerableAgentI;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends
        IDentificable, SteerableAgentI, Disposable,
        ProyectilStats,
        EspacialInterpolado, Consumible, Corporeo
{

}
