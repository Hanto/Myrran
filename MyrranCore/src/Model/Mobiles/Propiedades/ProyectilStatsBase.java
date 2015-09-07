package Model.Mobiles.Propiedades;// Created by Hanto on 04/09/2015.

import Interfaces.EntidadesPropiedades.Misc.Caster;
import Interfaces.EntidadesPropiedades.TipoMobile.ProyectilStats;
import Interfaces.Misc.Spell.SpellI;

public class ProyectilStatsBase implements ProyectilStats
{
    protected Caster owner;
    protected SpellI spell;
    protected float daño;

    @Override public Caster getOwner()              { return owner; }
    @Override public SpellI getSpell()              { return spell; }
    @Override public float getDaño()                { return daño; }
    @Override public void setSpell(SpellI spell)    { this.spell = spell; }
    @Override public void setDaño(float daño)       { this.daño = daño; }
    @Override public void setOwner(Caster caster)   { this.owner = caster; }
}
