package DAO.TipoSpell;// Created by Hanto on 17/04/2014.

import Interfaces.Misc.Spell.TipoSpellI;

public interface TipoSpellDAO
{
    public boolean añadirTipoSpell(TipoSpellI tipoSpell);
    public void salvarTipoSpell(TipoSpellI tipoSpell);
    public void eliminarTipoSpell (String tipoSpellID);
    public TipoSpellI getTipoSpell(String tipoSpellID);
}
