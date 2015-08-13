package Interfaces.EntidadesTipos;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesPropiedades.*;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends
        IDentificable, Espacial, DinamicoSimple, Disposable, Steerable2D,
        Actualizable, ProyectilStats, EspacialInterpolado, Consumible, Corporeo
{ }
