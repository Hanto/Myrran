package Interfaces.Misc.Spell;// Created by Hanto on 09/06/2014.

import Model.Skills.SkillsPersonalizados.SkillStat;
import Interfaces.EntidadesPropiedades.Propiedades.Caster;
import Interfaces.Misc.GameState.MundoI;

import java.util.Iterator;

public interface TipoSpellI
{
    //SET
    public void setID(String id);
    public void setNombre (String nombre);
    public void setDescripcion (String descripcion);
    public void setSkillStat(SkillStat skillStat, int statID);
    public void setNumSkillStats(int numSkillStats);

    //GET
    public String getID();
    public String getNombre();
    public String getDescripcion ();
    public SkillStat getSkillStat(int statID);
    public Iterator<SkillStat> getSkillStats();
    public int getNumSkillStats();

    //METODOS:
    public void inicializarSkillStats();
    public void ejecutarCasteo(SpellI spell, Caster Caster, int targetX, int targetY, MundoI mundo);
}