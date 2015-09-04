package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import InterfacesEntidades.EntidadesPropiedades.PCStats;
import Interfaces.GameState.MundoI;

public class PCStatsBase implements PCStats
{
    protected int iDProyectiles = 0;
    protected String nombre = "Player";
    protected int nivel = 1;

    public String getNombre()                                     { return nombre; }
    public int getNivel()                                         { return nivel; }
    public void setNombre(String nombre)                          { this.nombre = nombre; }
    public void setNivel(int nivel)                               { this.nivel = nivel; }

    public int getIDProyectiles()
    {
        if (iDProyectiles++ >= (Math.pow(2, 20)))
            iDProyectiles = 0;
        return iDProyectiles;
    }

    // CADA ENTIDAD LO IMPLEMENTA A SU MANERA:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void actualizarTimers(float delta) {}
    @Override public void actualizarIA(float delta, MundoI mundo) {}
}
