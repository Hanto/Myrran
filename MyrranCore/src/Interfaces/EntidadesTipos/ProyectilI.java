package Interfaces.EntidadesTipos;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesPropiedades.Misc.Consumible;
import Interfaces.EntidadesPropiedades.Espaciales.Corporeo;
import Interfaces.EntidadesPropiedades.Espaciales.EspacialInterpolado;
import Interfaces.EntidadesPropiedades.Misc.IDentificable;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.EntidadesPropiedades.Steerable.SteerableAgentI;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends
        IDentificable, SteerableAgentI, Disposable,
        ProyectilStats,
        EspacialInterpolado, Consumible, Corporeo
{

}
