package Interfaces.EntidadesTipos;// Created by Hanto on 03/08/2015.

import Interfaces.EntidadesPropiedades.*;
import com.badlogic.gdx.utils.Disposable;

public interface ProyectilI extends IDentificable, ProyectilStats, Espacial, EspacialInterpolado, Dinamico, Consumible,
        Corporeo, Actualizable, Disposable
{ }
