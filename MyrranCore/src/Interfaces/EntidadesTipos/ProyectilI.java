package Interfaces.EntidadesTipos;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesPropiedades.Propiedades.Consumible;
import Interfaces.EntidadesPropiedades.Espaciales.Corporeo;
import Interfaces.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import Interfaces.EntidadesPropiedades.Propiedades.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends
        IDentificable, SteerableAgentI, Disposable,
        ProyectilStats,
        EspacialInterpolado, Consumible, Corporeo
{

}
