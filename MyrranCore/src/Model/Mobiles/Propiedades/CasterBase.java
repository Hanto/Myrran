package Model.Mobiles.Propiedades;// Created by Hanto on 03/09/2015.

import InterfacesEntidades.EntidadesPropiedades.Caster;

public class CasterBase implements Caster
{
    protected float actualCastingTime = 0.0f;
    protected float totalCastingTime = 0.0f;
    protected String spellIDSeleccionado = null;
    protected Object parametrosSpell;


    public float getActualCastingTime()                           { return actualCastingTime; }
    public float getTotalCastingTime()                            { return totalCastingTime; }
    public String getSpellIDSeleccionado()                        { return spellIDSeleccionado; }
    public Object getParametrosSpell()                            { return parametrosSpell; }


    public void setActualCastingTime(float castingTime)           { actualCastingTime = castingTime; }
    public void setSpellIDSeleccionado(String spellID)            { spellIDSeleccionado = spellID; }
    public void setParametrosSpell(Object parametros)             { parametrosSpell = parametros; }

    public boolean isCasteando()
    {
        if (actualCastingTime >0) return true;
        else return false;
    }

    public void setTotalCastingTime(float castingTime)
    {
        actualCastingTime = 0.01f;
        totalCastingTime = castingTime;
    }

    public void actualizarCastingTime(float delta)
    {
        if (isCasteando())
        {
            actualCastingTime += delta;
            if (actualCastingTime >= totalCastingTime)
            {
                actualCastingTime = 0f;
                totalCastingTime = 0f;
            }
        }
    }

    // CADA ENTIDAD LO IMPLEMENTA A SU MANERA:
    //------------------------------------------------------------------------------------------------------------------

    @Override public void setCastear(boolean castear, int screenX, int screenY) {}
    @Override public void setCastear(String spellID, Object parametrosSpell, int screenX, int screenY) {}
}
