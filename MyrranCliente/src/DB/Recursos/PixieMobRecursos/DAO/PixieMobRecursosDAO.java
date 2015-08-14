package DB.Recursos.PixieMobRecursos.DAO;// Created by Hanto on 13/08/2015.

import View.Classes.Actores.Pixie;

public interface PixieMobRecursosDAO
{
    public Pixie getPixieMob(String nombreMob);
    public void salvarPixieMob(String nombreMob);
}
