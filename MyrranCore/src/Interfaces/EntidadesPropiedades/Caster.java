package Interfaces.EntidadesPropiedades;// Created by Hanto on 17/04/2014.

import Interfaces.Model.ModelI;

public interface Caster extends ModelI, IDentificable
{
    //GET:
    public boolean isCasteando();
    public float getActualCastingTime();
    public float getTotalCastingTime();
    public String getSpellIDSeleccionado();
    public Object getParametrosSpell();

    //SET:
    public void setCastear(boolean castear, int screenX, int screenY);
    public void setTotalCastingTime(float castingTime);
    public void setSpellIDSeleccionado(String spellID);
    public void setParametrosSpell (Object parametrosDTO);
}
