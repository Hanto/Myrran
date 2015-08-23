package Interfaces.EntidadesTipos;// Created by Ladrim on 19/04/2014.

import Interfaces.EntidadesPropiedades.*;
import Model.AI.Steering.Huella;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayDeque;

public interface PCI extends
        IDentificable, Espacial, DinamicoSimple, Solido, Orientable, SteerableAgent, Disposable,
        Actualizable, Vulnerable, CasterPersonalizable, Corporeo, Animable, PCStats

{
        public ArrayDeque<Huella>getListaHuellas();
}
