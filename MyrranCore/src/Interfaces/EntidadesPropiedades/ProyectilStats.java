package Interfaces.EntidadesPropiedades;// Created by Hanto on 06/08/2015.

import Interfaces.AI.SistemaAggroI;
import Interfaces.EntidadesPropiedades.Espaciales.Colisionable;
import Interfaces.Spell.SpellI;

import java.util.List;

public interface ProyectilStats
{
    public Caster getOwner();
    public SpellI getSpell();
    public float getDaño();

    public void setSpell(SpellI spell);
    public void setDaño(float daño);
    public void setOwner(Caster caster);

    public void checkColisiones(List<Colisionable>colisionables, SistemaAggroI aggro);
}
