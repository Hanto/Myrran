package Model.Mobiles.Propiedades;// Created by Hanto on 04/09/2015.

import InterfacesEntidades.EntidadesPropiedades.TipoMobile.MobStats;
import Interfaces.GameState.MundoI;

public class MobStatsBase implements MobStats
{
    // CADA ENTIDAD LO IMPLEMENTA A SU MANERA:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta) {}
    @Override public void actualizarIA(float delta, MundoI mundo) {}
}
