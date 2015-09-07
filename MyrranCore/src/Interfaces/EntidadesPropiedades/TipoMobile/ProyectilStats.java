package Interfaces.EntidadesPropiedades.TipoMobile;// Created by Hanto on 06/08/2015.

import Interfaces.Misc.Spell.SpellI;
import Interfaces.EntidadesPropiedades.Misc.Caster;

public interface ProyectilStats
{
    public Caster getOwner();
    public SpellI getSpell();
    public float getDaño();

    public void setSpell(SpellI spell);
    public void setDaño(float daño);
    public void setOwner(Caster caster);
}
