package Interfaces.Misc.Skill;// Created by Hanto on 24/06/2014.

import Model.Skills.SkillsPersonalizados.SkillStat;
import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.Misc.Model.ModelI;

import java.util.Iterator;

public interface SkillI extends ModelI
{
    public void setID(String id);
    public void setNombre (String nombre);
    public void setDescripcion (String descripcion);

    public float getValorTotal(Caster Caster, int statID);
    public String getID();
    public String getNombre ();
    public String getDescripcion ();
    public SkillStat getSkillStat(int numSkillStat);
    public Iterator<SkillStat> getSkillStats();
    public int getNumSkillStats();
}
